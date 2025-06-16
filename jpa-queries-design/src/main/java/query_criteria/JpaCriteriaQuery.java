package query_criteria;

import entity.Sample;
import helper.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class JpaCriteriaQuery {

    // TODO. 使用CriteriaQuery.createQuery(Sample.class)来创建条件查询和Entity Name没有关系 !!
    private static void testCriteriaQuery() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sample> criteriaQuery = criteriaBuilder.createQuery(Sample.class);

        Root<Sample> root = criteriaQuery.from(Sample.class);
        criteriaQuery.select(root.get("id"));
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "java"));
        criteriaQuery.distinct(true);
        // criteriaQuery.having(predicate..);

        TypedQuery<Sample> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Sample> sampleList = typedQuery.getResultList();
        // typedQuery.setLockMode(LockModeType.OPTIMISTIC) 没有transaction则不需要设置锁模式

        entityManager.close();
        for (Sample sample : sampleList) {
            System.out.println(sample.getId());
        }
    }
}
