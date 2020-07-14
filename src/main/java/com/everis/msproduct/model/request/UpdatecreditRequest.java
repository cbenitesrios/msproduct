package com.everis.msproduct.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdatecreditRequest {
  private String id;
  private String titular;
  private String credittype;	
  private Double baseline;
  private Double consume;
}
