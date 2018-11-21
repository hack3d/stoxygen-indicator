package de.stoxygen.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class IndicatorConfiguration extends Auditable<String>  {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer indicatorConfigurationsId;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false)
    private UUID indicatorConfigurationName;

    @ManyToOne
    private Indicator indicators;

    public Integer getIndicatorConfigurationsId() {
        return indicatorConfigurationsId;
    }

    public UUID getIndicatorConfigurationName() {
        return indicatorConfigurationName;
    }

    public Indicator getIndicators() {
        return indicators;
    }

    public void addIndicator(Indicator indicator) {
        this.indicators = indicator;
    }

    // We need this to generate a uuid before we save the object to database.
    @PrePersist
    public void setIndicatorConfigurationName() {
        this.indicatorConfigurationName = UUID.randomUUID();
    }
}
