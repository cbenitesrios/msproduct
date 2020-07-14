package com.everis.msproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.msproduct.model.Transaction;

public interface ITransactionrepo extends ReactiveMongoRepository<Transaction, String>{

}
