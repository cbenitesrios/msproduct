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
public class MwithdrawRequest {
	
	private String id;
	private String prodtype; 
	private List<String> titular;
	private Double amount;
	
}
