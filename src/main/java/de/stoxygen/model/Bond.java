package de.stoxygen.model;

import javax.persistence.*;

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

    public Bond() {}

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

}
