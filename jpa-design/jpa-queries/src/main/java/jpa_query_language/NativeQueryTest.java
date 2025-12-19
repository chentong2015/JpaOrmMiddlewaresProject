package jpa_query_language;

import entity.Book;
import utils.EntityManagerHandler;
import jakarta.persistence.EntityManager;

import java.util.List;

// TODO. 创建原始SQL语句时表名必须是映射出来的DB中的table名称
// SQL语法异常: org.hibernate.exception.SQLGrammarException
public class NativeQueryTest {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Book(1, "cpp"));
        entityManager.persist(new Book(2, "javax"));
        entityManager.getTransaction().commit();

        String sqlString = "SELECT * FROM t_book";
        List<Book> books = entityManager.createNativeQuery(sqlString, Book.class).getResultList();
        for (Book b : books) {
            System.out.println(b);
        }
        EntityManagerHandler.close();
    }
}
