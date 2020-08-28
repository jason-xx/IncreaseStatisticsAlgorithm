package top.xoai.archforce;

import top.xoai.archforce.entity.CountObject;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/7 3:54 下午
 */
public interface QueryMapper {

    CountObject countIncrease(Long baseIndex);

    CountObject countChunk(Long baseIndex, Long dataBaseSpeed);
}
