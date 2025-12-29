package Common;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonBase {

    private static final String URL = null;
    public WebDriver driver;
    protected String baseUrl = "https://staging.capa.ai/";
    protected int DEFAULT_TIMEOUT = 20000;
    // bước nhảy là 1s 1, 1000=1s
    protected int WAIT_INTERVAL = 1000;
    public int loopCount = 0;
    // lặp lại
    public final int ACTION_REPEAT = 5; // lặp lại
    public Actions action;

    /**
     * pause driver in timeInMillis
     *
     * @param timeInMillis
     */
    public void pause(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * click on an element
     */

    public void click(Object locator) {
        By xPath = locator instanceof By ? (By) locator : By.xpath(locator.toString());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementClick = wait.until(ExpectedConditions.elementToBeClickable(xPath));
        elementClick.click();
    }

    /**
     * get absolute path of file
     *
     * @param relativeFilePath
     * @return
     */
    public String getAbsoluteFilePath(String relativeFilePath) {
        String curDir = System.getProperty("user.dir");
        String absolutePath = curDir + relativeFilePath;
        return absolutePath;
    }

    /**
     *
     * @param locator
     * @param opParams
     */
//	public void mouseOverAndClick(Object locator, Object... opParams) {
//		WebElement element;
//		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
//		Actions actions = new Actions(driver);
//		try {
//			element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay);
//			actions.moveToElement(element).click(element).build().perform();
//		} catch (StaleElementReferenceException e) {
//			mouseOverAndClick(locator, opParams);
//		}
//	}

    /**
     * quit driver if driver existed
     *
     * @param dr
     */
    public void quitDriver(WebDriver dr) {
        {
            if (dr.toString().contains("null")) {
                System.out.print("All Browser windows are closed ");
            } else {
                dr.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                dr.manage().deleteAllCookies();
                dr.close();
            }
        }
    }

    public int findIFrame() {
        int indexOfIFrame =0;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int size = driver.findElements(By.tagName("iframe")).size();
        System.out.println("số lượng frame" + size);
        for (int i=0 ; i<size; i++) {
            driver.switchTo().frame(i);
            int numberOfIFrame = driver.findElements(By.xpath("//button[text()='Gửi ngay']")).size();
            System.out.println("elementCanTim ở vị trí:" + numberOfIFrame);
            if(numberOfIFrame !=0)
            {
                indexOfIFrame = i ;
                break;
            }
            ///sau khi in ra element cần tìm phải trở về frame cha để tìm tiếp đến hết
            driver.switchTo().defaultContent();
        }
        System.out.println("indexOfIFrame:" + indexOfIFrame);
        return indexOfIFrame;
    }
    /**
     * switch to a frame
     *
     * @param locator
     * @param opParams
     */
//	public void switchToFrame(Object locator, Object... opParams) {
//		info("Switch to frame " + locator);
//		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
//		try {
//			driver.switchTo().frame(getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay));
//		} catch (Exception e) {
//			switchToFrame(locator, notDisplay);
//		}
//	}

    /**
     * back to main frame
     */
    public void switchToParentFrame() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            switchToParentFrame();
        }
    }

    /**
     * accept unexpected alert
     */
    public void acceptAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();

        } catch (NoAlertPresentException ex) {
            info("No Alert present");
            ;
        }
    }

    /**
     *
     * @param locator
     * @param opParams
     */
//	public void scrollToElement(Object locator, Object... opParams) {
//		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
//		WebElement element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay);
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//	}

    /**
     * check field is null = ""
     *
     * @param s
     */
    public void verifyNull(String s) {
        if (!s.equalsIgnoreCase("")) {
            Assert.fail("Du lieu khong null");
        }
    }

    /**
     * tra ve so lan xuat hien cua 1 xau trong chuoi
     *
     * @param value
     * @param array
     * @return
     */
    public String checkValueInArray(String value, String[] array) {
        int soLan = 0;
        if (value != null && value != "" && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                if (value.equalsIgnoreCase(array[i])) {
                    soLan++;
                    info("Chuoi \"" + value + "\" xuat hien trong mang lan thu: " + soLan);
                }
            }
            return String.valueOf(soLan);
        } else {
            info("Chuoi can kiem tra dang null hoac mang khong co phan tu");
            return "";
        }
    }

    /**
     *
     * @param string
     * @param split
     * @return
     */
    public String[] convertStringToArray(String string, String split) {
        String[] a = new String[100];
        if (string != null && string != "") {
            a = string.split(split);
        } else {
            info("Xau ky tu can chuyen sang mang ");
        }
        return a;
    }

    private void info(String string) {
		// TODO Auto-generated method stub
		
	}

	// nếu chỉ muốn chạt chrome thì chỉ cần mỗi cái này
    public WebDriver initChromeDrvier(String URL) {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
        return driver;
    }

    // public WebDriver driver;
    public int initWaitTime = 10;

    public WebDriver initChromeDriver(String URL) {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(URL);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        return driver;
    }
    private WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser...");
        System.setProperty("webdriver.firefox.driver" ,
                System.getProperty("user.dir") + "\\driver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }
    private WebDriver initChormeDriver() {
        System.out.println("Launching Chrome browser...");
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver" ,
                System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }
    private WebDriver initEdgeDriver() {
        System.out.println("Launching Edge browser...");
        System.setProperty("webdriver.edge.driver" ,
                System.getProperty("user.dir") + "\\driver\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        return driver;
    }
    public void inputTextJavaScriptInnerHTML(By inputElement, String companyName) {
        WebElement element = driver.findElement(inputElement);
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = '" + companyName + "'", element);
        } catch (StaleElementReferenceException ex) {
            pause(1000);
            inputTextJavaScriptInnerHTML(inputElement, companyName);
        }
    }

    public void inputTextJavaScriptValue(By locator, String value) {
        WebElement element = getElementPresentDOM(locator);
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '" + value + "'", element);
        } catch (StaleElementReferenceException ex) {
            pause(1000);
            inputTextJavaScriptValue(locator, value);
        }
    }

    public void scrollToElement(By locator) {
        WebElement element = getElementPresentDOM(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickJavaScript(By locator) {
        WebElement element = getElementPresentDOM(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public WebElement getElementPresentDOM(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(initWaitTime));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    public boolean isElementPresent(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(initWaitTime));
        wait.until(ExpectedConditions.visibilityOf(getElementPresentDOM(locator)));
        return getElementPresentDOM(locator).isDisplayed();
    }

    public void click(By locator) {
        WebElement element = getElementPresentDOM(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(initWaitTime));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public void type(By locator, String value) {
        WebElement element = getElementPresentDOM(locator);
        element.sendKeys(value);
    }

}
