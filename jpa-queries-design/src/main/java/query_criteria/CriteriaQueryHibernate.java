package query_criteria;

import entity.BaseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Arrays;
import java.util.List;

public class CriteriaQueryHibernate {

    // TODO. Hibernate Session依赖于[hibernate.cfg.xml]配置文件
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        testCriteriaQueryFull(session);
        session.close();
    }

    // select * from t_base_entity;
    private static void testCriteriaQuerySimple(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BaseEntity> criteriaQuery = criteriaBuilder.createQuery(BaseEntity.class);
        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);
        criteriaQuery.select(root);

        List<BaseEntity> baseEntities = session.createQuery(criteriaQuery).getResultList();
        for (BaseEntity baseEntity : baseEntities) {
            System.out.println(baseEntity);
        }
    }

    // TODO. 定义查询字符和多个匹配条件
    // Select distinct label from t_base_entity
    // where id>=2 and id<10 and count in(?,?,?)
    // group by label
    // order by label desc;
    private static void testCriteriaQueryFull(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);

        criteriaQuery.distinct(true);
        criteriaQuery.select(root.get("label"));
        // criteriaQuery.multiselect(root.get("id"), root.get("label"));

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(root.get("id"), 2),
                criteriaBuilder.lessThan(root.get("id"), 10),
                criteriaBuilder.isTrue(root.get("count").in(Arrays.asList(1, 2, 3))));
        criteriaQuery.groupBy(root.get("label"));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("label")));
        // criteriaQuery.having()

        // 执行Query并返回结果
        List<String> labels = session.createQuery(criteriaQuery).getResultList();
        for (String label : labels) {
            System.out.println(label);
        }
    }
}
