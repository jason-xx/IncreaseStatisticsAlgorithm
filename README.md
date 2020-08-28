本项目使用增量查询统计数据库大表.（数据库数据只增加，无修改和删除操作），同时项目中包含数据生成工具.
* src目录
> 统计工具源码

* tool目录 
>数据生成工具
# 数据生成工具使用方式
```
1.执行mysql_tester.sql
```

```
2.ava -jar mysql-tester-1.0.3.jar --mysql-username=root -mysql-password=123456 --mysql-url=jdbc:mysql://localhost:3306/mtest?serverTimezone=UTC --user-count=1000000 --max-item-per-user=10
```
