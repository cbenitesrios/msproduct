package com.everis.msproduct.service.impl;  

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.dto.AccountReport;
import com.everis.msproduct.model.dto.CreditReport;
import com.everis.msproduct.model.response.BalanceReport;
import com.everis.msproduct.repository.IAccountrepo;
import com.everis.msproduct.repository.ICreditrepo;
import com.everis.msproduct.service.IMsprodreport; 
import reactor.core.publisher.Mono;

@Service
public class MsprodreportImpl implements IMsprodreport{

	@Autowired
	ICreditrepo creditrepo;
	@Autowired
	IAccountrepo accountrepo;
	
	@Override
	public Mono<BalanceReport> prodbalancereport(String titular) {   
		 
	return 	Mono.just(BalanceReport.builder().titularname(titular))
			.flatMap(builder-> 
			        creditrepo.findByTitular(titular).groupBy(Credit::getCredittype)
					.flatMap(credlist-> 
					         credlist
					         .map(a-> new CreditReport(a.getCredittype(),a.getCredittypedesc(),a.getConsume()))
					         .reduce((c,d)->  new CreditReport(c.getCredittype(),c.getCredittypedesc(),(c.getConsume()+d.getConsume()))))  
		                     .collectList().map(builder::cred))
			.flatMap(builder->  
			        accountrepo.findByTitular(titular).groupBy(Account::getAcctype)
				    .flatMap(acclist-> 
				             acclist
				             .map(a-> new AccountReport(a.getAccdescription(),a.getAcctype(),a.getBalance()))
				             .reduce((c,d)-> new AccountReport(c.getAcctypedesc(),c.getAcctype(),(c.getBalance()+d.getBalance()))))
			                 .collectList().map(account-> builder.acc(account).build()))
			; 
	}
}
