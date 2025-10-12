package hibernate.framework.apis.query_hql;

import hibernate.framework.apis.query.hql.entity.Person;
import hibernate.framework.apis.query.hql.entity.Sample;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;


public class HqlQuerySelect {

    // TODO. 使用Session来创建HQL查询语句
    // 1. 当没有设置entity name时，.getSimpleName()和.getName()都能工作
    // 2. 当设置entity name之后，HQL查询语句必须使用全路径名称.getName()
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

    public class SelectionResult {

        private Person person;
        private Sample sample;

        public SelectionResult(Person person, Sample sample) {
            this.person = person;
            this.sample = sample;
        }
    }
}
