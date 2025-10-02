package entitymanager.entity.join;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KZ_RECORD")
public class KzRecord {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "kz_record_id", referencedColumnName = "id")
    private Set<KzAddress> kzAddresses = new HashSet<>();

    public KzRecord() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<KzAddress> getKzAddresses() {
        return kzAddresses;
    }

    public void setKzAddresses(Set<KzAddress> kzAddresses) {
        this.kzAddresses = kzAddresses;
    }

    @Override
    public String toString() {
        return "KzRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
