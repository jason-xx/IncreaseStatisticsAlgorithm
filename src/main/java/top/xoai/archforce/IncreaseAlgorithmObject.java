package top.xoai.archforce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xoai.archforce.entity.CountObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 刻度元数据对象
 * @author wx
 * @version 1.0
 * @date 2020/8/10 7:38 下午
 */
/*
    t = DATA_BASE_SPEED;
    lastDataBaseIndex = 0;
    currentDataBaseIndex = 0;
    增量统计：最终统计值=lastDataBaseIndex的统计值+currentDataBaseIndex的统计值(currentDataBaseIndex的统计值是基于lastDataBaseIndex的统计值的增量)
    设：上一次统计到lastDataBaseIndex=t2，lastDataBaseIndex的统计值为a
    case1：currentDataBaseIndex=e11（t2<e11<t3）的统计值为 x，则最终统计值为 a + x；（不存在此种情况）
                                   e11
                                   👇🏼
    ·------------·-----------·-----------·-----------·-----------·-----------·-----------·-----------·----------->
    0           t1          t2          t3          t4          t5          t6          t7          t8
    case2：currentDataBaseIndex=e12（t3<e12<=t4）的统计值为x，则最终统计值为 a + x；
                                                e12
                                                👇🏼
    ·------------·-----------·-----------·-----------·-----------·-----------·-----------·-----------·----------->
    0           t1          t2          t3          t4          t5          t6          t7          t8
    case3：currentDataBaseIndex=e13（t4<e13<=t5）的统计值为x，则最终统计值为 a + x；
    同时更新lastDataBaseIndex+=Double.valueOf(Math.floor((currentDataBaseIndex - DATA_BASE_SPEED - lastDataBaseIndex)/DATA_BASE_SPEED)).intValue() * DATA_BASE_SPEED=t3
    和lastDataBaseIndex的统计值为a + z，z为t2到t3的增量数据
                                                      e13
                                                       👇🏼
    ·------------·-----------·-----------·-----------·-----------·-----------·-----------·-----------·----------->
    0           t1          t2          t3          t4          t5          t6          t7          t8
    case4：currentDataBaseIndex=e14（t4<e14 && t5<=e14）的统计值为x，则最终统计值为 a + x；
    同时更新lastDataBaseIndex+=Double.valueOf(Math.floor((currentDataBaseIndex - DATA_BASE_SPEED - lastDataBaseIndex)/DATA_BASE_SPEED)).intValue() * DATA_BASE_SPEED
    和lastDataBaseIndex的统计值为a + z，z为t2到更新后的lastDataBaseIndex的增量数据（极端情况）
                                                                                          e14
                                                                                           👇🏼
    ·------------·-----------·-----------·-----------·-----------·-----------·-----------·-----------·----------->
    0           t1          t2          t3          t4          t5          t6          t7          t8
     */
public class IncreaseAlgorithmObject {
    private final Logger logger = LoggerFactory.getLogger(IncreaseAlgorithmObject.class);

    private static final Long DEFAULT_INCREASE_SIZE = Long.MAX_VALUE;
    //数据库的理论写入速度
    private Long DATA_BASE_SPEED = 100000L;

    //基础数据量
    private Long dbBaseIndex = 0L;

    //当前查询的数据库已存在记录的最大数量
    private Long dbCurrentIndex = 0L;

    //基础数据量的偏移数量
    private Long baseIndexOffset = 0L;

    private IncreaseAlgorithmQueryMapper queryMapper;

    private Map<String,Long> dbBaseIndexIndicator = new HashMap(){{
        put("indicator1",0L);
        put("indicator2",0L);
        put("indicator3",0L);
        put("indicator4",0L);
        put("indicator5",0L);
        put("indicator6",0L);
        put("indicator7",0L);
        put("indicator8",0L);
        put("indicator9",0L);
    }};

    private Map<String,Long> preDbBaseIndexIndicator = new HashMap(){{
        put("indicator1",0L);
        put("indicator2",0L);
        put("indicator3",0L);
        put("indicator4",0L);
        put("indicator5",0L);
        put("indicator6",0L);
        put("indicator7",0L);
        put("indicator8",0L);
        put("indicator9",0L);
    }};

