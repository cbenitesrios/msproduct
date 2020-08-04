package com.everis.msproduct.expose;
 

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everis.msproduct.model.response.BalanceReport;
import com.everis.msproduct.model.response.BankReport;
import com.everis.msproduct.model.response.TotalReport;
import com.everis.msproduct.service.IMsprodreport; 
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apiprodreport")
public class MsprodrepController {
	
	@Autowired
	private IMsprodreport prodreport;
	
	
	/*Balance acumulado de los saldos que tenga un titular en cuentas y creditos*/
	@GetMapping("/clientbalance/{titular}")
	public Mono<BalanceReport> clientreport(@PathVariable String titular ){ 
		return prodreport.prodbalancereport(titular);
	}
	
	/*Devuelve todos los productos que posea el titular*/
	@GetMapping("/clientproduct/{titular}")
	public Mono<TotalReport> clientallproducts(@PathVariable String titular){ 
		return prodreport.prodtotalreport(titular);
	}

	@GetMapping("/findbytitular/{bank}") 
	public Mono<BankReport> findtitulartransaction(@PathVariable String bank,
		  @RequestParam(name = "from", defaultValue ="1980-01-01" ) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
  	      @RequestParam(name = "to", defaultValue = "4000-12-12") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to){
	      return prodreport.prodbankreport(bank, from, to);
    }
	
}
