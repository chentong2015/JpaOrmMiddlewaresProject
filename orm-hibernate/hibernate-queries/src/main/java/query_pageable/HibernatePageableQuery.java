package query_pageable;

import org.hibernate.Session;
import org.hibernate.Transaction;
import session.HibernateSessionUtil;

// TODO. 使用纯hibernate API实现分页查询效果, 底层兼容不同DB类型
public class HibernatePageableQuery {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSession();
        Transaction transaction = session.beginTransaction();

        transaction.commit();
        HibernateSessionUtil.closeSession();
    }
}
