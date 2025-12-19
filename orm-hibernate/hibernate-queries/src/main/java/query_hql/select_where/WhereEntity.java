package query_hql.select_where;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

// TODO. Where clause will be added to any query or subquery to this entity
//  Where查询条件不支持parameters参数, 无法根据需求Enable条件
@Entity
@Table(name = "t_where_entity")
@Where(clause = "type = 'GOOD'")
public class WhereEntity {

    @Id
    private int id;

    @Column
    @Where(clause = "name like 'na%'")
    private String name;

    @Column
    private String type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @Where(clause = "deleted = false")
    private List<Phone> phones = new ArrayList<>();

    public WhereEntity() {
    }

    public WhereEntity(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "FilterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
