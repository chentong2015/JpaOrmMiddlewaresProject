package query_hql.select_filter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

// TODO. 定义特定的Filter查询过滤条件, 支持传递参数
@Entity
@Table(name = "t_filter_entity")
@FilterDef(name = "incomeLevelFilter",     // 指定特定名称的参数
        parameters = @ParamDef(name = "incomeLimit", type = Integer.class))
        // defaultCondition = "limit > 0") // 已被废弃，不应再使用
@Filter(name = "incomeLevelFilter",        // 指定过滤名称和细节
        condition = "limit > :incomeLimit")
public class FilterEntity {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private int limit;

    public FilterEntity() {
    }

    public FilterEntity(int id, String name, String type, int limit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.limit = limit;
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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "FilterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", limit=" + limit +
                '}';
    }
}
