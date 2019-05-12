package de.stoxygen.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Bond extends Auditable<String> {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer bondsId;

    @Column(nullable = false)
    private String bondName;

    @Column(nullable = false)
    private String bondIsin;

    @Column(nullable = false)
    private Boolean bondState;

    @Column(nullable = false)
    private String cryptoPair;

    @ManyToMany(mappedBy = "bonds", fetch = FetchType.EAGER)
    private Set<Exchange> exchanges = new HashSet<>();

    public Bond() {}

    public Set<Exchange> getExchanges() {
        return exchanges;
    }

    public Integer getBondsId() {
        return bondsId;
    }

    public String getBondName() {
        return bondName;
    }

    public String getBondIsin() {
        return bondIsin;
    }

    public Boolean getBondState() {
        return bondState;
    }

    public String getCryptoPair() {
        return cryptoPair;
    }

    @Override
    public String toString() {
        return "Bond{" +
                "bondsId=" + bondsId +
                ", bondName='" + bondName + '\'' +
                ", bondIsin='" + bondIsin + '\'' +
                ", bondState=" + bondState +
                ", cryptoPair='" + cryptoPair + '\'' +
                ", exchanges=" + exchanges +
                '}';
    }
}
