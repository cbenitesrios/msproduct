package com.everis.msproduct.model.request;
 

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Createaccrequest {
	private List<String> titular;
	private List<String> firmante;
	private String clienttype; 
	private String product;
	private String producttype;
	private Double saldo;
}
