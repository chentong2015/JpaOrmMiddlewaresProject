package entitymanager.query_native;

import entitymanager.entity.Book;
import entitymanager.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;
import java.util.Optional;

// TODO. Native SQL 查询被废弃使用
// session.createNativeQuery(sqlQuery) - hibernate v6
// session.createSQLQuery(sqlQuery)    - hibernate v5
//
// 创建原始SQL语句时表名必须是映射出来的DB中的table名称
// SQL语法异常: org.hibernate.exception.SQLGrammarException
public class DemoSqlNativeQuery {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Book(1, "cpp"));
        entityManager.persist(new Book(2, "javax"));
        entityManager.getTransaction().commit();

        String sqlString = "SELECT * FROM t_book";
        List<Book> books = entityManager.createNativeQuery(sqlString, Book.class).getResultList();
        for (Book b : books) {
            System.out.println(b);
        }
        EntityManagerHandler.close();
    }

    protected <T> NativeQuery<T> getNatvieQuery(Session session, Class<T> clazz, String sqlQuery) {
        NativeQuery<T> query = session.createNativeQuery(sqlQuery);
        // 判断clazz是否是Identifiable的
        // Identifiable.class.isAssignableFrom(clazz)

        // 通过addEntity()让原生查询返回实体对象
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
}
