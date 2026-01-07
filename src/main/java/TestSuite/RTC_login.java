package TestSuite;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.CommonBase;
import Constant.CT_Account;
import pageLocator.Login_locator;
import pageLocator.OtpPage;

public class RTC_login extends CommonBase {
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
	public void TC_Login_email_SS() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_SS_test("stg2@mailinator.com", "Te@12345");
// Check msg sau khi click continue
		Assert.assertTrue(loginPage.checkSuccessMsgIsDisplayed(), "LỖI: Thông báo thành công không hiển thị!");
		// nhập code OTP confirm login
		otpPage.waitOtpVisible();
		otpPage.enterOtp("123456");
		boolean isLoginSuccess = loginPage.verifyloginSS();
		Assert.assertTrue(isLoginSuccess,
				"LỖI: Không hiển thị đúng URL trang chủ sau khi login!" + "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 2)
	public void TC_Login_email_USS_blank_Email() throws InterruptedException {

		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_blank("", "Te@12345");
	}

	@Test(priority = 3)
	public void TC_Login_email_USS_account_invalid_email_miss_domain() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_account_invalid("abc", "Te@12345");
		boolean isMsgDisplayed01 = loginPage.account_invalid();
		Assert.assertTrue(isMsgDisplayed01, "LỖI: Không tìm thấy thông báo lỗi tài khoản không hợp lệ!"
				+ "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 4)
	public void TC_Login_email_USS_account_invalid_email_miss_acong() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_account_invalid("abc.com", "Te@12345");
		boolean isMsgDisplayed01 = loginPage.account_invalid();
		Assert.assertTrue(isMsgDisplayed01, "LỖI: Không tìm thấy thông báo lỗi tài khoản không hợp lệ!"
				+ "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 5)
	public void TC_Login_email_USS_account_invalid_email_miss_thieu_com() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_account_invalid("abc@com", "Te@12345");
		boolean isMsgDisplayed01 = loginPage.account_invalid();
		Assert.assertTrue(isMsgDisplayed01, "LỖI: Không tìm thấy thông báo lỗi tài khoản không hợp lệ!"
				+ "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 6)
	public void TC_Login_email_USS_account_invalid_email_miss_thieu_giua() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_account_invalid("abc@.om", "Te@12345");
		boolean isMsgDisplayed01 = loginPage.account_invalid();
		Assert.assertTrue(isMsgDisplayed01, "LỖI: Không tìm thấy thông báo lỗi tài khoản không hợp lệ!"
				+ "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 7)
	public void TC_Login_email_SS_space_after() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_SS_test("stg2@mailinator.com ", "Te@12345");
// Check msg sau khi click continue
		Assert.assertTrue(loginPage.checkSuccessMsgIsDisplayed(),
				"LỖI: Thông báo thành công không hiển thị!" + "URL hiện tại: " + driver.getCurrentUrl());
		// nhập code OTP confirm login
		otpPage.waitOtpVisible();
		otpPage.enterOtp("123456");
		boolean isLoginSuccess = loginPage.verifyloginSS();
		Assert.assertTrue(isLoginSuccess,
				"LỖI: Không hiển thị đúng URL trang chủ sau khi login!" + "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 8)
	public void TC_Login_email_USS_account_invalid_email_space_betwwen() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_account_invalid("stg 2@mailinator.com", "Te@12345");
		boolean isMsgDisplayed01 = loginPage.account_invalid();
		Assert.assertTrue(isMsgDisplayed01, "LỖI: Không tìm thấy thông báo lỗi tài khoản không hợp lệ!"
				+ "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 9)
	public void TC_Login_email_SS_space_before() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_SS_test(" stg2@mailinator.com", "Te@12345");
// Check msg sau khi click continue
		Assert.assertTrue(loginPage.checkSuccessMsgIsDisplayed(),
				"LỖI: Thông báo thành công không hiển thị!" + "URL hiện tại: " + driver.getCurrentUrl());
		// nhập code OTP confirm login
		otpPage.waitOtpVisible();
		otpPage.enterOtp("123456");
		boolean isLoginSuccess = loginPage.verifyloginSS();
		Assert.assertTrue(isLoginSuccess,
				"LỖI: Không hiển thị đúng URL trang chủ sau khi login!" + "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 10)
	public void TC_Login_email_USS_account_invalid_passsword() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_account_invalid("stg1@mailinator.com", "tE@1234556");
		boolean isMsgDisplayed01 = loginPage.account_invalid();
		Assert.assertTrue(isMsgDisplayed01, "LỖI: Không tìm thấy thông báo lỗi tài khoản không hợp lệ!"
				+ "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 11)
	public void TC_Login_email_USS_blank_password() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_password_blank("stg1@mailinator.com", "");
	}

	@Test(priority = 12)
	public void TC_Login_password_Space_before() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_SS_test("stg2@mailinator.com", " Te@12345");
// Check msg sau khi click continue
		Assert.assertTrue(loginPage.checkSuccessMsgIsDisplayed(),
				"LỖI: Thông báo thành công không hiển thị!" + "URL hiện tại: " + driver.getCurrentUrl());
		// nhập code OTP confirm login
		otpPage.waitOtpVisible();
		otpPage.enterOtp("123456");
		boolean isLoginSuccess = loginPage.verifyloginSS();
		Assert.assertTrue(isLoginSuccess,
				"LỖI: Không hiển thị đúng URL trang chủ sau khi login!" + "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 12)
	public void TC_Login_password_Space_after() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_SS_test("stg2@mailinator.com", "Te@12345 ");
// Check msg sau khi click continue
		Assert.assertTrue(loginPage.checkSuccessMsgIsDisplayed(),
				"LỖI: Thông báo thành công không hiển thị!" + "URL hiện tại: " + driver.getCurrentUrl());
	//	 nhập code OTP confirm login
		otpPage.waitOtpVisible();
		otpPage.enterOtp("123456");
		boolean isLoginSuccess = loginPage.verifyloginSS();
		Assert.assertTrue(isLoginSuccess,
				"LỖI: Không hiển thị đúng URL trang chủ sau khi login!" + "URL hiện tại: " + driver.getCurrentUrl());
	}

	@Test(priority = 13)
	public void TC_Login_password_Space_between() throws InterruptedException {
		Login_locator loginPage = new Login_locator(driver);
		loginPage.LoginFunction_Email_SS_test("stg2@mailinator.com", "Te@12 345");
		// Check msg sau khi click continue
		Assert.assertTrue(loginPage.checkSuccessMsgIsDisplayed(),
				"LỖI: Thông báo thành công không hiển thị!" + "URL hiện tại: " + driver.getCurrentUrl());
		// nhập code OTP confirm login
		otpPage.waitOtpVisible();
		otpPage.enterOtp("123456");
		boolean isLoginSuccess = loginPage.verifyloginSS();
		Assert.assertTrue(isLoginSuccess,
				"LỖI: Không hiển thị đúng URL trang chủ sau khi login!" + "URL hiện tại: " + driver.getCurrentUrl());
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

//	@Test(priority = 1)
//	public void TC_Login_email_SS() throws InterruptedException {
//		Login_locator loginPage = new Login_locator(driver);
//		loginPage.LoginFunction_Email_SS("stg2@mailinator.com", "Te@12345", "1", "2", "3", "4", "5", "6");
//	    boolean isMsgDisplayed = loginPage.checkSuccessMsgIsDisplayed(); 
//	    Assert.assertTrue(isMsgDisplayed, "LỖI: Thông báo thành công không hiển thị!");
//	    boolean isLoginSuccess = loginPage.verifyloginSS();
//	    Assert.assertTrue(isLoginSuccess, "LỖI: Không hiển thị đúng URL trang chủ sau khi login!");
//}
}
