package com.entity.manager;

import com.entity.manager.entity.Book;

import javax.persistence.EntityManager;
import java.util.List;

public class DemoEntityManagerQuery {

    // Saves the movie entity into the database.
    public void saveBook() {
        EntityManager em = EntityManagerHandler.getEntityManager();
        em.getTransaction().begin();
        Book book = new Book();
        book.setName("java");
        book.setTitle("java title");
        em.persist(book);
        em.getTransaction().commit();
    }

    // TODO. 参数的占位符需要表明数字序号 !!
    public Book queryForBookById() {
        EntityManager em = EntityManagerHandler.getEntityManager();
        return (Book) em.createQuery("SELECT book from " + Book.class.getName() + " book where book.id = ?1")
                .setParameter(1, 1L)
                .getSingleResult();
    }

    // 返回查询的list结果列表
    public List queryForBooks() {
        EntityManager em = EntityManagerHandler.getEntityManager();
        return em.createQuery("SELECT book from " + Book.class.getName() + " book where book.name = ?1")
                .setParameter(1, "java")
                .getResultList();
    }

    // TODO. 根据指定的class类型就能获取到对应的table表中的数据
    public Book getBook(Long bookId) {
        EntityManager em = EntityManagerHandler.getEntityManager();
        return em.find(Book.class, bookId);
    }

    // 从persistence context持久层上下文中移除和merge更新
    public void mergeBook() {
        EntityManager em = EntityManagerHandler.getEntityManager();
        Book book = getBook(1L);
        em.detach(book);
        book.setTitle("new title");

        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }

    public void removeBook() {
        EntityManager em = EntityManagerHandler.getEntityManager();
        em.getTransaction().begin();
        Book book = getBook(1L);
        em.remove(book);
        em.getTransaction().commit();
    }
}
