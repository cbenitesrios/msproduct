package com.everis.msproduct.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.msproduct.model.Transaction;
import com.everis.msproduct.model.request.MdepositRequest;
import com.everis.msproduct.model.request.MwithdrawRequest;
import com.everis.msproduct.service.IMstransacservice;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactionapi")
public class MstransactionController {
	
	@Autowired
	private IMstransacservice transacservice;
	
	@PostMapping("/withdraw")
	public Mono<Transaction> moneywithdraw(@RequestBody MwithdrawRequest mwithdrawrequest){ 
		return transacservice.moneywithdraw(mwithdrawrequest);
	}
	
	@PostMapping("/deposit")
	public Mono<Transaction> moneydeposit(@RequestBody MdepositRequest mdepositrequest){
		return transacservice.moneydeposit(mdepositrequest);
	}

}
