package com.everis.msproduct.model.request;

import java.util.List;  
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString 
public class ClientListFind { 
	private List<String> clientcode;
	private String clienttype;
	private String bankassociate;
}
