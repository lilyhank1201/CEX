package TestSuite;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import Common.CommonBase;
import Constant.CT_Account;
import pageLocator.Asset_overview_location;
import pageLocator.Login_locator;
import pageLocator.OtpPage; 

public class Login_single extends CommonBase {
	WebDriver driver;
	WebDriverWait wait;
	Login_locator loginPage;
	OtpPage otpPage;
	
	@BeforeMethod
	public void openChromeDriver() {
		driver = initChromeDriver(CT_Account.webURL_login);
		otpPage = new OtpPage(driver);
		loginPage = new Login_locator(driver);
	}

	@Test(priority = 1)
	public void Transfer_SS() throws InterruptedException {
		// login
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_SS_test("stg2@mailinator.com", "Te@12345");
		AssertJUnit.assertTrue(loginPage.checkSuccessMsgIsDisplayed(), "LỖI: Thông báo thành công không hiển thị!");
		otpPage.waitOtpVisible();
		otpPage.enterOtp("123456");
		boolean isLoginSuccess = loginPage.verifyloginSS();
		AssertJUnit.assertTrue("LỖI: Không vào được trang chủ!" + "URL hiện tại: " + driver.getCurrentUrl(), isLoginSuccess); 
	// goto Assets > overview
		driver.get(CT_Account.Assets_transfer);  
	// Go to transfer	
		Asset_overview_location openAssets = new Asset_overview_location(driver);  
		openAssets.getAvailableBalance();
	
		openAssets.handleTransferAmount("0.01");  
		openAssets.ClickTransferAsset( );
	} 
 
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}}

//} 
//tôi  đang  xây dựng tại pageLocator cho asset  trước  
//Check  balacne "available_balance_text":
//- nếu "available_balance_text"* > 0  thì nhập vào amount_transfer_text
//- nếu "available_balance_text" = 0 thì
//+  in ra số dư = 0
//+ và thực hiện  click  vào  "icon_swap" >>>  rồi  check balacne  "available_balance_text" ( nếu  "available_balance_text" = 0 *thì* in *ra* số dư = 0, nếu "available_balance_text" > 0 thì nhập vào amount_transfer_text )