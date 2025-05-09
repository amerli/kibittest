package com.test.transactionservice.controller;

import com.test.transactionservice.model.Transaction;
import com.test.transactionservice.notification.KafkaSender;
import com.test.transactionservice.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class TransactionController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private KafkaSender kafkaSender;

    @GetMapping("/api/transaction/{transactionAmount}")
    public ResponseEntity<String> processTransaction(@PathVariable Integer transactionAmount) {
        LOGGER.info("Transaction amount: "+transactionAmount);
        return addNewTransaction(transactionAmount);
    }

    @Transactional
    private ResponseEntity<String> addNewTransaction(Integer transactionAmount) {
        long maxId = transactionRepository.getMaxId();
        Optional<Transaction> lastEntryOpt = transactionRepository.findById(maxId);
        int totalAmount = lastEntryOpt.map(transaction -> transaction.getTotalAmount() + transactionAmount)
                .orElse(transactionAmount);
        if (totalAmount >= 0) {
            Transaction newTransaction = new Transaction(
                    maxId+1, transactionAmount, totalAmount);
            try {
                transactionRepository.save(newTransaction);
                kafkaSender.sendMessage(newTransaction.toString(), "notification-1");
            } catch (OptimisticLockingFailureException e) {
                // duplicate transaction problem
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict on transaction insert");
            } catch (Exception e) {
                // unexpected exception
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unexpected exception");
            }
            return ResponseEntity.status(HttpStatus.OK).body(newTransaction.toString());
        } else {
            // balance problem
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The total amount on the account cannot cover the transaction");
        }
    }
}
