package top.xoai.archforce.data;

import java.sql.Timestamp;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/18 9:29 上午
 */
public class ItemTable {
    private Long item_id;
    private Long store_id;
    private short type;
    private Timestamp create_time;
    private Long create_user_id;
    private Timestamp last_modify_time;
    private Long is_deleted;

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Long getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(Long create_user_id) {
        this.create_user_id = create_user_id;
    }

    public Timestamp getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(Timestamp last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public Long getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Long is_deleted) {
        this.is_deleted = is_deleted;
    }
}
