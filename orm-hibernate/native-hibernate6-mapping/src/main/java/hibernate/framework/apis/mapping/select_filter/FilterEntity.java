package hibernate.framework.apis.mapping.select_filter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_filter_entity")
public class FilterEntity {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String type;
}
