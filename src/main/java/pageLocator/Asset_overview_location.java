package pageLocator;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v112.page.Page;
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
	// span[contains(@class, 'button-2')]
	// span[contains(@class, 'button-2') and contains(.., 'Khả dụng')]
	// span[@class='button-2 text-neutral-700']
	// (//span[@class='button-2 text-neutral-700'])[1]
	@FindBy(xpath = "//span[contains(@class, 'button-2')]")
	public WebElement available_balance_text;

	// (//*[name()='svg' and @width='25' and @height='25'])[last()]
	// div[2]/div[2]/*[name()='svg'][1]

	@FindBy(xpath = "//div[2]/div[2]/*[name()='svg'][1]")
	private WebElement icon_swap;

	@FindBy(xpath = "(//span[contains(text(),'Chuyển tài sản')])[1]")
	private WebElement TransferBtn;
	private WebElement transferBtn01;

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
		// String balanceText = available_balance_text.getText().replaceAll("[^0-9.]",
		// "").trim();
		// return balanceText.isEmpty() ? 0.0 : Double.parseDouble(balanceText);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//		String availableBalanceCurrnet = available_balance_text.getText();
		WebElement AvailableTxt = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'button-2')]")));
		String availableBalanceCurrent = AvailableTxt.getText().replaceAll("[^0-9.]", "").trim();
//		WebElement spanButton = wait
//				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'button-2')]")));
		return 0;
	}

//	public void AvailableOld ()
//	{
//		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 50), this);
//		web
//	}

	public void ClickTransferAsset() {

		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 50), this);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//		wait.until(ExpectedConditions.elementToBeClickable(TransferBtn));

		WebElement transferBtn = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[.//span[contains(text(),'Chuyển tài sản')]]")));
		transferBtn.click();
		System.out.println("Transfer Success");
	}

	public void clickSwapIcon() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(icon_swap));
		icon_swap.click();
	}
	// Method: Nhập amount nếu balance > 0
//	public void enterAmountIfPossible(String amount) {
//		amount_transfer_text.clear();
//		amount_transfer_text.sendKeys(amount);
//	}
	// enterAmountIfPossible

	public void enterAmountIfPossible(String amount) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			WebElement input = wait.until(ExpectedConditions.visibilityOf(amount_transfer_text));

			// Scroll vào view
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", input);

			// Click để focus
			input.click();

			// Clear sạch (Ant Design hay không clear bình thường)
			input.sendKeys(Keys.CONTROL + "a"); // Select all
			input.sendKeys(Keys.DELETE); // Xóa hết

			// Nhập amount mới
			input.sendKeys(amount);

			System.out.println("Đã nhập thành công số lượng: " + amount);

		} catch (Exception e) {
			throw new RuntimeException("Không thể nhập amount transfer", e);
		}
	}

	// Method chính xử lý toàn bộ logic bạn mô tả
	public void handleTransferAmount(String amountToTransfer) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		double balance = getAvailableBalance();

		if (balance > 0) {
			System.out.println("Số dư available: " + balance + " > 0 → Nhập amount");
			enterAmountIfPossible(amountToTransfer);
		} else {
			System.out.println("Số dư = 0");

			// Click swap icon
			wait.until(ExpectedConditions.elementToBeClickable(icon_swap)).click();

			// Đợi balance cập nhật sau swap
			wait.until(ExpectedConditions.visibilityOf(available_balance_text));
			balance = getAvailableBalance();

			if (balance > 0) {
				System.out.println("Sau khi swap, số dư: " + balance + " > 0 → Nhập amount");
				enterAmountIfPossible(amountToTransfer);
			} else {
				System.out.println("Sau khi swap, số dư vẫn = 0");
				// Có thể throw exception hoặc return false nếu cần fail test
			}
		}
	}
}