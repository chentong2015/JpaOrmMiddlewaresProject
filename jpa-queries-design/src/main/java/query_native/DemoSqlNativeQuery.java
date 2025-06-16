package query_native;

import entity.Sample;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// TODO. Native SQL 查询被废弃使用
// session.createNativeQuery(sqlQuery) - hibernate version6
// session.createSQLQuery(sqlQuery)    - hibernate version5
//
// 创建原始的SQL查询语句, 使用的表名必须是映射出来的DB中的table名称
// SQL语法异常: org.hibernate.exception.SQLGrammarException
public class DemoSqlNativeQuery {

    private final String sqlQuery;

    public DemoSqlNativeQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Query getQuery(Session session) {
        session.createNativeQuery(sqlQuery);

        return session.createNativeQuery(sqlQuery);
    }

    protected <T> Query<T> getQuery(Session session, Class<T> clazz) {
        NativeQuery<T> query = session.createNativeQuery(sqlQuery);
        // Identifiable.class.isAssignableFrom(clazz) 判断clazz是否是Identifiable的
        if ((clazz != null)) {
            query.addEntity(clazz);
        }
        return query;
    }

    // TODO. 测试复杂的查询语句和Entity Name的关系
    public static void testSqlQuery(Session session) {
        String sql = "Select firstname from t_person";
        List<String> firstnameList = session.createNativeQuery(sql).getResultList();
        for (String firstname : firstnameList) {
            System.out.println(firstname);
        }

        String sqlQuery = "Select p.firstname from t_person p where p.id=:id";
        Optional firstname = session.createNativeQuery(sqlQuery)
                .setParameter("id", 4)
                .getResultStream()
                .findFirst();
        if (firstname.isPresent()) {
            System.out.println("Found name: " + firstname.get());
        }
    }

    // 测试SqlQuery查询: 是否只能使用DB table的名称来做查询 ?
    // 自定义返回的字段, 使用映射到数据库的table名称
    private static void testSqlRawQuery(Session session) {
        String sql = "Select name from t_second_entity";
        String sqlQuery = "Select name from " + Sample.class.getName();
        List<String> names = session.createNativeQuery(sqlQuery).getResultList();
        for (String name : names) {
            System.out.println("Found name: " + name);
        }
    }

    // 创建SQL Query查询语句，通过addEntity()让原生查询返回实体对象
    private static void testCreateQuery(Session session) {
        NativeQuery query = session.createNativeQuery("Select * from t_first_entity");

       // List<MyEntity> rows = session.createSQLQuery("Select * from t_first_entity")
       //         .addEntity(MyEntity.class)
       //         .getResultList();
    }
}
