package com.everis.msproduct.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController; 
import com.everis.msproduct.model.Transaction;
import com.everis.msproduct.model.request.Creditconsumerequest;
import com.everis.msproduct.model.request.Creditpaymentrequest; 
import com.everis.msproduct.model.request.Updatetransactionreq;
import com.everis.msproduct.model.request.AccdepositRequest;
import com.everis.msproduct.model.request.AccwithdrawRequest;
import com.everis.msproduct.service.IMstransacservice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactionapi")
public class MstransactionController {
	
	@Autowired
	private IMstransacservice transacservice;
	
	@PostMapping("/withdraw")
	public Mono<Transaction> moneywithdraw(@RequestBody AccwithdrawRequest mwithdrawrequest){ 
		return transacservice.moneywithdraw(mwithdrawrequest);
	}
	
	@PostMapping("/deposit")
	public Mono<Transaction> moneydeposit(@RequestBody AccdepositRequest mdepositrequest){
		return transacservice.moneydeposit(mdepositrequest);
	}

	@PostMapping("/payment")
	public Mono<Transaction> creditpayment(@RequestBody Creditpaymentrequest cpaymentrequest){
		return transacservice.creditpayment(cpaymentrequest);
	}

	@PostMapping("/consume")
	public Mono<Transaction> creditconsume(@RequestBody Creditconsumerequest cconsumerequest){
		return transacservice.creditconsume(cconsumerequest);
	}
	
	@DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Mono<Void> deletetransaction(@PathVariable String id){
		return transacservice.deletetransaction(id);
	}
	
	@GetMapping("/find")
	public Flux<Transaction> findtransaction(){
	      return transacservice.findtransaction();
    }
	@GetMapping("/find/{id}")
	public Mono<Transaction> findtransactionbyid(@PathVariable String id){
	      return transacservice.findtransactionbyid(id);
    }
	
	@GetMapping("/findbytitular/{titular}")
	public Flux<Transaction> findtitulartransaction(@PathVariable String titular){
	      return transacservice.findclienttransaction(titular);
    }
	
    @PutMapping("/updateaccount")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Transaction> updatetransaction(@RequestBody Updatetransactionreq updatetransactionreq) {
      return transacservice.updatetransaction(updatetransactionreq);
    }


}
