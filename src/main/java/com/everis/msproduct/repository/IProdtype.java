package com.everis.msproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.msproduct.model.Prodtype;

import reactor.core.publisher.Mono;

public interface IProdtype extends ReactiveMongoRepository<Prodtype, String> { 
	Mono<Prodtype> findByClienttypeAndProductAndProdtype(String clienttype,String product, String prodtype);
}
