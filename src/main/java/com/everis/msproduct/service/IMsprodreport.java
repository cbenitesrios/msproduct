package com.everis.msproduct.service;

import com.everis.msproduct.model.response.BalanceReport;

import reactor.core.publisher.Mono;

public interface IMsprodreport {
	
	Mono<BalanceReport> prodbalancereport(String titular);
	Mono<BalanceReport> prodtotalreport(String titular);
}
