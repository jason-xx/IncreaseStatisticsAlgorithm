package top.xoai.archforce.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/7 4:42 下午
 */
public class CountObject {
    private Long increasedCount;
    private Map<String,Long> statisticalData;

    public CountObject(Map<String,Object> data) {
        this.increasedCount = Long.valueOf(data.getOrDefault("increasedCount","0").toString());
        this.statisticalData = new HashMap<>();
        data.forEach((key, value) -> statisticalData.put(key, Long.valueOf(value==null?"0":value.toString())));
        this.statisticalData.remove("increasedCount");
    }

    public Long getIncreasedCount() {
        return increasedCount;
    }

    public void setIncreasedCount(Long increasedCount) {
        this.increasedCount = increasedCount;
    }

    public Map<String, Long> getStatisticalData() {
        return statisticalData;
    }

    public void setStatisticalData(Map<String, Long> statisticalData) {
        this.statisticalData = statisticalData;
    }
}
