package com.everis.msproduct.service.impl;
 

import java.util.List;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
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
		return  prodtyperepo.findByClienttypeAndProductAndProdtype(cproductrequest.getClienttype(),
						cproductrequest.getProduct(), cproductrequest.getProducttype()) 
						.switchIfEmpty(Mono.error(new Exception(String.format("Cant process account %s",cproductrequest.getProducttype()))))
						.filter(prod-> prod.getMinbalance()<= cproductrequest.getSaldo())
						.switchIfEmpty(Mono.error(new Exception("Cant process - saldo es menor que el monto de creación"))) 
						.flatMap(prod-> { 
							if(prod.getClienttype().equalsIgnoreCase("PER")){
								return accountrepo.countFindByTitularInAndAcctype(cproductrequest.getTitular(), 
										cproductrequest.getProducttype()).filter(acc-> acc==0) 
										.switchIfEmpty(Mono.error(new Exception("No puede haber mas de una cuenta personal")))
										.then(Mono.just(prod));
							}
							return Mono.just(prod); 
						}).flatMap(prod-> accountrepo.save(Account.builder()
										    .titular(cproductrequest.getTitular())
											.saldo(cproductrequest.getSaldo())
											.accdescription(prod.getProdtypedesc())
										    .acctype(cproductrequest.getProducttype())
											.build())); 
	}

	@Override
	public Flux<Account> findclientacc(List<String> titular) { 
		return accountrepo.findByTitularIn(titular);
	}

	@Override
	public Flux<Account> findacc() { 
		return accountrepo.findAll();
	}
	
	@Override
	public Mono<Account> updateaccount(UpdateaccountRequest updateacc) { 
		return accountrepo.findById(updateacc.getId())
				.switchIfEmpty(Mono.error(new Exception("not found")))
				.flatMap(a-> accountrepo.save(Account.builder()
	                       .id(a.getId())
	                       .titular(updateacc.getTitular())
	                       .acctype(updateacc.getAcctype())
	                       .saldo(updateacc.getSaldo())
	                       .firmantecode(updateacc.getFirmante())
	                       .build()));
   
	}
	
	@Override
	public Mono<Void> deleteaccount(String id) { 
		return accountrepo.findById(id).switchIfEmpty(Mono.error(new Exception("No encontrado"))).flatMap(accountrepo::delete);
 	}

	@Override
	public Mono<Account> findaccbyid(String id) {
		return accountrepo.findById(id).switchIfEmpty(Mono.error(new Exception("No encontrado")));
	}

}
