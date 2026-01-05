package pageLocator;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Constant.CT_Account;

public class Asset_overview_location {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//div[./span[text()='Tài sản']]")
	private WebElement Asset_btn_menu;

	@FindBy(xpath = "//p[text()='Tài sản của tôi']")
	private WebElement my_asset_option_list;

	@FindBy(xpath = "//button[./span[text()='Chuyển tài sản']]")
	private WebElement transfer_btn;

	@FindBy(xpath = "//button[./span[text()='Chuyển tài sản']]")
	private WebElement amount_transfer_text;
	@FindBy(xpath = "//span[contains(@class, 'button-2') and contains(.., 'Khả dụng')]")
	private WebElement available_balance_text;
	
	public Asset_overview_location(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(50));
	}

	// case test Transfer

	public void open_aset_web_overview(String amount) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(transfer_btn)).click();
		wait.until(ExpectedConditions.visibilityOf(available_balance_text));
		String fulltext = available_balance_text.getText();
		String balanceNumber = fulltext.replaceAll("[^0-9.]", "").trim(); 
	    double balance = Double.parseDouble(balanceNumber);

	    // 3. Thực hiện logic rẽ nhánh
	    if (balance == 0) {
	        System.out.println("Balance = 0. Skip checking this part.");
	        // Bạn có thể dùng throw new SkipException nếu dùng TestNG để đánh dấu skip
	    } else {
	        System.out.println("Balance = " + balance + ". Proceeding to enter amount.");
	        // Nếu > 0 thì thực hiện nhập text
	        amount_transfer_text.sendKeys(amount); 
	    }
	}
		
	} 
