package com.openpayd.task.dao;

import com.openpayd.task.model.AmountConversion;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class AmountConversionPersistenceDaoImpl implements AmountConversionPersistenceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AmountConversion> fetchAllAmountConversionByParams(String transactionId, LocalDate transactionDate, int pageLength, int pageNumber) {
        String hql = "select amountConversion from AmountConversion amountConversion where 1=1";

        if(StringUtils.hasText(transactionId)){
            hql += " and amountConversion.transactionId = :transactionId ";
        }

        if(transactionDate != null){
            hql += " and (" +
                    " year(amountConversion.transactionDate) = year(:transactionDate) " +
                    " and month(amountConversion.transactionDate) = month(:transactionDate) " +
                    " and day(amountConversion.transactionDate) = day(:transactionDate) " +
                    ") ";
        }

        Query query = entityManager.createQuery(hql);

        if(StringUtils.hasText(transactionId)){
            query.setParameter("transactionId", transactionId);
        }

        if(transactionDate != null){
            query.setParameter("transactionDate", transactionDate);
        }

        query.setMaxResults(pageLength);
        query.setFirstResult(pageNumber * pageLength);

        return query.getResultList();
    }
}
