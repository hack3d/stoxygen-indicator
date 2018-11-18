package de.stoxygen.repository;

import de.stoxygen.model.MacdData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MacdDataRepository extends CrudRepository<MacdData, String> {

}
