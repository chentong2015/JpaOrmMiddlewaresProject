package query_pageable;

import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateSessionUtil;

import java.util.List;

// TODO. 使用纯hibernate API实现分页查询 -> 底层兼容不同DB类型
public class HibernatePageableQuery {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        String query = "Select p from Product p where p.price > :price order by p.id";

        assert session != null;
        List<Product> resultList =  session.createQuery(query, Product.class)
                .setParameter("price", 10)
                .setFirstResult(10) // Page Offset
                .setMaxResults(20)  // Page Size
                .getResultList();

        for (Product product: resultList) {
            System.out.println(product);
        }
        HibernateSessionUtil.closeSession();
    }

    // 测试前需要先InitData表中数据
    private static void initData(Session session) {
        session.getTransaction().begin();
        for (int index = 1; index < 500; index++) {
            Product product = new Product(index, "name " + index, "description " + index,
                    "location " + index, 10 + index, "url " + index, 100 + index);
            session.persist(product);
        }
        session.getTransaction().commit();
    }
}
