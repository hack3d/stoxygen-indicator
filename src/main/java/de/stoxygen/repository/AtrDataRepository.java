package de.stoxygen.repository;

import de.stoxygen.model.atr.AtrData;
import de.stoxygen.model.IndicatorConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AtrDataRepository extends CrudRepository<AtrData, Long> {
    List<AtrData> findByIndicatorConfigurationAndAggregate(IndicatorConfiguration indicatorConfiguration, String aggregate);

    @Transactional
    List<AtrData> removeByIndicatorConfigurationAndAggregate(IndicatorConfiguration indicatorConfiguration, String aggregate);
}
