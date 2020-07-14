package com.everis.msproduct.expose;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController; 
import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.request.Createaccrequest;
import com.everis.msproduct.model.request.UpdateaccountRequest;
import com.everis.msproduct.repository.IAccountrepo;
import com.everis.msproduct.service.IMsaccountservice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apiproduct")
public class MsaccountController {

	  @Autowired
	  private IMsaccountservice msproductservice; 
	  
	    @PostMapping("/createacc")
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public Mono<Account> createClientPer(@RequestBody Createaccrequest cacctrequest) {
	      return msproductservice.createacc(cacctrequest);
	    }
	    
	    @GetMapping("/findclientacc/{titular}")
	    public Flux<Account> findclientacc(@PathVariable List<String> titular){
	     return msproductservice.findclientacc(titular);
	    }
	    
	    @GetMapping("/findclientacc")
	    public Flux<Account> findclientacc(){
	     return msproductservice.findacc();
	    } 
	    
	    @PutMapping("/updateaccount")
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public Mono<Account> updateclient(@RequestBody UpdateaccountRequest updateaccount) {
	      return msproductservice.updateaccount(updateaccount);
	    }

	    @DeleteMapping("/deleteaccount/{id}")
	    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	    public Mono<Void> deleteclient(@PathVariable String id) {
	      return msproductservice.deleteaccount(id);
	    }
	
}
