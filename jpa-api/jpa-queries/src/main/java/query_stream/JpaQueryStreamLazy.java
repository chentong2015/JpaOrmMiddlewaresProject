package query_stream;

import jakarta.persistence.EntityManager;
import query_pageable.Product;
import utils.EntityManagerHandler;

import java.util.stream.Stream;

public class JpaQueryStreamLazy {

    public static void main(String[] args) throws InterruptedException {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        String query = "Select p from Product p where p.price > :price order by p.id";

        // 返回Stream流结果: 当数据被使用时才会执行数据查询
        try (Stream<Product> productStream = entityManager.createQuery(query, Product.class)
                .setParameter("price", 400)
                .getResultStream()) {
            System.out.println("Create Stream Query.");
            Thread.sleep(10000);

            // Lazy操作: 真正获取数据的时刻
            productStream.forEach(System.out::println);
        }

        EntityManagerHandler.close();
    }
}
