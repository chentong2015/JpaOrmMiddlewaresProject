package hibernate.framework.apis.query_hql.update_dynamic;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// TODO. @DynamicUpdate 动态更新
// - 将会产生性能开销，需要追踪之前变更的状态
// - 适合拥有大量Columns且仅有少量Column会频繁变动的场景
public class DemoDynamicUpdate {

    public static void main(String[] args){
        Transaction transaction = null;
        try {
            Session session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();
            // AccountEntity accountEntity = new AccountEntity(1,"name 1", "type1", "description1");
            // session.persist(accountEntity);
            testDynamicUpdate(session);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }

    private static void testDynamicUpdate(Session session) {
        // TODO. 普通Update将生成全字段的更新语句
        // update t_account_entity set description=?, name=?, type=? where id=?;
        AccountEntity accountEntity = session.get(AccountEntity.class, 1);
        accountEntity.setName("new name");
        session.merge(accountEntity);

        // TODO. @DynamicUpdate将生成只含变更列的更新语句
        // update t_account_entity set name=? where id=?;
        accountEntity.setName("dynamic update name");
        session.merge(accountEntity);
    }
}
