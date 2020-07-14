package com.everis.msproduct.service;

import com.everis.msproduct.model.Transaction;
import com.everis.msproduct.model.request.Creditconsumerequest;
import com.everis.msproduct.model.request.Creditpaymentrequest;
import com.everis.msproduct.model.request.Updatetransactionreq;
import com.everis.msproduct.model.request.AccdepositRequest;
import com.everis.msproduct.model.request.AccwithdrawRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMstransacservice {
  Mono<Transaction> moneywithdraw(AccwithdrawRequest mwithdrawrequest);
  Mono<Transaction> moneydeposit(AccdepositRequest mdepositrequest);
  Mono<Transaction> creditpayment(Creditpaymentrequest cpaymentrequest);
  Mono<Transaction> creditconsume(Creditconsumerequest cconsumerequest);
  Mono<Void> deletetransaction(String id); 
  Flux<Transaction> findtransaction();
  Flux<Transaction> findclienttransaction(String titular);
  Mono<Transaction> findtransactionbyid(String id);
  Mono<Transaction> updatetransaction(Updatetransactionreq updatetransactionreq);
 
}
