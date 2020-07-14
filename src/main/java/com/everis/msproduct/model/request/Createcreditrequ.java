package com.everis.msproduct.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Createcreditrequ {
   private String titular;
   private String clienttype;
   private String credittype;
   private Double   baseline;
}
