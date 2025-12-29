package pageLocator;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
    WebDriver driver;
    //gotoLogin
    @FindBy(xpath = "//input[@name='Username' and @type='email']")
    private WebElement email_txt;
    @FindBy(xpath = "//input[@autocomplete='Username' and @type='phone']")
    private WebElement phone_txt;
    @FindBy(xpath = "//input[@autocomplete='Password' and @type='password']")
    private WebElement Password_txt;
    @FindBy(xpath = "//input[@type='button']")
    private WebElement continue_btn;
    
    public void Login (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void LoginFunction(String email, String password) throws InterruptedException {
       Thread.sleep(2000); 
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        email_txt.clear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continue_btn );
        txtUserName.sendKeys(email);
        txtPass.clear();
        txtPass.sendKeys(password);
        btnLogin.click();
    }
}