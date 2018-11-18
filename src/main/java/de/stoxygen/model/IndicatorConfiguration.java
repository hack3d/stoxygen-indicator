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

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, columnDefinition = "BINARY(16)")
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

    @PrePersist
    public void setIndicatorConfigurationName() {
        this.indicatorConfigurationName = UUID.randomUUID();
    }
}
