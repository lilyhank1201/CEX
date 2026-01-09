package pageLocator;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Asset_overview_location {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//div[./span[text()='Tài sản']]")
	private WebElement Asset_btn_menu;
	@FindBy(xpath = "//p[text()='Tài sản của tôi']")
	private WebElement my_asset_option_list;
	@FindBy(xpath = "//button[./span[text()='Chuyển tài sản']]")
	private WebElement GotoTransfer;
	@FindBy(xpath = "(//*[@type='text'])[3]")
	private WebElement amount_transfer_text;

	// (//*[name()='svg' and @width='25' and @height='25'])[last()]
	// div[2]/div[2]/*[name()='svg'][1]

	@FindBy(xpath = "//div[2]/div[2]/*[name()='svg'][1]")
	private WebElement icon_swap;

	@FindBy(xpath = "(//span[contains(text(),'Chuyển tài sản')])[1]")
	private WebElement TransferBtn;
	private WebElement transferBtn01;

	@FindBy(xpath = "(//div[@class='button-1'])[1]")
	private WebElement NameAssetDefault;
	@FindBy(xpath = "span[title='Ví Funding']")
	private WebElement FromAssetDefault;
	// span[contains(@class, 'button-2')]
	// span[contains(@class, 'button-2') and contains(.., 'Khả dụng')]
	// span[@class='button-2 text-neutral-700']
	// (//span[@class='button-2 text-neutral-700'])[1]
	@FindBy(xpath = "//span[contains(@class, 'button-2')]")
	public WebElement available_balance_text;

	public Asset_overview_location(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	}
	// case test Transfer

	public void OpenTransferPage() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(GotoTransfer)).click();
//		wait.until(ExpectedConditions.visibilityOf(available_balance_text));
//		System.out.println("Số dư available: " + available_balance_text );

	}

	public double getAvailableBalance() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOf(available_balance_text));
		return Double.parseDouble(available_balance_text.getText().trim().replaceAll("[^0-9.]", ""));
	}
//	public void printAllBalanceInfo() {
//	    System.out.println("=== THÔNG TIN TÀI KHOẢN ===");
// 	    System.out.println("Asset Default: " + NameAssetDefault.getText().trim().replaceAll("[^0-9.]", ""));
//     System.out.println("Ví Funding: " + FromAssetDefault.getText().trim());
//	    System.out.println("Available Balance: " + available_balance_text.getText().trim().replaceAll("[^0-9.]", ""));
//	    System.out.println("===========================");
//	}

	public void handleTransferAmount(String amount) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		double balanceBefore = getAvailableBalance();
		if (balanceBefore > 0) {
			try {
				WebElement input = wait.until(ExpectedConditions.visibilityOf(amount_transfer_text));

				// Scroll vào view
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", input);

				// Click để focus
				input.click();
				input.sendKeys(Keys.CONTROL + "a"); // Select all
				input.sendKeys(Keys.DELETE); // Xóa hết

				// Nhập amount mới
				input.sendKeys(amount);

				System.out.println("Đã nhập thành công số lượng: " + amount);

			} catch (Exception e) {
				throw new RuntimeException("Không thể nhập amount transfer", e);
			}
		} else {
			System.out.println("Số dư = 0");
//
//			// Click swap icon
//			wait.until(ExpectedConditions.elementToBeClickable(icon_swap)).click();
//
//			// Đợi balance cập nhật sau swap
//			wait.until(ExpectedConditions.visibilityOf(available_balance_text));
//			balance = printAllBalanceInfo();
//
//			if (balance > 0) {
//				System.out.println("Sau khi swap, số dư: " + balance + " > 0 → Nhập amount");
//				enterAmountIfPossible(amountToTransfer);
//			} else {
//				System.out.println("Sau khi swap, số dư vẫn = 0");
//				// Có thể throw exception hoặc return false nếu cần fail test
//			}
		}
	}

	public void ClickTransferAsset() throws InterruptedException {
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 60), this);
		double balance = getAvailableBalance();
		if (balance > 0) {
			WebElement transferBtn01 = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//span[contains(text(),'Chuyển tài sản')]//ancestor::button")));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
					transferBtn01);
			Thread.sleep(2000);
			
			boolean transferbtnEnable = transferBtn01.isEnabled();
			if (transferbtnEnable) {
				System.out.println("Transfer button Enabled");

			} else {
				System.out.println("Transfer button NOT enable");
			}
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", transferBtn01);

			System.out.println("Click Transfer Success");
			driver.navigate().refresh();
			
			double balanceAfterInput = getAvailableBalance();
			if (balanceAfterInput < balance) {
				System.out.println("Transfer SUCCESS is REAL với balance new = " + balanceAfterInput);

			} else {
				System.out.println(
						"Transfer FAILED hoặc chưa cập nhật: trước = " + balance + ", sau = " + balanceAfterInput);
			}
		} else {
			System.out.println(" Not enter Amount transfer LAN 1");
			
		}

	}

	public void clickSwapIcon() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(icon_swap));
		icon_swap.click();
	}

}