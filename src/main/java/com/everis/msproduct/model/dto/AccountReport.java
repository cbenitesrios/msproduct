package com.everis.msproduct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountReport {
	private String acctypedesc;
	private String acctype;
	private Double saldo;
}
