package pageLocator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory; 

public class Home {
 
    WebDriver driver;
    @FindBy(xpath = "//div[contains(text(),'Build the Future with')]")
    public static WebElement Home;
    @FindBy(xpath = "//a[contains(text(),'Markets')]")
    public static WebElement Menu_Market;
    @FindBy(xpath = "//a[contains(text(),'Spot')]")
    public static WebElement Menu_spot;
    @FindBy(xpath = "//a[contains(text(),'Margin')]")
    public static WebElement Menu_Margin;
    public void Dashboard(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

}


