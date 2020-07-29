package com.everis.msproduct.model.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Createaccrequest {
	@NotNull
	private List<String> titular;
	private List<String> firmante;
	@NotNull(message = "bank - null")
	private String bank;
	@NotNull(message = "client type - null")
	private String clienttype;
	@NotNull(message = "product- null")
	private String product;
	@NotNull(message = "product type- null")
	private String producttype;
	@PositiveOrZero(message = "balance - null")
	private Double balance;
}
