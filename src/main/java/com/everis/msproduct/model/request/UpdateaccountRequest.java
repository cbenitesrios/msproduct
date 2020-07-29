package com.everis.msproduct.model.request;

import java.util.List; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero; 
import lombok.AllArgsConstructor; 
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateaccountRequest{ 
	
	@NotNull(message = "ID - null")
	private String id;     
	@NotNull(message = "titular - null")
	private List<String> titular;
	private List<String> firmantecode;
	@NotNull(message = "bank - null")
	private String bank;  
	@NotNull(message = "acc type- null")
	private String acctype;
	private String accdescription;
	@PositiveOrZero(message = "saldo - null")
	private Double balance;  
}
