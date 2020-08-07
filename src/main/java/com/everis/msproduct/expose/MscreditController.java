package com.everis.msproduct.expose;
 
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.dto.ErrorDto;
import com.everis.msproduct.model.request.ClientListFind;
import com.everis.msproduct.model.request.Createcreditrequ; 
import com.everis.msproduct.model.request.UpdatecreditRequest;
import com.everis.msproduct.service.IMscreditservice;  
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apicredit")
public class MscreditController {
	
	    @Autowired
	    private IMscreditservice mscreditservice; 
	    private static final String URL_TRANSACT= "http://localhost:8040/apitransaction/checkexpired/";
	    private static final String URL_CLIENT="http://localhost:8020/apiclient";
	    
	    
	      @ExceptionHandler
		  public Mono<ErrorDto> exception(ServerHttpResponse response, Exception request) {
			 response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		    return Mono.just(new ErrorDto(request.getLocalizedMessage(), request.getMessage()));
		  } 
	    
	    
	    @PostMapping("/createcred")
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public Mono<Credit> createClientPer(@RequestBody @Valid Createcreditrequ ccreditrequest) { 
	      return  WebClient.create( URL_TRANSACT +ccreditrequest.getTitular())
	                .get().retrieve().bodyToMono(Boolean.class).filter(expired-> !expired.booleanValue())
	                .switchIfEmpty(Mono.error(new Exception("Have expired consumes")))
	                .then(WebClient.create(URL_CLIENT + "/findclientlist")
	                        .post().body(BodyInserters.fromValue(ClientListFind.builder()
          		                  .clientcode(Arrays.asList(ccreditrequest.getTitular()))
          		                  .clienttype(ccreditrequest.getClienttype())
          		                  .bankassociate(ccreditrequest.getBank())
          		                  .build()))
                                    .retrieve().bodyToMono(Boolean.class).filter(a-> a).switchIfEmpty(Mono.error(new Exception("Titulares incorrectos, revise si tiene cuenta o el tipo de cliente"))))
                    .then( mscreditservice.createcredit(ccreditrequest));
	    }
	    
	    @GetMapping("/findclientcred/{titular}")
	    public Flux<Credit> findclientcred(@PathVariable String titular){
	      return mscreditservice.findclientcredit(titular);
	    }
	    
	    @GetMapping("/findcred/{id}")
	    public Mono<Credit> findcreditbyid(@PathVariable String id){
	      return mscreditservice.findcreditbyid(id);
	    } 
	    
	    @GetMapping("/findcred")
	    public Flux<Credit> findcreditall(){
	      return mscreditservice.findcredit();
	    } 
	    
	    @PutMapping("/updatecredit")
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public Mono<Credit> updatecredit(@RequestBody @Valid UpdatecreditRequest updatecredit) {
	      return mscreditservice.updatecredit(updatecredit);
	    }

	    @DeleteMapping("/deletecredit/{id}")
	    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	    public Mono<Void> deletecredit(@PathVariable String id) {
	      return mscreditservice.deletecredit(id);
	    }

}
