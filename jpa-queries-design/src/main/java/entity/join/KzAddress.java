package entity.join;

import jakarta.persistence.*;

@Entity
@Table(name = "KZ_ADDRESS")
public class KzAddress {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kz_record_id", nullable = false)
    private KzRecord kzRecord;

    public KzAddress() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public KzRecord getKzRecord() {
        return kzRecord;
    }

    public void setKzRecord(KzRecord kzRecord) {
        this.kzRecord = kzRecord;
    }

    @Override
    public String toString() {
        return "KzAddress{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", kzRecord=" + kzRecord +
                '}';
    }
}
