package query_hql;

import entity.Person;
import entity.Sample;
import helper.EntityManagerHandler;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

// HQL: hibernate query language 类似sql的简化查询语言
// 1. 必须满足特定的语句描述规则，查询时使用的JPA Entity Name名称
// 2. Hibernate源码中会将HQL转换成SQL，然后发送到数据库进行查询
// org.hibernate.hql.internal.ast.QueryTranslatorImpl
public class DemoHqlQuerySelect {

    // TODO. 使用EntityManager来创建HQL查询语句
    //  @Entity(name 推荐设置
    // 1. 当没有设置entity name时，.getSimpleName()和.getName()都能工作
    // 2. 当设置entity name之后，HQL查询语句必须使用全路径名称.getName()
    private static void testHqlSelectQuery() {
        EntityManager entityManager = EntityManagerHandler.getEntityManager();
        String qlString = "FROM " + Sample.class.getName();

        // 3. 创建HQL Query查询时需要提供ResultClass结果值的类型 !!
        List<Sample> sampleList = entityManager.createQuery(qlString, Sample.class).getResultList();
        System.out.println(sampleList.size());
    }

    // TODO. 使用Session来创建HQL查询语句
    // 1. .createQuery(query, String.class)   提供的是查询返回的结果类型
    // 2. .createQuery(query).executeUpdate() 如果是更新的语句，则不需要提供查询结果的类型 => 使用transaction事务
    public static void testHqlSelectQuery(Session session) {
        // 表示选择指定的table的所有字段
        String selectQuery = "From " + Person.class.getName();
        Query<Person> query = session.createQuery(selectQuery, Person.class);
        List<Person> personList = query.getResultList();
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    public static void testHqlSelectQueryWhere(Session session) {
        String selectQuery1 = "Select p.firstname FROM " + Person.class.getName() + " p where p.id = :id";
        Optional<String> result = session.createQuery(selectQuery1, String.class)
                .setParameter("id", 4)
                .stream()
                .findFirst();
        if (result.isPresent()) {
            System.out.println("Firstname: " + result.get());
        }
    }

    // TODO. HQL支持使用查询结果构建新的对象(非entity class)
    // Entity和Class必须使用类型的Package全路径 !!
    public static void testHqlWithJoin(Session session) {
        String hqlQuery = "SELECT new entity.SelectionResult(person, sample)" +
                "FROM entity.Person person " +
                "JOIN entity.Sample sample " +
                "ON person.id = sample.id";
        // 指定ResultSet返回的结果数据类型
        Query<SelectionResult> resultQuery = session.createQuery(hqlQuery, SelectionResult.class);
        List<SelectionResult> resultList = resultQuery.getResultList();
        for (SelectionResult result : resultList) {
            System.out.println(result);
        }
    }
}
