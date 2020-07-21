package com.everis.msproduct.model.response;

import java.util.List;

import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.Credit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BalanceReport {
  private String titularname;
  private List<Account> acc;
  private List<Credit> cred;
}
