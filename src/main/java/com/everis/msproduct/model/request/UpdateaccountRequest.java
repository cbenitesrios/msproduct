package com.everis.msproduct.model.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateaccountRequest{ 
	private String id;   
	private String acctype; 
	private List<String> titular;
	private List<String> firmante;
}
