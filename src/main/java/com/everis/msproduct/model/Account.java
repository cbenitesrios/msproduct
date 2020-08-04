package com.everis.msproduct.model;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString 	
public class Account {

	@Id
	private String id; 
	private String acctype;
	private String accdescription;
	private String bank; 
	private List<String> titular;
	private List<String> firmantecode;
	@Builder.Default 
	private Double balance=0d;
	@Builder.Default 
	private LocalDateTime creationdate=LocalDateTime.now();
}
