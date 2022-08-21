package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class RepaymentAmountTests {

    LoanCalculatorController controller;

    LoanApplication loanApplication = spy(LoanApplication.class);
    LoanRepository repository = mock(LoanRepository.class);
    JavaMailSender mailSender = mock(JavaMailSender.class);
    RestTemplate restTemplate = mock(RestTemplate.class);

    @BeforeEach
    public void setup() {
        controller = new LoanCalculatorController(repository, mailSender, restTemplate);
    }

    @Test
    public void test1YearLoanWholePounds() {
        given(loanApplication.getPrincipal()).willReturn(1200);
        given(loanApplication.getTermInMonths()).willReturn(12);
        given(loanApplication.getInterestRate()).willReturn(new BigDecimal(10));

        when(controller.processNewLoanApplication(loanApplication)).then(InvocationOnMock::callRealMethod);

        assertEquals(new BigDecimal(110), loanApplication.getRepayment());
    }

    @Test
    public void test2YearLoanWholePounds() {
        //given
        loanApplication.setPrincipal(1200);
        loanApplication.setTermInMonths(24);
        doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();

        //when
        controller.processNewLoanApplication(loanApplication);

        //then
        assertEquals(new BigDecimal(60), loanApplication.getRepayment());
    }

    @Test
    public void test5YearLoanWithRounding() {
        //given
        loanApplication.setPrincipal(5000);
        loanApplication.setTermInMonths(60);
        when(loanApplication.getInterestRate()).thenReturn(BigDecimal.valueOf(6.5));

        //when
        controller.processNewLoanApplication(loanApplication);

        //then
        assertEquals(new BigDecimal(111), loanApplication.getRepayment());
    }

    @Test
    public void test6YearLoanWithRounding() {
        //given
        loanApplication.setPrincipal(5000);
        loanApplication.setTermInMonths(60);
        when(loanApplication.getInterestRate()).thenReturn(BigDecimal.valueOf(6.5));

        //when
        controller.processNewLoanApplication(loanApplication);

        //then
        assertEquals(new BigDecimal(111), loanApplication.getRepayment());
    }

    @Test
    public void test10YearLoanWholePounds() {
        given(loanApplication.getPrincipal()).willReturn(1200);
        given(loanApplication.getTermInMonths()).willReturn(12);
        given(loanApplication.getInterestRate()).willReturn(new BigDecimal(10));

        controller.processNewLoanApplication(loanApplication);

        assertEquals(new BigDecimal(110), loanApplication.getRepayment());
    }
}
