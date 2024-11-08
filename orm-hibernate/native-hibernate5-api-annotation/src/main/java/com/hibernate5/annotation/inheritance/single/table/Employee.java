package com.hibernate5.annotation.inheritance.single.table;

import javax.persistence.*;

@Entity
@Table(name = "Employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 添加用于取分的column字段名称，根据值确定类型
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
// 确定当前super class的用于区分的值
@DiscriminatorValue(value = "EMP")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}