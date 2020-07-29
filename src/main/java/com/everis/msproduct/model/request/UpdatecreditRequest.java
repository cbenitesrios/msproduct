package com.everis.msproduct.model.request;
 
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
public class UpdatecreditRequest {
	    
  @NotNull(message = "ID - null")
  private String id;
  @NotNull(message = "bank - null")
  private String bank;
  @NotNull(message = "titular - null")
  private String titular;
  @NotNull(message = "credit type- null")
  private String credittype;	 
  private String credittypedesc;
  @PositiveOrZero(message = "baseline - null")
  private Double baseline;
  @PositiveOrZero(message = "consume - null")
  private Double consume;
  
}
