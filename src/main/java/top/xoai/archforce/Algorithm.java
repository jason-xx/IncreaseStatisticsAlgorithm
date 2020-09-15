package top.xoai.archforce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.xoai.archforce.entity.CountObject;

import javax.annotation.PostConstruct;
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
    private IncreaseAlgorithmQueryMapperImpl queryMapper;

    private final IncreaseAlgorithmObject increaseAlgorithmObject = new IncreaseAlgorithmObject(queryMapper);

    private Map<String,Long> indicatorResult;

    private ThreadLocal<IncreaseAlgorithmObject> threadLocal = new ThreadLocal<>();

    public void query(){
        if(threadLocal.get() == null){
            threadLocal.set(new IncreaseAlgorithmObject(queryMapper));
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
