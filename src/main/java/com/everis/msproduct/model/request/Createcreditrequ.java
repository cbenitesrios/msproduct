package com.everis.msproduct.model.request; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero; 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Createcreditrequ {
 
    @NotNull
    private String titular; 
	@NotNull(message = "bank - null")
	private String bank;
	@NotNull(message = "client type - null")
	private String clienttype;
	@NotNull(message = "product- null")
	private String product;
	@NotNull(message = "product type- null")
	private String producttype;
	@Positive(message = "baseline - null")
	private Double baseline;
   
   
   
   
}
