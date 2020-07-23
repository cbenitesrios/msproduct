package com.everis.msproduct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditReport {
	private String credittypedesc;
	private String credittype;
	private Double consume;
}
