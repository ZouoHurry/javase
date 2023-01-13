package jdbc;

import java.sql.*;

/**
 * @Author zouo
 * @Date 2023 - 01 - 11 - 20:30
 */

//<property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>   <!-- 连接数据库必须用到mysql 连接器 jar包-->
//<!--数据库访问地址-->
//<property name="url" value="jdbc:mysql://localhost:3306/zouo?useUnicode=true%26characterEncoding=utf-8%26useSSL=false%26serverTimezone=GMT%2B8"/>
//<!-- &转义为%26       %26useSSL=false%26serverTimezone=Asia/Shanghai"-->
//<property name="username" value="root"/>
//<!--数据库访问密码-->
//<property name="password" value="123456"/>
public class jdbcdemo {
    
    public static void main(String[] args) throws Exception {
        int i=0;
//        注册驱动，创建操作mysql的接口实现类
        Class.forName ("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/zouo?useUnicode=true%26characterEncoding=utf-8%26useSSL=false%26serverTimezone=GMT%2B8";
        String name="root";
        String password="123456";
//        与数据库建立连接
        Connection connection = DriverManager.getConnection (url, name, password);
        String sql="select * from user where name='zhen'";
        String sql1="insert into user values('zou',10,'男')";
//        通过connection获取执行sql对象statement

//        普通statement，存在sql注入风险
        Statement statement = connection.createStatement ();
//        预编译sql，防止sql注入
//        对sql进行转义，使sql注入失效
        PreparedStatement preparedStatement = connection.prepareStatement (sql);
//        执行sql,将返回数据封装进resultset
        ResultSet resultSet = preparedStatement.executeQuery ();
//        遍历集合，获取到每条记录
        while (resultSet.next ()) {
//            获取到每条记录的所有字段
            System.out.println (resultSet.getInt ("age"));
            System.out.println (resultSet.getString ("name"));
            System.out.println (resultSet.getString ("gender"));
        }
        try {
//        开启事务，false表示手动提交，true表示自动提交
        connection.setAutoCommit (false);
//        执行sql
        i = statement.executeUpdate (sql1);
//        提交事务
        connection.commit ();
          }catch (Throwable e){
//            发生异常时事务回滚
            connection.rollback ();
            e.printStackTrace ();
        }
        System.out.println (i);
//        关闭连接
        statement.close ();
        connection.close ();
        preparedStatement.close ();
        System.out.println ("test");
    }

}
