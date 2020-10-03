package com.openpayd.task.dao;


import com.openpayd.task.model.AmountConversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountConversionDao extends JpaRepository<AmountConversion, String>, AmountConversionPersistenceDao {
}
