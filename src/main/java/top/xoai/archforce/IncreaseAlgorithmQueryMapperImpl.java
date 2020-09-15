package top.xoai.archforce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import top.xoai.archforce.entity.CountObject;

import java.util.Map;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/14 5:15 下午
 */
@Service
public class IncreaseAlgorithmQueryMapperImpl implements IncreaseAlgorithmQueryMapper
{
    private final Logger logger = LoggerFactory.getLogger(IncreaseAlgorithmQueryMapperImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public CountObject countChunk(Long baseIndex, Long dataBaseSpeed) {
        String sql = "select count(1) increasedCount,sum(case type when 1 then 1 else 0 end) as indicator1," +
                "sum(case type when 2 then 1 else 0 end) as indicator2," +
                "sum(case type when 3 then 1 else 0 end) as indicator3," +
                "sum(case type when 4 then 1 else 0 end) as indicator4," +
                "sum(case type when 5 then 1 else 0 end) as indicator5," +
                "sum(case type when 6 then 1 else 0 end) as indicator6," +
                "sum(case type when 7 then 1 else 0 end) as indicator7," +
                "sum(case type when 8 then 1 else 0 end) as indicator8," +
                "sum(case type when 9 then 1 else 0 end) as indicator9 from (select * from item order by item_id limit " + baseIndex + ", " + dataBaseSpeed + ") t";
//        logger.info(sql);
        Map<String,Object> result = jdbcTemplate.queryForMap(sql);
        return new CountObject(result);
    }
}
