package com.test.transactionservice.repository;

import com.test.transactionservice.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(value = "SELECT coalesce(max(id), 0) FROM Transaction")
    public Long getMaxId();
}
