package top.xoai.archforce.data;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/18 9:27 上午
 */
public class CreateDataOption {

    public ItemTable create(){
        ItemTable itemTable = new ItemTable();
        itemTable.setType(Short.valueOf(String.valueOf(new Random().nextInt(10))));
        itemTable.setStore_id(new Random().nextLong());
        itemTable.setCreate_time(new Timestamp(new Date().getTime()));
        itemTable.setCreate_user_id(9999999999L);
        return itemTable;
    }

    enum ItemType{
        TYPE_1(1, "类型1"),
        TYPE_2(2, "类型2"),
        TYPE_3(3, "类型3"),
        TYPE_4(4, "类型4"),
        TYPE_5(5, "类型5"),
        TYPE_6(6, "类型6"),
        TYPE_7(7, "类型7"),
        TYPE_8(8, "类型8"),
        TYPE_9(9, "类型9");


        ItemType(Integer type, String typeName) {
            this.type = type;
            this.typeName = typeName;
        }

        private Integer type;
        private String typeName;

        public Integer getType() {
            return type;
        }

        public String getTypeName() {
            return typeName;
        }

        public static ItemType valueOf(Integer type){
            return Arrays.stream(ItemType.values()).filter(item -> item.getType().equals(type)).findAny().get();
        }
    }
}
