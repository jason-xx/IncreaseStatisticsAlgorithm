package top.xoai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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

        BigDecimal num = new BigDecimal("2222.333");
        System.out.println(num.toString());

        System.out.println("" + new Byte("-1"));

//        Byte b = new Byte(null);
//        System.out.println(b);

        Logger logger = LoggerFactory.getLogger(TEST.class);
        logger.info("print param:{},{}",1,"2");
    }
}
