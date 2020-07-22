package com.everis.msproduct.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.msproduct.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAccountrepo extends ReactiveMongoRepository<Account, String>{
 
	Mono<Long> countFindByTitularInAndAcctype(List<String> titular, String acctype);
	Flux<Account> findByTitularIn(List<String> titular);
	Flux<Account> findByTitular(String titular); 
	Mono<Account> findByIdAndTitularIn(String id, List<String> titular);
	
}
