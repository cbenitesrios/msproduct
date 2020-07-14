package com.everis.msproduct.service;
 
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.request.Createcreditrequ;
import com.everis.msproduct.model.request.UpdatecreditRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMscreditservice {
	   Mono<Credit> createcredit(Createcreditrequ ccreditrequest); 
	   
	   Flux<Credit> findclientcredit(String titular);
	  
	   Mono<Credit> findcreditbyid(String id);
	   
	   Flux<Credit> findcredit();

	   Mono<Void>   deletecredit(String id); 
	   
	   Mono<Credit> updatecredit(UpdatecreditRequest updatecredit);
}
