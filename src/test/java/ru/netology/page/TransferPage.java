package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement header = $("[data-test-id='dashboard']");
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement button = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'].notification .notification__content");
    private final SelenideElement cancelButton = $("[data-test-id='action-cancel']");

    public TransferPage () {
        header.should(visible);
    }

    public DashboardPage transferForm(String sum, DataHelper.CardInfo cardInfo) {
        amount.setValue(sum);
        from.setValue(cardInfo.getCardNumber());
        button.click();
        return new DashboardPage();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).should(visible);
    }

    public DashboardPage cancelButton() {
        cancelButton.click();
        return new DashboardPage();
    }
}
