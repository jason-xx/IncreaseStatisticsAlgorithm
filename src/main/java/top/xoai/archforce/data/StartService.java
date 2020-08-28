package top.xoai.archforce.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/18 10:10 上午
 */
public class StartService{

    private CreateDataOption createDataOption;

    private InsertDataOption insertDataOption;

    private DataCarrier dataCarrier;

    private void initCreateDataThread(Long dataCount){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss:SSS");
        Long startTime = System.currentTimeMillis();
        Long startCount = 0L;
        for (int i =0; i< dataCount; i++){
            dataCarrier.push(createDataOption.create());
            Long currentTime = System.currentTimeMillis();
            if(currentTime - startTime >= 1000){
                System.out.println(sdf.format(new Date(startTime)) + " 到 " + sdf.format(new Date(currentTime)) + "生成数据:" + (i-startCount));
//                startCount = i;
            }
        }
    }

    private void initInsertDataThread(){

    }

}
