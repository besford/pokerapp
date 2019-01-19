package testAutomation;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import OSTools.OSInfo;

//Test ID 2
public class ClickingStartNewGameButtonOnLobbyWithAIDisplaysStrats {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		if (OSInfo.getOs() == OSInfo.OS.UNIX)
			System.setProperty("webdriver.chrome.driver","selenium-3.4/chromedriver");
		if (OSInfo.getOs() == OSInfo.OS.WINDOWS)
			System.setProperty("webdriver.chrome.driver","selenium-3.4/chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testClickingStartNewGameButtonOnLobbyWithAIDisplaysStrats() throws Exception {
		driver.get(baseUrl + "/webapp/");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("PokerApp | Lobby", driver.getTitle());
		driver.findElement(By.cssSelector("input[value=\"AI\"]")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("PokerApp | Strategies", driver.getTitle());
		assertTrue(isElementPresent(By.cssSelector("div")));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