    public IncreaseAlgorithmObject(IncreaseAlgorithmQueryMapper queryMapper) {
        this.queryMapper = queryMapper;
    }

    public IncreaseAlgorithmObject(Map<String, Long> dbBaseIndexIndicator, IncreaseAlgorithmQueryMapperImpl queryMapper) {
        this.dbBaseIndexIndicator = new HashMap<>(dbBaseIndexIndicator);
        this.preDbBaseIndexIndicator = new HashMap<>(dbBaseIndexIndicator);
        this.queryMapper = queryMapper;
    }

    public IncreaseAlgorithmObject(Map<String, Long> dbBaseIndexIndicator, Long dataBaseSpeed, IncreaseAlgorithmQueryMapperImpl queryMapper) {
        this.dbBaseIndexIndicator = new HashMap<>(dbBaseIndexIndicator);
        this.preDbBaseIndexIndicator = new HashMap<>(dbBaseIndexIndicator);
        this.queryMapper = queryMapper;
        this.DATA_BASE_SPEED = dataBaseSpeed;
    }

    private void updateIndexAndBaseIndicator(Long baseIndexOffset){
        this.baseIndexOffset = baseIndexOffset;
        this.dbCurrentIndex = dbBaseIndex + baseIndexOffset;
        this.dbBaseIndexIndicator.forEach((k,v)->this.preDbBaseIndexIndicator.put(k,v));
        if(isNeedUpdateIndex()){
            Long oldLastDataBaseIndex = dbBaseIndex;
            dbBaseIndex += Double.valueOf(Math.floor((dbCurrentIndex - DATA_BASE_SPEED - dbBaseIndex)/DATA_BASE_SPEED)).longValue() * DATA_BASE_SPEED;
            CountObject countObject = queryMapper.countChunk(oldLastDataBaseIndex, dbBaseIndex - oldLastDataBaseIndex);
            countObject.getStatisticalData().forEach((key,value)-> dbBaseIndexIndicator.merge(key,value, Long::sum));
        }
    }

    public synchronized CountObject query(){
        logger.info("before--dbBaseIndex:{},dbBaseIndexIndicator:{}", dbBaseIndex,dbBaseIndexIndicator);
        CountObject countResult = queryMapper.countChunk(dbBaseIndex, DEFAULT_INCREASE_SIZE);
        updateIndexAndBaseIndicator(countResult.getIncreasedCount());
        final Map<String,Long> indicatorResult = new HashMap<>(getPreDbBaseIndexIndicator());
        countResult.getStatisticalData().forEach((key,value)-> indicatorResult.merge(key,value, Long::sum));
        logger.info("after:dbBaseIndex:{},dbBaseIndexIndicator:{},currentDBIndex:{},baseIndexOffsetIndicator:{},indicatorResult:{}",
                baseIndexOffset,dbBaseIndex,dbCurrentIndex,countResult.getStatisticalData(),indicatorResult);
        CountObject rsObj = new CountObject(new HashMap<>(indicatorResult));
        rsObj.setIncreasedCount(dbCurrentIndex);
        return rsObj;
    }

    protected boolean hasOffset(){
        return dbCurrentIndex - dbBaseIndex > 0;
    }

    protected boolean isNeedUpdateIndex(){
        return dbCurrentIndex - dbBaseIndex > 2*DATA_BASE_SPEED;
    }

    public Long getDATA_BASE_SPEED() {
        return DATA_BASE_SPEED;
    }

    public Long getDbBaseIndex() {
        return dbBaseIndex;
    }

    public Long getDbCurrentIndex() {
        return dbCurrentIndex;
    }

    public Map<String, Long> getDbBaseIndexIndicator() {
        return Collections.unmodifiableMap(dbBaseIndexIndicator);
    }

    public Map<String, Long> getPreDbBaseIndexIndicator() {
        return preDbBaseIndexIndicator;
    }

    public Long getIndicatorByKey(String key){
        return dbBaseIndexIndicator.get(key);
    }

    public Long getBaseIndexOffset() {
        return baseIndexOffset;
    }

}


