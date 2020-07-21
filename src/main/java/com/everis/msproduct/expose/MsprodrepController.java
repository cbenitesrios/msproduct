package com.everis.msproduct.expose;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.msproduct.model.response.BalanceReport;
import com.everis.msproduct.service.IMsprodreport;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apiprodreport")
public class MsprodrepController {
	
	@Autowired
	private IMsprodreport prodreport;
	
	@GetMapping("/clientreport/{titular}")
	public Mono<BalanceReport> clientreport(@PathVariable String titular ){ 
		return prodreport.prodbalancereport(titular);
	}
	
	

}
