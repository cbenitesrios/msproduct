package com.everis.msproduct.expose;

import java.util.List; 
import javax.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.msproduct.exception.CustomException;
import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.dto.ErrorDto;
import com.everis.msproduct.model.request.ClientListFind;
import com.everis.msproduct.model.request.Createaccrequest;
import com.everis.msproduct.model.request.UpdateaccountRequest; 
import com.everis.msproduct.service.IMsaccountservice; 

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apiaccount")
public class MsaccountController {

	    @Autowired
	    private IMsaccountservice msproductservice; 
	    
	    private static final String URL_CLIENT="http://localhost:8020/apiclient";
	    
	    @ExceptionHandler
	    public Mono<ErrorDto> exception(CustomException exception) {  
	      return Mono.just(new ErrorDto(exception.getStatus().toString(), exception.getMessage()));
	    } 
	    
	    @PostMapping("/createacc")
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public Mono<Account> createClientPer(@RequestBody @Valid Createaccrequest cacctrequest){
	    	return  WebClient.create(URL_CLIENT + "/findclientlist")
                    .post().body(BodyInserters.fromValue(ClientListFind.builder()
                    		                  .clientcode(cacctrequest.getTitular())
                    		                  .clienttype(cacctrequest.getClienttype())
                    		                  .bankassociate(cacctrequest.getBank())
                    		                  .build()))
                                              .retrieve().bodyToMono(Boolean.class).filter(a-> a).switchIfEmpty(Mono.error(new Exception("Titulares incorrectos, revise si tiene cuenta o el tipo de cliente")))
                     .then(msproductservice.createacc(cacctrequest));
	    }
	    
	    @GetMapping("/findclientacc/{titular}")
	    public Flux<Account> findclientacc(@PathVariable List<String> titular){
	      return msproductservice.findclientacc(titular);
	    }
	    
	    @GetMapping("/findacc")
	    public Flux<Account> findacc(){
	      return msproductservice.findacc();
	    }
	    
	    @GetMapping("/findacc/{id}")
	    public Mono<Account> findaccbyid(@PathVariable String id){
	      return msproductservice.findaccbyid(id);
	    } 
	    
	    @PutMapping("/updateaccount")
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public Mono<Account> updateaccount(@RequestBody @Valid UpdateaccountRequest updateaccount) {
	      return msproductservice.updateaccount(updateaccount);
	    }

	    @DeleteMapping("/deleteaccount/{id}")
	    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	    public Mono<Void> deleteaccount(@PathVariable String id) {
	      return msproductservice.deleteaccount(id);
	    }
	
}
