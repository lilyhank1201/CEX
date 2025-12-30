package pageLocator;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OtpPage {

    WebDriver driver;

    @FindBy(xpath = "//input[contains(@aria-label,'OTP Input')]")
    private List<WebElement> otpInputs;

    public OtpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitOtpVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//input[contains(@aria-label,'OTP Input')]"), 5
            ));
    }

    public void enterOtp(String otp) {
        for (int i = 0; i < otp.length(); i++) {
            otpInputs.get(i).sendKeys(
                String.valueOf(otp.charAt(i))
            );
        }
    }
}
