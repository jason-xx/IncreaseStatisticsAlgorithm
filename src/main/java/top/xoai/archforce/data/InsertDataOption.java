package top.xoai.archforce.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/18 9:27 上午
 */
public class InsertDataOption implements Runnable{

    private JdbcTemplate jdbcTemplate;

    private DataCarrier dataCarrier;

    public void insert(ItemTable data){
        String sql = "insert into item(type,store_id,create_time,create_user_id,last_modify_time,is_deleted) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,data.getType());
                preparedStatement.setLong(2,data.getStore_id());
                preparedStatement.setTimestamp(3,data.getCreate_time());
                preparedStatement.setLong(4, data.getCreate_user_id());
                preparedStatement.setTimestamp(5, data.getLast_modify_time());
                preparedStatement.setLong(6,data.getIs_deleted());
            }
        });
    }

    @Override
    public void run() {
        while(true){
            ItemTable itemTable = dataCarrier.take();
            insert(itemTable);
        }
    }
}
