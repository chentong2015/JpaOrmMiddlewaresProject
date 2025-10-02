package entitymanager.query_hql;

import entitymanager.entity.Person;
import entitymanager.entity.Sample;
import entitymanager.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;

public class DemoHqlQueryUpdate {

    // TODO. 使用EntityManager来执行HQL更新语句
    private static void testHqlDeleteQuery() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        entityManager.getTransaction().begin();
        // 等效于class.getName()返回全路径
        String fullPath = Sample.class.getCanonicalName();

        String deleteQuery = "DELETE FROM " + fullPath + " p WHERE p.name = :name";
        entityManager.createQuery(deleteQuery).setParameter("name", "test").executeUpdate();
        entityManager.getTransaction().commit();
    }

    // TODO. 使用Session来执行HQL更新语句
    //  Hibernate v6版本之前支持使用session.createQuery()来执行数据更新
    //  Insert, Update, Delete推荐使用session.createMutationQuery()
    public static void testHqlUpdate(Session session) {
        String updateQuery = "update " + Person.class.getName() + "set name = 'new name' where id=2";
        session.createMutationQuery(updateQuery).executeUpdate();
    }

    public static void testHqlDeleteQuery(Session session) {
        session.beginTransaction();
        String deleteQuery = "Delete from " + Person.class.getName() + " p where p.id = 3";
        session.createQuery(deleteQuery).executeUpdate();
        session.getTransaction().commit();
    }
}
