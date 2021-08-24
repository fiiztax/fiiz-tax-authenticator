package com.jaimedantas.fiiztaxauthenticator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class DarfByMonth {
    List<String> month;
    List<BigDecimal> darf;
}
