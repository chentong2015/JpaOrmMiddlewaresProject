package query_hql.select_where;

import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateSessionUtil;

import java.util.List;

public class WhereSelectDemo {

    public static void main(String[] args){
        Transaction transaction = null;
        try {
            Session session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();
            // initData(session);
            // testEntityWhereCondition(session);
            testDirectQuery(session);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }

    private static void initData(Session session) {
        Phone phone1 = new Phone(1, false, "1234");
        Phone phone2 = new Phone(2, true, "123444");
        Phone phone3 = new Phone(3, false, "12345");
        WhereEntity filterEntity1 = new WhereEntity(1, "name 1", "GOOD");
        filterEntity1.setPhones(List.of(phone1, phone2, phone3));
        WhereEntity filterEntity2 = new WhereEntity(2, "name 2", "BAD");
        WhereEntity filterEntity3 = new WhereEntity(3, "test 1", "GOOD");

        // 适用Cascade级联操作进行存储
        session.persist(filterEntity1);
        session.persist(filterEntity2);
        session.persist(filterEntity3);
    }

    // TODO. 查询所有Entity实体时自动添加where过滤条件
    // select f1_0.id, f1_0.name, f1_0.type from t_filter_entity f1_0
    // where ( f1_0.type = 'GOOD' )
    private static void testEntityWhereCondition(Session session) {
       List<WhereEntity> whereEntityList = session.createQuery("from WhereEntity").list();
       for (WhereEntity whereEntity : whereEntityList) {
           System.out.println(whereEntity);

           // TODO. 查询所有关联的Field列表时自动添加where过滤条件
           // select p1_0.employee_id, p1_0.id, p1_0.deleted, p1_0.number
           // from t_phone_entity p1_0
           // where p1_0.employee_id=? and ( p1_0.deleted = 0 )
           List<Phone> phoneList = whereEntity.getPhones();
           for(Phone phone : phoneList){
               System.out.println(phone);
           }
       }
    }

    // TODO. 单独查询Phone列表不会产生任何的Where过滤条件
    // select p1_0.id, p1_0.deleted, p1_0.number from t_phone_entity p1_0
    private static void testDirectQuery(Session session) {
        List<Phone> phoneList = session.createQuery("from Phone").list();
        for (Phone phone : phoneList) {
            System.out.println(phone);
        }
    }
}
