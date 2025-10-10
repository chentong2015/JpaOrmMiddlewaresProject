package com.hibernate5.testing;

import com.hibernate5.testing.package2.MyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class DemoHibernateMapping {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml")
            .build();
    static SessionFactory sessionFactory = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();

        // 使用class name来取类型的全路径名称(具有唯一性)
        String hqlQuery = "from " + MyEntity.class.getSimpleName();
        System.out.println(hqlQuery);
        Query<MyEntity> query = session.createQuery(hqlQuery, MyEntity.class);
        List<MyEntity> myEntities = query.getResultList();
        for (MyEntity entity : myEntities) {
            System.out.println(entity);
        }
        session.close();
    }
}
