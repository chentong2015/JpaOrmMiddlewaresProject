package jpa_query_language;

import utils.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Map;

// Jpql: JPA Query Language 针对JPA API的查询语句
public class JpaQueryLanguage {

    public static final int FETCH_SIZE = 1000;

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters) {
        EntityManager em = EntityManagerHandler.getEntityManager();
        try {
            Query query = em.createQuery(jpqlString);
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            query.setMaxResults(FETCH_SIZE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
