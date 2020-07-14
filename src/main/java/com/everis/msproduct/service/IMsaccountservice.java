package com.everis.msproduct.service;
 
 
import java.util.List;

import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.request.Createaccrequest;
import com.everis.msproduct.model.request.UpdateaccountRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMsaccountservice {
	
	   Mono<Account> createacc(Createaccrequest cproductrequest); 
	   
	   Flux<Account> findclientacc(List<String> titular);
	  
	   Flux<Account> findacc();

	   Mono<Void> deleteaccount(String id); 
	   
	   Mono<Account> updateaccount(UpdateaccountRequest updateaccount);
}
