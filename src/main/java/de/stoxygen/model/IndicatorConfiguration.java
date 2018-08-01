package de.stoxygen.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class IndicatorConfiguration extends Auditable<String>  {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer indicatorConfigurationsId;

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
}
