package de.stoxygen.repository;

import de.stoxygen.model.IndicatorConfiguration;
import de.stoxygen.model.adx.AdxData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdxDataRepository extends CrudRepository<AdxData, Long> {
    List<AdxData> findByIndicatorConfigurationAndAggregate(IndicatorConfiguration indicatorConfiguration, String aggregate);

    @Transactional
    List<AdxData> removeByIndicatorConfigurationAndAggregate(IndicatorConfiguration indicatorConfiguration, String aggregate);
}
