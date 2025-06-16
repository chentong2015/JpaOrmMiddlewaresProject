package query_named;

import helper.EntityManagerHandler;
import jakarta.persistence.EntityManager;

import java.util.List;

public class NamedQueryTest {

    // TODO. 同时支持@NamedQuery和@NamedNativeQuery
    // 通过实体类型上定义的NamedQuery具名查询来查询数据
    public static void testNamedQueries() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        List<NamedQueryEntity> results = entityManager.createNamedQuery("EntityNamed.findAll").getResultList();
        // List<NamedQueryEntity> results = entityManager.createNamedQuery("EntityNamedNative.findAll").getResultList();
        for (NamedQueryEntity entity : results) {
            System.out.println(entity);
        }
        entityManager.close();
    }
}
