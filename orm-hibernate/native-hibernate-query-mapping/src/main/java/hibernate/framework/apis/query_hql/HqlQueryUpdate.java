package hibernate.framework.apis.query_hql;

import hibernate.framework.apis.query.hql.entity.Person;
import org.hibernate.Session;

// TODO. 使用Session来执行HQL更新语句
//  Hibernate v6版本之前支持使用session.createQuery()来执行数据更新
//  Insert, Update, Delete推荐使用session.createMutationQuery()
public class HqlQueryUpdate {

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
