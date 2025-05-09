package com.test.transactionservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.annotation.Version;

@Entity
public class Transaction {
    @Id
    @Version
    private Long id;
    private Integer transactionAmount;
    private Integer totalAmount;

    public Transaction() {
        this(null, null, null);
    }

    public Transaction(Long id, Integer transactionAmount, Integer totalAmount) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Integer transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "totalAmount=" + totalAmount +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}