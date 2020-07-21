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
		switch (cproductrequest.getClienttype()) {
		case "PER":
			return  prodtyperepo.findByClienttypeAndProductAndProdtype("PER",
					"ACC", "ACC1")
					.switchIfEmpty(Mono.error(new Exception("Cant process personal account")))
					.flatMap(prod->
					accountrepo.findByTitularInAndAcctype(cproductrequest.getTitular()
							,cproductrequest.getProducttype())
					.switchIfEmpty(Mono.error(new Exception("ERROR")))
					        .filter(acc-> acc.getTitular().isEmpty())
					        .thenReturn(prod))
					.flatMap(a -> accountrepo.save(Account.builder()
						    .titular(cproductrequest.getTitular())
							.saldo(cproductrequest.getSaldo())
							.accdescription(a.getProdtypedesc())
						    .acctype(cproductrequest.getProducttype())
							.build())); 
		case "EMP":
			return prodtyperepo.findByClienttypeAndProductAndProdtype("EMP",
					cproductrequest.getProduct(), cproductrequest.getProducttype())
					.switchIfEmpty(Mono.error(new Exception("Cant process empresarial account")))
					.flatMap(type-> accountrepo.save(Account.builder()
						    .titular(cproductrequest.getTitular())
							.acctype(cproductrequest.getProducttype())
							.saldo(cproductrequest.getSaldo())
							.firmantecode(cproductrequest.getFirmante())
							.build()) ) 
					;
		case "PVIP":
			return cproductrequest.getProducttype().equalsIgnoreCase("ACC3")?accountrepo.save(Account.builder()
				    .titular(cproductrequest.getTitular())
					.acctype(cproductrequest.getProducttype())
					.saldo(cproductrequest.getSaldo())
					.firmantecode(cproductrequest.getFirmante())
					.build()):Mono.error(new Exception("No se pudo crear account"));
		case "PYME":
			return cproductrequest.getProducttype().equalsIgnoreCase("ACC3")?accountrepo.save(Account.builder()
				    .titular(cproductrequest.getTitular())
					.acctype(cproductrequest.getProducttype())
					.saldo(cproductrequest.getSaldo())
					.firmantecode(cproductrequest.getFirmante())
					.build()):Mono.error(new Exception("No se pudo crear account"));
		case "CORP":
			return cproductrequest.getProducttype().equalsIgnoreCase("ACC3")?accountrepo.save(Account.builder()
				    .titular(cproductrequest.getTitular())
					.acctype(cproductrequest.getProducttype())
					.saldo(cproductrequest.getSaldo())
					.firmantecode(cproductrequest.getFirmante())
					.build()):Mono.error(new Exception("No se pudo crear account")); 
		default:
			return Mono.error(new Exception("Error al crear")); 
		} 
 
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
