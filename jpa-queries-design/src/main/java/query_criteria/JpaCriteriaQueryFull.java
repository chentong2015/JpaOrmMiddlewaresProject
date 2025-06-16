package query_criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import entity.BaseEntity;

import java.util.List;

public class JpaCriteriaQueryFull {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        testSelectLabel(session);
        session.close();
    }

    // select * from t_entity_sample;
    private static void testSelectEntityClass(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BaseEntity> criteriaQuery = criteriaBuilder.createQuery(BaseEntity.class);
        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);

        // 获取整个Entity Class的所有属性(获取表的所有列)
        criteriaQuery.select(root);
        List<BaseEntity> baseEntities = session.createQuery(criteriaQuery).getResultList();
        for (BaseEntity baseEntity : baseEntities) {
            System.out.println(baseEntity);
        }
    }

    // 完整的对应到SQL原生查询语句的条件
    // Select distinct label from t_base_entity
    // where id>=2 and id<10
    // group by label
    // order by label desc;
    private static void testSelectLabel(Session session) {
        // Builder用来设置一些匹配条件
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        // 创建条件查询，参数是查询结果的类型 Params: resultClass – type of the query result
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        // 从指定的Entity Class中查询数据 Params: entityClass – the entity class
        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);

        criteriaQuery.distinct(true);

        // 执行多个属性的查询, 定义查询出来的结果class
        criteriaQuery.select(root.get("label"));
        // criteriaQuery.multiselect(root.get("id"), root.get("label"));

        // 同时添加多个where的匹配条件: like, between, isNull, >=, <=
        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), 2),
                criteriaBuilder.lessThan(root.get("id"), 10));

        // Group By写在Order by的前面
        criteriaQuery.groupBy(root.get("label"));

        // Hibernate API提供的两种Order By的写法
        // criteriaQuery.orderBy(criteriaBuilder.desc(root.get("label")));
        // criteriaQuery.orderBy(new OrderImpl(root.get("label"), false));

        // having对查询出来的结果进行处理，放在最后
        // criteriaQuery.having()

        // 执行查询，获取返回的结果 .list()
        List<String> labels = session.createQuery(criteriaQuery).getResultList();
        for (String label : labels) {
            System.out.println(label);
        }
    }
}
