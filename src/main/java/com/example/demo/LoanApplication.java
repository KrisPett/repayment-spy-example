package com.example.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int principal;
    private int termInMonths;
    private BigDecimal repayment;
    private Boolean approved;

    public BigDecimal getInterestRate() {
//		RestTemplate restTemplate = new RestTemplate();
//		return restTemplate.getForObject("http://loans.virtualpairprogrammers.com/getInterestRate", BigDecimal.class);
        return new BigDecimal(20);
    }

}
