package com.everis.msproduct.repository;
 
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.everis.msproduct.model.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditrepo extends ReactiveMongoRepository<Credit, String>{
	Flux<Credit> findByTitular(String titular);
	Mono<Credit> findByIdAndTitular(String id, String titular);
	
}
