package com.everis.msproduct.service.impl;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.request.Createcreditrequ;
import com.everis.msproduct.model.request.UpdatecreditRequest;
import com.everis.msproduct.repository.ICreditrepo;
import com.everis.msproduct.service.IMscreditservice; 

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MscreditserviceImpl implements IMscreditservice{

	@Autowired
	private ICreditrepo creditrepo;
	
	@Override
	public Mono<Credit> createcredit(Createcreditrequ ccreditrequest) { 
		return creditrepo.save(Credit.builder()
				               .baseline(ccreditrequest.getBaseline())
				               .titular(ccreditrequest.getTitular())
				               .credittype(ccreditrequest.getCredittype())
				               .build());
	}

	@Override
	public Flux<Credit> findclientcredit(String titular) {
		return creditrepo.findByTitular(titular); 
	}

	@Override
	public Flux<Credit> findcredit() { 
		return creditrepo.findAll();
	}

	@Override
	public Mono<Void> deletecredit(String id) { 
		return creditrepo.findById(id)
				         .switchIfEmpty(Mono.error(new Exception("No encontrado")))
				         .flatMap(creditrepo::delete);
	}

	@Override
	public Mono<Credit> updatecredit(UpdatecreditRequest updatecredit) {
		return creditrepo.findById(updatecredit.getId())
				.switchIfEmpty(Mono.error(new Exception("not found")))
				.flatMap(a->{
							if(updatecredit.getBaseline()>updatecredit.getConsume())
								return Mono.error(new Exception("Baseline must be higher than consume"));
							return creditrepo.save(Credit.builder()
						   .id(a.getId())
	                       .titular(updatecredit.getTitular())
	                       .baseline(updatecredit.getBaseline())
	                       .consume(updatecredit.getConsume())
	                       .build());});
	}

	@Override
	public Mono<Credit> findcreditbyid(String id) { 
		return creditrepo.findById(id).switchIfEmpty(Mono.error(new Exception("No encontrado")));
	}

}
