package com.openpayd.task.dao;

import com.openpayd.task.model.AmountConversion;

import java.time.LocalDate;
import java.util.List;

public interface AmountConversionPersistenceDao {

    List<AmountConversion> fetchAllAmountConversionByParams(String transactionId, LocalDate transactionDate, int pageNumber, int pageLength);
}
