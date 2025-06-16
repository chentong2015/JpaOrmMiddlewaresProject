import entity.Sample;

import helper.EntityManagerHandler;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaEntityManagerQueries {

    public static void main(String[] args) {
        testHqlSelectQuery();
        // testHqlDeleteQuery();
        // testNativeSqlQueries();
    }

    // TODO. @Entity(name 推荐设置
    // 1. 当没有设置entity name时，.getSimpleName()和.getName()都能工作
    // 2. 当设置entity name之后，HQL查询语句必须使用全路径名称.getName()
    private static void testHqlSelectQuery() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        String qlString = "FROM " + Sample.class.getName();

        // 3. 创建HQL Query查询时需要提供ResultClass结果值的类型 !!
        List<Sample> sampleList = entityManager.createQuery(qlString, Sample.class).getResultList();
        System.out.println(sampleList.size());
    }

    private static void testHqlDeleteQuery() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        entityManager.getTransaction().begin();
        // 等效于class.getName()返回全路径
        String fullPath = Sample.class.getCanonicalName();

        String deleteQuery = "DELETE FROM " + fullPath + " p WHERE p.name = :name";
        entityManager.createQuery(deleteQuery).setParameter("name", "test").executeUpdate();
        entityManager.getTransaction().commit();
    }




    // TODO. 提供查询出来的结果对应的类型，如果是原实体类型，则必须查询出所有的字段
    public static void testNativeSqlQueries() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        String sqlString = "SELECT * FROM t_sample_entity";
        List<Sample> sampleList = entityManager.createNativeQuery(sqlString, Sample.class).getResultList();
        for (Sample sample : sampleList) {
            System.out.println(sample);
        }
        entityManager.close();
    }


}
