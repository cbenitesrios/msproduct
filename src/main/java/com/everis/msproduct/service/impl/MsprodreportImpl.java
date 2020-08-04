package com.everis.msproduct.service.impl;  

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.dto.AccountReport;
import com.everis.msproduct.model.dto.CreditReport;
import com.everis.msproduct.model.response.BalanceReport;
import com.everis.msproduct.model.response.BankReport;
import com.everis.msproduct.model.response.TotalReport;
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
					         .reduce((c,d)->  new CreditReport(c.getCredittypedesc(),c.getCredittype(),(c.getConsume()+d.getConsume()))))  
		                     .collectList().map(builder::cred))
			.flatMap(builder->  
			        accountrepo.findByTitular(titular).groupBy(Account::getAcctype)
				    .flatMap(acclist-> 
				             acclist
				             .map(a-> new AccountReport(a.getAccdescription(),a.getAcctype(),a.getBalance()))
				             .reduce((c,d)-> new AccountReport(c.getAcctypedesc(),c.getAcctype(),(c.getBalance()+d.getBalance()))))
			                 .collectList().map(account-> builder.acc(account).build())); 
	}
	
	
	@Override
	public Mono<TotalReport> prodtotalreport(String titular) {    
	return Mono.just(TotalReport.builder().titularname(titular))
			.flatMap(builder->  creditrepo.findByTitular(titular).collectList().map(builder::cred))
			.flatMap(builder->  accountrepo.findByTitular(titular).collectList().map(builder::acc))
			.map(TotalReport.TotalReportBuilder::build);
			 
	}


	@Override
	public Mono<BankReport> prodbankreport(String bank, LocalDate from, LocalDate to) {
		return Mono.just(BankReport.builder().from(from).to(to))
				.flatMap(builder->  creditrepo.findByBankAndCreationdateBetween(bank,from,to.plusDays(1)).collectList().map(builder::creditList))
				.flatMap(builder->  accountrepo.findByBankAndCreationdateBetween(bank,from,to.plusDays(1)).collectList().map(builder::accountList))
				.map(BankReport.BankReportBuilder::build);
	}
}
