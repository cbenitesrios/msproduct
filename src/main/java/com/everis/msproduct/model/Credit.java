package com.everis.msproduct.model;

import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
public class Credit { 
	@Id
	private String id;	
	@Indexed
	private String productcode;	
	@NotNull(message = "Credit product type must not be null")
	private String producttype;
	@NotNull(message = "Credit product sub type must not be null")
	private String productsubtype;
	@NotNull(message = "Credit client code must not be null")
	private String clientcode;

	

}
