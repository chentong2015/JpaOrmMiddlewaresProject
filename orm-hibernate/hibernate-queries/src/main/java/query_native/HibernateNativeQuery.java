package query_native;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;
import java.util.Optional;

// TODO. Native SQL 查询被废弃使用
// session.createNativeQuery(sqlQuery) - hibernate v6
// session.createSQLQuery(sqlQuery)    - hibernate v5
public class HibernateNativeQuery {

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

    protected <T> NativeQuery<T> getNativeQuery(Session session, Class<T> clazz, String sqlQuery) {
        NativeQuery<T> query = session.createNativeQuery(sqlQuery);
        // 判断clazz是否是Identifiable的
        // Identifiable.class.isAssignableFrom(clazz)

        // 通过addEntity()让原生查询返回实体对象
        if ((clazz != null)) {
            query.addEntity(clazz);
        }
        return query;
    }
}
