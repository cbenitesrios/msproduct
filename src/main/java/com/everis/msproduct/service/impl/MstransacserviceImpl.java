package com.everis.msproduct.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.Transaction;
import com.everis.msproduct.model.request.Creditconsumerequest;
import com.everis.msproduct.model.request.Creditpaymentrequest;
import com.everis.msproduct.model.request.Updatetransactionreq;
import com.everis.msproduct.model.request.AccdepositRequest;
import com.everis.msproduct.model.request.AccwithdrawRequest;
import com.everis.msproduct.repository.IAccountrepo;
import com.everis.msproduct.repository.ICreditrepo;
import com.everis.msproduct.repository.ITransactionrepo;
import com.everis.msproduct.service.IMstransacservice;  
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@Service
public class MstransacserviceImpl implements IMstransacservice{
	
	@Autowired
	private ITransactionrepo transacrepo;
	@Autowired
	private ICreditrepo creditrepo;
	@Autowired
	private IAccountrepo accountrepo;

	@Override
	public Mono<Transaction> moneywithdraw(AccwithdrawRequest mwithdrawrequest) { 
		return accountrepo.findByIdAndTitularIn(mwithdrawrequest.getId(), mwithdrawrequest.getTitular()) 
					.switchIfEmpty(Mono.error(new Exception("Not found account - mwithdraw"))) 
					.filter(acc-> acc.getSaldo()-mwithdrawrequest.getAmount()>=0)
					.switchIfEmpty(Mono.error(new Exception("Dont have enought money")))  
					.flatMap(acc-> refreshwithdraw(acc,mwithdrawrequest.getAmount())) 
					.flatMap(then-> transacrepo.save(Transaction.builder()
								                    .prodid(mwithdrawrequest.getId())
								                    .prodtype(mwithdrawrequest.getProdtype())
								                    .transtype("WITHDRAW")
								                    .titular(mwithdrawrequest.getTitular().get(0))
								                    .amount(mwithdrawrequest.getAmount())
								                    .postamount(then.getSaldo())
								                    .build())); 
	}

	@Override
	public Mono<Transaction> moneydeposit(AccdepositRequest mdepositrequest) {
		 
		return accountrepo.findByIdAndTitularIn(mdepositrequest.getId(), mdepositrequest.getTitular())
				          .switchIfEmpty(Mono.error(new Exception("Not found account - mdeposit")))
				          .flatMap(acc-> refreshdeposit(acc,mdepositrequest.getAmount()))
				          .flatMap(then-> transacrepo.save(Transaction.builder()
								                    .prodid(then.getId())
								                    .prodtype(mdepositrequest.getProdtype())
								                    .transtype("DEPOSIT")
								                    .titular(mdepositrequest.getTitular().get(0))
								                    .amount(mdepositrequest.getAmount())
								                    .postamount(then.getSaldo())
								                    .build()));
	}
	
	@Override
	public Mono<Transaction> creditpayment(Creditpaymentrequest cpaymentrequest) { 
		return creditrepo.findByIdAndTitular(cpaymentrequest.getId(), cpaymentrequest.getTitular())
				          .switchIfEmpty(Mono.error(new Exception("Not found account - cpayment"))) 
				          .filter(credit -> credit.getConsume()-cpaymentrequest.getAmount()>=0)
				          .switchIfEmpty(Mono.error(new Exception("Cant process the transaction")))
				          .flatMap(cre-> refreshpayment(cre,cpaymentrequest.getAmount()))
				          .flatMap(then-> transacrepo.save(Transaction.builder()
								                    .prodid(then.getId())
								                    .prodtype(cpaymentrequest.getProdtype())
								                    .transtype("PAYMENT")
								                    .titular(cpaymentrequest.getTitular())
								                    .amount(cpaymentrequest.getAmount())
								                    .postamount(then.getBaseline()-then.getConsume())
								                    .build()));
	}
	
	@Override
	public Mono<Transaction> creditconsume(Creditconsumerequest cconsumerequest) { 
		return creditrepo.findByIdAndTitular(cconsumerequest.getId(), cconsumerequest.getTitular())				
				          .switchIfEmpty(Mono.error(new Exception("Not found account - cconsume"))) 
				          .filter(credit -> (credit.getBaseline()-credit.getConsume()-cconsumerequest.getAmount())>=0)
				           .switchIfEmpty(Mono.error(new Exception("Cant process the transaction")))
				          .flatMap(cre-> refreshconsume(cre,cconsumerequest.getAmount()))
				          .flatMap(then-> transacrepo.save(Transaction.builder()
								                    .prodid(then.getId())
								                    .prodtype(cconsumerequest.getProdtype())
								                    .transtype("CONSUME")
								                    .titular(cconsumerequest.getTitular())
								                    .amount(cconsumerequest.getAmount())
								                    .postamount(then.getBaseline()-then.getConsume())
								                    .build()));
	}
	
	@Override
	public Mono<Void> deletetransaction(String id) { 
		return transacrepo.findById(id)
				.switchIfEmpty(Mono.error(new Exception("No encontrado")))
				.flatMap(transacrepo::delete);
	}

	@Override
	public Flux<Transaction> findclienttransaction(String titular) { 
		return transacrepo.findByTitular(titular)
				          .switchIfEmpty(Mono.error(new Exception("Not found transaction")));
	}
	
	@Override
	public Flux<Transaction> findtransaction() { 
		return transacrepo.findAll();
	}
	
	@Override
	public Mono<Transaction> findtransactionbyid(String id) { 
		return transacrepo.findById(id)
				          .switchIfEmpty(Mono.error(new Exception("Not found transaction")));
	}
	
	
	
	
	
	@Override
	public Mono<Transaction> updatetransaction(Updatetransactionreq updatetransacreq) { 
		return transacrepo.findById(updatetransacreq.getId())
				.switchIfEmpty(Mono.error(new Exception("not found")))
				.flatMap(a-> transacrepo.save(Transaction.builder()
	                       .id(a.getId())
	                       .prodid(updatetransacreq.getId())
	                   	   .prodtype(updatetransacreq.getProdtype()) 
	                   	   .transtype(updatetransacreq.getTranstype())
	                   	   .titular(updatetransacreq.getTitular())
	                   	   .amount(updatetransacreq.getAmount())
	                   	   .postamount(updatetransacreq.getPostamount()) 
	                       .build()));
		}
	
	
	
	private Mono<Account> refreshwithdraw(Account account, Double amount) {
		account.setSaldo(account.getSaldo()-amount);
		return accountrepo.save(account);
	}
	private Mono<Account> refreshdeposit(Account account, Double amount) {
		account.setSaldo(account.getSaldo()+amount);
		return accountrepo.save(account);
	}	
	private Mono<Credit> refreshpayment(Credit credit, Double amount) {
		credit.setConsume(credit.getConsume()-amount);
		return creditrepo.save(credit);
	}
	private Mono<Credit> refreshconsume(Credit credit, Double amount) {
		credit.setConsume(credit.getConsume()+ amount);
		return creditrepo.save(credit);
	}


}
