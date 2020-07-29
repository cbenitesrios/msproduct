package com.everis.msproduct.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.everis.msproduct.exception.CustomException;
import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.request.Createaccrequest;
import com.everis.msproduct.model.request.UpdateaccountRequest;
import com.everis.msproduct.repository.IAccountrepo;
import com.everis.msproduct.repository.IProdtype;
import com.everis.msproduct.service.IMsaccountservice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@Service
public class MsaccountserviceImpl implements IMsaccountservice {

	@Autowired
	private IAccountrepo accountrepo;
	@Autowired
	private IProdtype prodtyperepo;
	
	@Override
	public Mono<Account> createacc(Createaccrequest cproductrequest){ 
		/*Step 1: search by client type - product - product type*/
		return  prodtyperepo.findByClienttypeAndProductAndProdtype(cproductrequest.getClienttype(),
						cproductrequest.getProduct(), cproductrequest.getProducttype()) 
				/*Step 1.1: if empty throws an exception */
						.switchIfEmpty(Mono.error(new Exception(String.format("Cant process account %s",cproductrequest.getProducttype()))))
				/*Step 2: look for request balance if is lower than product minimum balance  */
						.filter(prod-> prod.getMinbalance()<= cproductrequest.getBalance())
						.switchIfEmpty(Mono.error(CustomException.builder()
								.code("1")
								.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.message("Cant process - saldo es menor que el monto de creación")
								.build())) 
				/*Step 2.1: logic for personal account max account per type: 1*/
						.flatMap(prod-> { 
							if(prod.getClienttype().equalsIgnoreCase("PER")){
								return accountrepo.countFindByTitularInAndAcctypeAndBank(cproductrequest.getTitular(), 
										cproductrequest.getProducttype(), cproductrequest.getBank()).filter(acc-> acc==0) 
										.switchIfEmpty(Mono.error(new Exception("No puede haber mas de una cuenta personal")))
										.then(Mono.just(prod));
							}
							return Mono.just(prod); 
				/*Step 3: save account and return*/
						}).flatMap(prod-> accountrepo.save(Account.builder()
										    .titular(cproductrequest.getTitular())
										    .bank(cproductrequest.getBank())
											.balance(cproductrequest.getBalance())
											.accdescription(prod.getProdtypedesc())
										    .acctype(cproductrequest.getProducttype())
											.build())); 
	}

	@Override
	public Flux<Account> findclientacc(List<String> titular) {
		/*Search accounts by titular*/
		return accountrepo.findByTitularIn(titular);
	}

	@Override
	public Flux<Account> findacc() { 
		/*return all accounts */
		return accountrepo.findAll();
	}
	
	@Override
	public Mono<Account> updateaccount(UpdateaccountRequest updateacc) { 
		
		/*find account id -> if empty throws exception -> save new inputs and return*/
		return accountrepo.findById(updateacc.getId())
				.switchIfEmpty(Mono.error(new Exception("not found")))
				.flatMap(a-> accountrepo.save(Account.builder()
	                       .id(a.getId())
	                       .acctype(updateacc.getAcctype())
	                       .bank(updateacc.getBank())
	                       .accdescription(updateacc.getAccdescription())
	                       .titular(updateacc.getTitular())
	                       .firmantecode(updateacc.getFirmantecode())
	                       .balance(updateacc.getBalance())
	                       .build()));
   
	}
	
	@Override
	public Mono<Void> deleteaccount(String id) { 
		/*find account id -> if empty throws exception -> delete account*/
		return accountrepo.findById(id).switchIfEmpty(Mono.error(new Exception("No encontrado"))).flatMap(accountrepo::delete);
 	}

	@Override
	public Mono<Account> findaccbyid(String id) {
		/*find account id and return*/
		return accountrepo.findById(id).switchIfEmpty(Mono.error(new Exception("No encontrado")));
	}

}
