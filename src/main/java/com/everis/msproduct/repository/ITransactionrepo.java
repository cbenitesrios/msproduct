package com.everis.msproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.msproduct.model.Transaction;

import reactor.core.publisher.Flux;

public interface ITransactionrepo extends ReactiveMongoRepository<Transaction, String>{

	Flux<Transaction> findByTitular(String titular);
}
