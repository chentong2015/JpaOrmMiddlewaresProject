package query_criteria;

import entity.BaseEntity;
import entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class CriteriaQueryHibernateComplex {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        testCriteriaQuerySubSelect(session);
        session.close();
    }

    // TODO. 定义SubSelect子查询语句
    // select b1_0.label
    // from t_base_entity b1_0
    // where b1_0.id in(
    //   (select b2_0.id from t_book b2_0)
    // )
    private static void testCriteriaQuerySubSelect(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<Book> rootSub = subquery.from(Book.class);
        subquery.select(rootSub.get("id"));

        Root<BaseEntity> root = criteriaQuery.from(BaseEntity.class);
        criteriaQuery.select(root.get("label"));
        criteriaQuery.where(criteriaBuilder.isTrue(root.get("id").in(subquery)));

        List<String> labels = session.createQuery(criteriaQuery).getResultList();
        for (String label : labels) {
            System.out.println(label);
        }
    }

    // TODO. 定义Restriction约束条件
    // org.springframework.data.jpa.domain.Specification
    private static void testCriteriaQueryRestriction(Session session) {

    }
}
