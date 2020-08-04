package com.everis.msproduct.service;

import java.time.LocalDate;

import com.everis.msproduct.model.response.BalanceReport;
import com.everis.msproduct.model.response.BankReport;
import com.everis.msproduct.model.response.TotalReport;

import reactor.core.publisher.Mono;

public interface IMsprodreport {
	
	Mono<BalanceReport> prodbalancereport(String titular);
	Mono<TotalReport> prodtotalreport(String titular);
	Mono<BankReport> prodbankreport(String bank, LocalDate from, LocalDate to);
}
