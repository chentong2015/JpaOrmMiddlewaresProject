package query_criteria;

import entity.Sample;
import entity.join.KzAddress;
import entity.join.KzRecord;
import helper.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CriteriaQueryEntityManager {

    // TODO. 使用CriteriaQuery来构建复杂SQL
    // select distinct top (?) s1_0.id, s1_0.name
    // from t_sample_entity s1_0
    // where s1_0.name=?
    // order by s1_0.name asc
    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        testCriteriaQueryJoin(entityManager);
    }

    // TODO. 配置参数条件 -> 配置Query属性 -> 获取查询结果
    private static void testCriteriaQueryBase(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sample> criteriaQuery = criteriaBuilder.createQuery(Sample.class);
        Root<Sample> root = criteriaQuery.from(Sample.class);
        criteriaQuery.distinct(true); // 必须和Order By同时使用
        criteriaQuery.select(root);      // 查询表的所有字段(Entity)
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "java"));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
        // criteriaQuery.having();

        TypedQuery<Sample> typedQuery = entityManager.createQuery(criteriaQuery);
        // typedQuery.setLockMode(LockModeType.OPTIMISTIC);
        typedQuery.setFlushMode(FlushModeType.COMMIT);
        typedQuery.setMaxResults(100);

        List<Sample> sampleList = typedQuery.getResultList();
        entityManager.close();
        for (Sample sample : sampleList) {
            System.out.println(sample);
        }
    }

    // TODO. 定义多表JOIN聚合的条件查询
    private static void testCriteriaQueryJoin(EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<KzRecord> criteriaQuery = cb.createQuery(KzRecord.class);
        Root<KzRecord> kzRecordRoot = criteriaQuery.from(KzRecord.class);

        Join<KzRecord, KzAddress> kzRecordKzAddressJoin = kzRecordRoot.join("kzAddresses");
        Predicate notTwo = cb.notEqual(kzRecordKzAddressJoin.get("address"), "paris");
        Predicate lessTen = cb.lessThan(kzRecordRoot.get("id"), 10);
        Predicate restriction = cb.and(notTwo, lessTen);

        criteriaQuery.select(kzRecordRoot);
        criteriaQuery.where(restriction);
        criteriaQuery.orderBy(cb.asc(kzRecordRoot.get("id")));

        List<KzRecord> kzRecordList = entityManager.createQuery(criteriaQuery).getResultList();
        for (KzRecord kzRecord : kzRecordList) {
            System.out.println(kzRecord);
        }
    }
}
