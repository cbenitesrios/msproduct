package com.everis.msproduct.service.impl;
 
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.everis.msproduct.model.Account; 
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
	return 	null;
	}
	 

}
