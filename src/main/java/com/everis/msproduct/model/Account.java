package com.everis.msproduct.model;
 
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString 
public class Account {
	@Id
	private String id;  
	@NotNull(message = "Account's type must not be null")
	private String acctype;
	@NotNull(message = "Account's titular must not be null")
	private List<String> titular;
	private List<String> firmantecode;
	@Builder.Default
	@Positive
	private Double saldo=0d;

}
