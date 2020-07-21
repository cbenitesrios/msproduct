package com.everis.msproduct.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero; 
import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

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
@Validated
public class Credit { 
	@Id
	private String id; 
	@NotNull(message = "Credit client code must not be null")
	private String titular;  
	@NotNull(message = "Credit credi type must not be null")
	private String credittype; 
	@NotNull(message = "Credit base line must not be null")
	@Positive
	private Double baseline; 
	@Builder.Default 
	private Double consume=0d;
	

}
