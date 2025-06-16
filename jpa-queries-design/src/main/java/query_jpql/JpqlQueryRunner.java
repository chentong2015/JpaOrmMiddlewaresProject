package query_jpql;

import helper.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

// Jpql: JPA Query Language 针对JPA API的查询语句
public class JpqlQueryRunner {

    public static final int FETCH_SIZE = 50000;

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters) {
        return runJpqlQuery(jpqlString, parameters, FETCH_SIZE);
    }

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters, int maxResults) {
        EntityManager em = EntityManagerHandler.getEntityManager();
        try {
            Query query = em.createQuery(jpqlString);
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            if (maxResults > 0) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
