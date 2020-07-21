package com.everis.msproduct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto { 
	public String producttype;
	public Double saldo; 
	public Double baseline; 
	public Double consume;
}
