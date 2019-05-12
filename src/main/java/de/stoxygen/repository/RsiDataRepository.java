package de.stoxygen.repository;

import de.stoxygen.model.IndicatorConfiguration;
import de.stoxygen.model.rsi.RsiData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RsiDataRepository extends CrudRepository<RsiData, Long> {
    List<RsiData> findByIndicatorConfigurationAndAggregate(IndicatorConfiguration indicatorConfiguration, String aggregate);

    @Transactional
    List<RsiData> removeByIndicatorConfigurationAndAggregate(IndicatorConfiguration indicatorConfiguration, String aggregate);
}
