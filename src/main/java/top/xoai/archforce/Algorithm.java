package top.xoai.archforce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.xoai.archforce.entity.CountObject;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/7 3:44 下午
 */
@Component
public class Algorithm {
    
    private final Logger logger = LoggerFactory.getLogger(Algorithm.class);

    @Autowired
    private QueryMapperImpl queryMapper;

    private final ReferenceFrameObject referenceFrameObject = new ReferenceFrameObject(queryMapper);

    private Map<String,Long> indicatorResult;

    public void algorithmDo(){
        boolean first = true;
        if(first) {
            referenceFrameObject.initBaseIndicator(new HashMap(){{
                put("indicator1",0L);
                put("indicator2",0L);
                put("indicator3",0L);
                put("indicator4",0L);
                put("indicator5",0L);
                put("indicator6",0L);
                put("indicator7",0L);
                put("indicator8",0L);
                put("indicator9",0L);
            }},queryMapper);
            first = false;
        }
        logger.info("baseIndex:" + referenceFrameObject.getDbBaseIndex());
        CountObject countResult = queryMapper.countIncrease(referenceFrameObject.getDbBaseIndex());
        referenceFrameObject.updateCurrentDBIndex(countResult.getIncreasedCount());
        logger.info("increasedCount:" + countResult.getIncreasedCount() + ",currentIndex:" + referenceFrameObject.getDbCurrentIndex());
        indicatorResult = new HashMap<>(referenceFrameObject.getDbBaseIndexIndicator());
        if(referenceFrameObject.hasOffset()){
            countResult.getStatisticalData().forEach((key,value)-> indicatorResult.merge(key,value, Long::sum));
            logger.info("increased:" + referenceFrameObject.getDbBaseIndexIndicator());
        }
        logger.info("indicatorResult:" + indicatorResult.toString());
        logger.info("------------------------------------------------------------------------------------------");
    }

    private ThreadLocal<ReferenceFrameObject> threadLocal = new ThreadLocal<>();

    public void query(){
        if(threadLocal.get() == null){
            threadLocal.set(new ReferenceFrameObject(queryMapper));
        }
        CountObject countObject = threadLocal.get().query();
        System.out.println("one query finished.");
    }

    @Scheduled(cron="0/3 * * * * ?")
    public void test(){
        logger.info("begin...");
//        algorithmDo();
        query();
    }

    @PostConstruct
    public void start(){
        logger.info("start...");
    }

}
