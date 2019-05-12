package de.stoxygen.repository;

import de.stoxygen.model.Bond;
import de.stoxygen.model.IndicatorBondSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IndicatorBondSettingRepository extends CrudRepository<IndicatorBondSetting, Integer> {
    List<IndicatorBondSetting> findByBonds(Bond bonds);
    IndicatorBondSetting findFirstByBonds(Bond bonds);
    IndicatorBondSetting findByIndicatorKeyAndBonds(String indicatorKey, Bond bonds);
}
