package com.everis.msproduct.model;
 
import java.util.List;

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
public class Product {
	@Id
	private String id;
	@Indexed
	private String productcode;
	private String producttype;
	private String productsubtype;
	private String clientcode;
	private List<String> titular;
	private List<String> firmante;

}
