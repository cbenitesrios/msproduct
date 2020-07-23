package com.everis.msproduct.model.response;

import java.util.List;

import com.everis.msproduct.model.Account;
import com.everis.msproduct.model.Credit;
import com.everis.msproduct.model.dto.AccountReport;
import com.everis.msproduct.model.dto.CreditReport;

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
  private List<AccountReport> acc;
  private List<CreditReport> cred;
}
