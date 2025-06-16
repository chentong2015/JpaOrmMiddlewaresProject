package query_jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Jpql: JPA Query Language 针对JPA API的查询语句
public class JpqlQueryRunner {

    public static final int FETCH_SIZE = 50000;
    private final EntityManagerFactory emf;

    public JpqlQueryRunner(DataSource dataSource) {
        this.emf = newEntityManagerFactory(dataSource);
    }

    // TODO. 创建EntityManagerFactory时通过代码动态配置
    private EntityManagerFactory newEntityManagerFactory(DataSource dataSource) {
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.connection.datasource", dataSource);
        jpaProperties.put("hibernate.dialect_resolvers", "xxxDialectResolver.class.getName()");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        return Persistence.createEntityManagerFactory("name", jpaProperties);
    }

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters) {
        return runJpqlQuery(jpqlString, parameters, FETCH_SIZE);
    }

    public <T> List<T> runJpqlQuery(String jpqlString, Map<String, Object> parameters, int maxResults) {
        EntityManager em = emf.createEntityManager();
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
