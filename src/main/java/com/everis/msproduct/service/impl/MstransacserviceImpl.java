package com.everis.msproduct.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.Transaction;
import com.everis.msproduct.model.request.Creditpaymentrequest;
import com.everis.msproduct.model.request.MdepositRequest;
import com.everis.msproduct.model.request.MwithdrawRequest;
import com.everis.msproduct.repository.IAccountrepo;
import com.everis.msproduct.repository.ICreditrepo;
import com.everis.msproduct.repository.ITransactionrepo;
import com.everis.msproduct.service.IMstransacservice;

import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@Service
public class MstransacserviceImpl implements IMstransacservice{
	
	@Autowired
	private ITransactionrepo transacrepo;
	@Autowired
	private ICreditrepo creditrepo;
	@Autowired
	private IAccountrepo accountrepo;

	@Override
	public Mono<Transaction> moneywithdraw(MwithdrawRequest mwithdrawrequest) { 
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
	public Mono<Transaction> moneydeposit(MdepositRequest mdepositrequest) {
		 
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
	public Mono<Transaction> creditconsume(Creditconsumerequest cpaymentrequest) { 
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

}
