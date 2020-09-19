package top.xoai.archforce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wx
 * @version 1.0
 * @date 2020/9/15 6:39 下午
 */
public class IncreaseAlgorithmObjectTool {

    private static final String KEY_CONN_STR = "-";
    private static final String KEY_NULL_STR = "@";
    public static final Long DEAFULT_DATABASE_SPEED = Long.MAX_VALUE;

    private static Map<String, IncreaseAlgorithmObject> increaseAlgorithmObjectMap = new HashMap<>();

    public static IncreaseAlgorithmObject getOrCreateIncreaseAlgorithmObject(IncreaseAlgorithmQueryMapper increaseAlgorithmQueryMapper, Object... queryParams){
        return getOrCreateIncreaseAlgorithmObject(increaseAlgorithmQueryMapper,DEAFULT_DATABASE_SPEED, queryParams);
    }

    public static IncreaseAlgorithmObject getOrCreateIncreaseAlgorithmObject(IncreaseAlgorithmQueryMapper increaseAlgorithmQueryMapper, Long dataBaseSpeed, Object... queryParams){
        String key = Arrays.asList(queryParams).stream().map(item->item==null?KEY_NULL_STR:item.toString()).collect(Collectors.joining(KEY_CONN_STR));
        if(!increaseAlgorithmObjectMap.containsKey(key)){
            increaseAlgorithmObjectMap.put(key, new IncreaseAlgorithmObject(increaseAlgorithmQueryMapper));
        }
        return increaseAlgorithmObjectMap.get(key);
    }
}
