package query_criteria;

import entity.Sample;
import helper.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class JpaCriteriaQuery {

    // TODO. 使用CriteriaQuery来构建复杂SQL
    // select distinct top (?) s1_0.id, s1_0.name
    // from t_sample_entity s1_0
    // where s1_0.name=?
    // order by s1_0.name asc
    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();

        // TODO. 配置查询的参数条件
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sample> criteriaQuery = criteriaBuilder.createQuery(Sample.class);
        Root<Sample> root = criteriaQuery.from(Sample.class);
        criteriaQuery.distinct(true); // 必须和Order By同时使用
        criteriaQuery.select(root);      // 查询表的所有字段(Entity)
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "java"));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
        // criteriaQuery.having();

        // TODO. 配置Query相关的属性
        TypedQuery<Sample> typedQuery = entityManager.createQuery(criteriaQuery);
        // typedQuery.setLockMode(LockModeType.OPTIMISTIC);
        typedQuery.setFlushMode(FlushModeType.COMMIT);
        typedQuery.setMaxResults(100);

        // 获取查询的结果
        List<Sample> sampleList = typedQuery.getResultList();
        entityManager.close();
        for (Sample sample : sampleList) {
            System.out.println(sample);
        }
    }
}
