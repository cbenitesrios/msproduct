package com.everis.msproduct.expose;
 
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
import com.everis.msproduct.model.Credit; 
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
	  
	    @PostMapping("/createcred")
	    @ResponseStatus(code = HttpStatus.CREATED)
	    public Mono<Credit> createClientPer(@RequestBody Createcreditrequ ccreditrequest) {
	      return mscreditservice.createcredit(ccreditrequest);
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
	    public Mono<Credit> updatecredit(@RequestBody UpdatecreditRequest updatecredit) {
	      return mscreditservice.updatecredit(updatecredit);
	    }

	    @DeleteMapping("/deletecredit/{id}")
	    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	    public Mono<Void> deletecredit(@PathVariable String id) {
	      return mscreditservice.deletecredit(id);
	    }

}
