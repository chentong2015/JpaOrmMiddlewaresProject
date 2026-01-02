package query_pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import utils.EntityManagerHandler;

import java.util.List;

public class JpaPageableCriteriaQuery {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();

        CriteriaQuery<Product> criteriaQuery = null;
        TypedQuery<Product> typedQuery = getPageableCriteriaQuery(entityManager, criteriaQuery, 1, 10);
        List<Product> resultList = typedQuery.getResultList();
    }

    // 封装条件查询和分页查询API
    public static <T> TypedQuery<T> getPageableCriteriaQuery(EntityManager entityManager, CriteriaQuery<T> criteriaQuery, int offset, int pageSize) {
        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(pageSize);
        return typedQuery;
    }
}
