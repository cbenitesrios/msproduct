package com.everis.msproduct.service;

import com.everis.msproduct.model.Transaction;
import com.everis.msproduct.model.request.Creditpaymentrequest;
import com.everis.msproduct.model.request.MdepositRequest;
import com.everis.msproduct.model.request.MwithdrawRequest;

import reactor.core.publisher.Mono;

public interface IMstransacservice {
  public Mono<Transaction> moneywithdraw(MwithdrawRequest mwithdrawrequest);
  public Mono<Transaction> moneydeposit(MdepositRequest mdepositrequest);
  public Mono<Transaction> creditpayment(Creditpaymentrequest cpaymentrequest);
 
}
