package pageLocator;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import Constant.CT_Account;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class Login_locator {
	WebDriver driver;
	WebDriverWait wait;
// locator login
	@FindBy(xpath = "//input[@name='Username' and @type='email']")
	private WebElement email_txt;
	@FindBy(xpath = "//input[@autocomplete='Username' and @type='phone']")
	private WebElement phone_txt;
	@FindBy(xpath = "//input[@autocomplete='Password' and @type='password']")
	private WebElement Password_txt;
	@FindBy(xpath = "(//button[contains(.,'Tiếp tục')])[1]") // button[@type='button' and contains(., 'Tiếp tục')]
	private WebElement continue_btn;
	@FindBy(xpath = "//button[contains(.,'Đăng nhập')]")
	private WebElement login_btn;

	@FindBy(xpath = "//div[text()='Email' and @role='tab']")
	private WebElement email_tab;
	@FindBy(xpath = "//div[contains(text(), 'Gửi mã xác minh thành công')]")
	private WebElement success_msg;
	@FindBy(xpath = "//div[@role='alert' and contains(@class, 'Toastify__toast--error')]")
	private WebElement Unsuccess_msg; 

	
	// OTP
	@FindBy(xpath = "//input[contains(@aria-label,'OTP Input')]")
	private List<WebElement> otpInputs;

	public Login_locator(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}



	public void LoginFunction_Email_SS_test(String email, String password) throws InterruptedException { 
		wait.until(ExpectedConditions.elementToBeClickable(email_tab));
		email_tab.click();
		email_txt.clear();
		email_txt.sendKeys(email);
		wait.until(ExpectedConditions.elementToBeClickable(continue_btn));
		continue_btn.click();
		wait.until(ExpectedConditions.elementToBeClickable(Password_txt));
		Password_txt.clear();
		Password_txt.sendKeys(password);
		wait.until(ExpectedConditions.elementToBeClickable(login_btn));
		login_btn.click(); 
	}
	public boolean checkSuccessMsgIsDisplayed() {
		try {
			// Đợi message xuất hiện trong tối đa 10 giây (dựa trên cấu hình wait của bạn)
			wait.until(ExpectedConditions.visibilityOf(success_msg));
			System.out.println("Hiển thị msg " + success_msg.getText());
			return true;
		} catch (Exception e) {
			// Nếu quá thời gian wait mà không thấy, nó sẽ nhảy vào đây
			System.err.println("LỖI: Không hiển thị message thành công!");
			return false; // Ném lỗi để bài test bị đánh dấu là Fail

		}
	} 
	 
	
//	public void LoginFunction_Email_SS(String email, String password, String... otp) throws InterruptedException {
////	public void LoginFunction_Email_SS(String email, String password, String input01, String input02, String input03,
////			String input04, String input05, String input06) throws InterruptedException {
//		wait.until(ExpectedConditions.elementToBeClickable(email_tab));
//		email_tab.click();
//		email_txt.clear();
//		email_txt.sendKeys(email);
//		wait.until(ExpectedConditions.elementToBeClickable(continue_btn));
//		continue_btn.click();
//		wait.until(ExpectedConditions.elementToBeClickable(Password_txt));
//		Password_txt.clear();
//		Password_txt.sendKeys(password);
//		wait.until(ExpectedConditions.elementToBeClickable(login_btn));
//		login_btn.click(); 
//
//		// TỐI ƯU: Đưa các ô input vào mảng để duyệt
//	    WebElement[] otpFields = {O1_txt, O2_txt, O3_txt, O4_txt, O5_txt, O6_txt};
//	    
//	    for (int i = 0; i < otpFields.length; i++) {
//	        wait.until(ExpectedConditions.visibilityOf(otpFields[i]));
//	        otpFields[i].clear();
//	        otpFields[i].sendKeys(otp[i]); // Nhập từng số từ tham số String
//	        Thread.sleep(500);
//	        }

	 
//	}
		
	public boolean verifyloginSS() {
		try {
			// Đợi URL thay đổi, không còn chữ 'login' thì mới là đã vào trang chủ
			wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
			String currentUrl = driver.getCurrentUrl();
			// Kiểm tra URL trang chủ chính xác 
			return currentUrl.equals("https://stg.u2w.io/vi") || currentUrl.equals("https://stg.u2w.io/en");
		} catch (Exception e) {
			System.out.println("Lỗi: Đăng nhập thất bại hoặc kẹt ở bước OTP.");
			return false;
 
		}
	}

	// display msg Gửi mã xác minh thành công
 

	public void LoginFunction_Email_blank(String email, String password) throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(email_tab));
		email_tab.click();
		// email_txt.clear();
		if (email != null && !email.isEmpty()) {
			email_txt.sendKeys(email);
		}
		// isEnabled() sẽ trả về true nếu nút có thể tương tác, false nếu bị disable
		if (continue_btn.isEnabled()) {
			System.out.println("Nút Continue đang mở - Tiến hành click");
			continue_btn.click();

			// Chỉ khi click được Continue mới làm tiếp các bước sau
			wait.until(ExpectedConditions.visibilityOf(Password_txt));
			Password_txt.clear();
			Password_txt.sendKeys(password);
			wait.until(ExpectedConditions.elementToBeClickable(login_btn));
			login_btn.click();
		} else {
			// Trường hợp email trống và nút bị xám (disable)
			System.out.println("Nút Continue đang bị DISABLE - Đúng như mong đợi khi email trống");
			// Ở đây bạn có thể thêm verify để đánh Case Test là PASS
		}
	}

	public void LoginFunction_Email_account_invalid(String email, String password) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(email_tab));
		email_tab.click();
		email_txt.clear();
		wait.until(ExpectedConditions.elementToBeClickable(email_tab));
		email_tab.click();
		email_txt.clear();
		email_txt.sendKeys(email);
		wait.until(ExpectedConditions.elementToBeClickable(continue_btn));
		continue_btn.click();
		wait.until(ExpectedConditions.elementToBeClickable(Password_txt));
		Password_txt.clear();
		Password_txt.sendKeys(password);
		wait.until(ExpectedConditions.elementToBeClickable(login_btn));
		login_btn.click();
	}

	// lỗi tài khoản
	public boolean account_invalid() {
		try {
			wait.until(ExpectedConditions.visibilityOf(Unsuccess_msg));

			System.out.println("Thông báo xuất hiện: " + Unsuccess_msg.getText());
			return true;
		} catch (Exception e) {
			System.err.println("LỖI: Không tìm thấy thông báo lỗi tài khoản không hợp lệ!");
			return false; // Ném lỗi để bài test bị đánh dấu là Fail

		}
	}

	public void LoginFunction_password_blank(String email, String password) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(email_tab));
		email_tab.click();
		email_txt.clear();
		email_txt.sendKeys(email);
		wait.until(ExpectedConditions.elementToBeClickable(continue_btn));
		continue_btn.click();
		wait.until(ExpectedConditions.elementToBeClickable(Password_txt));
		// email_txt.clear();
		if (password != null && !password.isEmpty()) {
			Password_txt.sendKeys(password);
		}
		if (login_btn.isEnabled()) {
			System.out.println("Nút Continue đang mở - Tiến hành click");
			login_btn.click();

			wait.until(ExpectedConditions.elementToBeClickable(login_btn));
			login_btn.click();
		} else {
			// Trường hợp email trống và nút bị xám (disable)
			System.out.println("Nút sign in đang bị DISABLE - Đúng như mong đợi khi email trống");
			// Ở đây bạn có thể thêm verify để đánh Case Test là PASS
		}
	}

}