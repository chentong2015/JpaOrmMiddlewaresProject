package entitymanager.query_criteria;

import entitymanager.entity.Sample;
import entitymanager.entity.join.KzAddress;
import entitymanager.entity.join.KzRecord;
import entitymanager.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CriteriaQueryEntityManager {

    // TODO. 使用Select 1查询来判断存在性
    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);

        // FROM子句只查常量，需要给定Table实体来源
        Root<Sample> root = cq.from(Sample.class);
        cq.select(cb.literal(1));
        cq.where(cb.equal(root.get("email"), "test@example.com"));

        TypedQuery<Integer> query = entityManager.createQuery(cq);
        List<Integer> result = query.setMaxResults(1).getResultList();
        boolean exists = !result.isEmpty();
        System.out.println(exists);
    }

    // TODO. 使用CriteriaQuery来构建复杂SQL
    // select distinct top (?) s1_0.id, s1_0.name
    // from t_sample_entity s1_0
    // where s1_0.name=?
    // order by s1_0.name asc
    private static void testCriteriaQueryBase(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sample> criteriaQuery = criteriaBuilder.createQuery(Sample.class);
        Root<Sample> root = criteriaQuery.from(Sample.class);
        criteriaQuery.distinct(true); // 必须和Order By同时使用
        criteriaQuery.select(root);      // 查询表的所有字段(Entity)
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), "java"));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
        // criteriaQuery.having();

        // TODO. 配置Query查询的参数
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

    // TODO. 定义多表JOIN聚合条件查询(利用表之间的关联)
    // SELECT k1_0.id, k1_0.NAME
    // FROM kz_record k1_0
    // JOIN kz_address k2_0 ON k1_0.id = k2_0.kz_record_id
    // WHERE k2_0.address !=? AND k1_0.id <?
    // ORDER BY k1_0.id ASC
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

        TypedQuery<KzRecord> typedQuery = entityManager.createQuery(criteriaQuery);
        List<KzRecord> kzRecordList = typedQuery.getResultList();
        for (KzRecord kzRecord : kzRecordList) {
            System.out.println(kzRecord);
        }
    }
}
