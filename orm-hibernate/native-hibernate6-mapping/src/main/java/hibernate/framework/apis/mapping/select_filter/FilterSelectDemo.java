package hibernate.framework.apis.mapping.select_filter;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FilterSelectDemo {

    public static void main(String[] args){
        Transaction transaction = null;
        try {
            Session session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();
            // initData(session);
            testFilterSelection(session);
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
        FilterEntity filter1 = new FilterEntity(1, "name 1", "type 1", 101);
        FilterEntity filter2 = new FilterEntity(2, "name 2", "type 3", 201);
        FilterEntity filter3 = new FilterEntity(3, "name 3", "type 3", 301);
        session.persist(filter1);
        session.persist(filter2);
        session.persist(filter3);
    }

    // TODO. 激活特定的Filter过滤器并设置参数的值, 未激活时无条件查询
    //  select f1_0.id, f1_0.limit, f1_0.name, f1_0.type
    //  from t_filter_entity f1_0
    //  where limit > ?
    private static void testFilterSelection(Session session) {
        session.enableFilter("incomeLevelFilter");
                // .setParameter("incomeLimit", 200);
        List<FilterEntity> filterEntityList = session.createQuery("from FilterEntity").list();
        for (FilterEntity filterEntity : filterEntityList) {
            System.out.println(filterEntity);
        }
    }
}
