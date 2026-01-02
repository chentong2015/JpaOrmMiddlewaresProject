package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerHandler {

    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager() {
        emf = Persistence.createEntityManagerFactory("jpa.queries");
        return emf.createEntityManager();
    }

    // TODO. 在代码层面动态设置DataSource并创建EntityManager对象
    public static EntityManager getEntityManagerWithDataSource(DataSource dataSource) {
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.connection.datasource", dataSource);
        jpaProperties.put("hibernate.dialect_resolvers", "xxxDialectResolver.class.getName()");
        jpaProperties.put("hibernate.show_sql", "false");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.jdbc.fetch_size", 1000);

        emf = Persistence.createEntityManagerFactory("name", jpaProperties);
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}
