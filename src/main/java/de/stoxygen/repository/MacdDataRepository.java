package de.stoxygen.repository;

import de.stoxygen.model.IndicatorConfiguration;
import de.stoxygen.model.MacdData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MacdDataRepository extends CrudRepository<MacdData, String> {
    List<MacdData> findByIndicatorConfigurationAndAggregate(IndicatorConfiguration indicatorConfiguration, String aggregate);
}
