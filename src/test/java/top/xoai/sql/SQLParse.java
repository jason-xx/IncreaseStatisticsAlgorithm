package top.xoai.sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/28 10:20 上午
 */
public class SQLParse {

    public static void main(String[] args) throws JSQLParserException {
        String sql = "select t1 from table1 t1 union all select t2.b,t3.c from table2 t2,table3 t3 where t2.b = t3.c";

        Statement stmt = CCJSqlParserUtil.parse(sql);

        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;

//From分析
        FromItem fromItem = plainSelect.getFromItem();
        Table table = (Table) fromItem; // table.getAlias().getName(), table.getName()

//// join分析
//        List<Join> joins = plainSelect.getJoins();
//        for (Join join : joins) {
//            FromItem rightItem = join.getRightItem();
//            Table table = (Table) rightItem; // (table.getAlias().getName(), table.getName());
//        }
    }
}
