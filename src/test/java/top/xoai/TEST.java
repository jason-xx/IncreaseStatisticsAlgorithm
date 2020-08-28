package top.xoai;

import java.util.Arrays;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/14 10:04 下午
 */
public class TEST {

    public static void main(String[] args) {
        String opu = "4";
        System.out.println(Arrays.stream(opu.split(",")).filter(item->item.equals("2")).findAny().isPresent());
    }
}
