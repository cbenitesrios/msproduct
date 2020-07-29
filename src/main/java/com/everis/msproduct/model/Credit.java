package com.everis.msproduct.model; 
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
	private String credittype;
	private String credittypedesc;
	private String bank;
	private String titular;   
	private Double baseline; 
	@Builder.Default 
	private Double consume=0d;
	

}
