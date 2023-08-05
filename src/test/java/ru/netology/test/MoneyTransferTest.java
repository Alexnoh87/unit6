package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = DataHelper.getAuhtInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromFirst() {
        var transferPage = new TransferPage();
        var firstCardInfo = getFirstCard();
        var secondCardInfo = getSecondCard();
        var balanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var balanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        var moneyTransfer = dashboardPage.secondCardButton();
        var infoCard = getFirstCard();
        String amount = "100";
        var moneyTransferDataInput = transferPage.transferForm(amount, infoCard);
        var expectedFirstCardBalance = balanceFirstCard - Integer.parseInt(amount);
        var expectedSecondCardBalance = balanceSecondCard + Integer.parseInt(amount);

        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(firstCardInfo));
        assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(secondCardInfo));

    }

    @Test
    void shouldTransferFromSecond() {
        var transferPage = new TransferPage();
        var firstCardInfo = getFirstCard();
        var secondCardInfo = getSecondCard();
        var balanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var balanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        var moneyTransfer = dashboardPage.firstCardButton();
        var infoCard = getSecondCard();
        String amount = "100";
        var moneyTransferDataInput = transferPage.transferForm(amount, infoCard);
        var expectedFirstCardBalance = balanceFirstCard + Integer.parseInt(amount);
        var expectedSecondCardBalance = balanceSecondCard - Integer.parseInt(amount);

        assertEquals(expectedFirstCardBalance, dashboardPage.getCardBalance(firstCardInfo));
        assertEquals(expectedSecondCardBalance, dashboardPage.getCardBalance(secondCardInfo));

    }

    @Test
    void shouldCancelTransfer() {
        var transferPage = new TransferPage();
        var moneyTransfer = dashboardPage.firstCardButton();
        var cancelTransfer = transferPage.cancelButton();
    }

    @Test
    void shouldTransferMoreBalance() {
        var firstCardInfo = getFirstCard();
        var secondCardInfo = getSecondCard();
        var balanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var balanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        var moneyTransfer = dashboardPage.firstCardButton();
        var infoCard = getSecondCard();
        String amount = "50_000";
        moneyTransfer.transferForm(amount, infoCard);
        moneyTransfer.findErrorMessage("Ошибка!");
    }

    @Test
    void shouldNotEnterAmount() {
        var firstCardInfo = getFirstCard();
        var secondCardInfo = getSecondCard();
        var balanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var balanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        var moneyTransfer = dashboardPage.firstCardButton();
        var infoCard = getSecondCard();
        String amount = "";
        moneyTransfer.transferForm(amount, infoCard);
        moneyTransfer.findErrorMessage("Ошибка!");
    }

    @Test
    void shouldTransferToSameCard() {
        var firstCardInfo = getFirstCard();
        var secondCardInfo = getSecondCard();
        var balanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var balanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        var moneyTransfer = dashboardPage.firstCardButton();
        var infoCard = getFirstCard();
        String amount = "100";
        moneyTransfer.transferForm(amount, infoCard);
        moneyTransfer.findErrorMessage("Ошибка!");
    }
}
