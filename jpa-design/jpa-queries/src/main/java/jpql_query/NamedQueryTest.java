package jpql_query;

import utils.EntityManagerHandler;
import jakarta.persistence.EntityManager;

import java.util.List;

// TODO. JPA支持@NamedQuery和@NamedNativeQuery两种具名查询
public class NamedQueryTest {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        List<NamedQueryEntity> results = entityManager.createNamedQuery("EntityNamed.findAll").getResultList();
        // List<NamedQueryEntity> results = entityManager.createNamedQuery("EntityNamedNative.findAll").getResultList();

        for (NamedQueryEntity entity : results) {
            System.out.println(entity);
        }
        entityManager.close();
    }
}
