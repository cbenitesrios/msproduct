package com.everis.msproduct.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
public class Prodtype {
	@Id
	private String id;
	private String clienttype;
	private String product;
	@Field(name = "prodtype")
    private String prodtype;
    private String prodtypedesc;
    private Double minbalance;
    private Double maxbalancexmonth;
}
