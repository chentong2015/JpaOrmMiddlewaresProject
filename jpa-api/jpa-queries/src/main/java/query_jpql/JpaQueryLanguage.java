package query_jpql;

import utils.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Map;

// TODO. JPA Query Language 查询语句基于Entity Name名称(面向对象)
public class JpaQueryLanguage {

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters) {
        EntityManager em = EntityManagerHandler.getEntityManager();
        try {
            Query query = em.createQuery(jpqlString);
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setMaxResults(1000); // Batch size
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
