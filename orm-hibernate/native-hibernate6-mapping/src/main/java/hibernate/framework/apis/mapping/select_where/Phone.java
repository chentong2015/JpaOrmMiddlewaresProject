package hibernate.framework.apis.mapping.select_where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_phone_entity")
public class Phone {

    @Id
    private Integer id;

    @Column
    private boolean deleted;

    @Column
    private String number;

    public Phone() {
    }

    public Phone(Integer id, boolean deleted, String number) {
        this.id = id;
        this.deleted = deleted;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", deleted=" + deleted +
                ", number='" + number + '\'' +
                '}';
    }
}
