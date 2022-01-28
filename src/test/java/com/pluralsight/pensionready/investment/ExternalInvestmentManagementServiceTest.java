package com.pluralsight.pensionready.investment;

import com.pluralsight.pensionready.Account;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExternalInvestmentManagementServiceTest {

    ExternalInvestmentManagementService underTest = partialMockBuilder(ExternalInvestmentManagementService.class)
            .addMockedMethod("executeInvestmentTransaction").createMock();

    @Test
    public void shouldBuyPensionInvestmentIfEnoughCashInAccount() throws IOException {
        expect(underTest.executeInvestmentTransaction(anyString(), isA(BigDecimal.class), anyString()))
                .andReturn(true);

        final String desiredInvestment = "a stock";
        Account testAccount = new Account();
        testAccount.setInvestments(new HashSet<>());
        final BigDecimal accountBalance = new BigDecimal(1000000);
        testAccount.setAvailableCash(accountBalance);
        final BigDecimal desiredInvestmentAmount = new BigDecimal(2000);

        replay(underTest);

        assertTrue(underTest.buyInvestmentFund(testAccount, desiredInvestment, desiredInvestmentAmount));
        assertEquals(testAccount.getAvailableCash(), accountBalance.subtract(desiredInvestmentAmount));
        assertTrue(testAccount.getInvestments().contains(desiredInvestment));
        verify(underTest);
    }


}