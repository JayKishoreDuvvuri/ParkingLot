package com.ParkingLot;

import static org.testng.Assert.assertTrue;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParkingcalcTest {

	WebDriver driver;
	WebDriverWait wait;

	@Parameters({ "browser", "url" })
	@BeforeTest
	public void setUp(String browser, String url) {
		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "src/com/TestSuite/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/com/TestSuite/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@Parameters({ "expectedTitle", "expectedurl" })
	@Test
	public void ParkingvalidationTest(String expectedTitle, String expectedurl) {
		Select Lot = new Select(driver.findElement(By.id("Lot")));
		Lot.selectByValue("VP");
		driver.findElement(By.id("EntryTime")).clear();
		driver.findElement(By.id("EntryTime")).sendKeys("11:00");
		driver.findElement(By.xpath("//input[@name='EntryTimeAMPM' and @value='PM']")).click();
		driver.findElement(By.id("EntryDate")).clear();
		driver.findElement(By.id("EntryDate")).sendKeys("11/21/2018");
		driver.findElement(By.id("ExitTime")).clear();
		driver.findElement(By.id("ExitTime")).sendKeys("11:45");
		driver.findElement(By.xpath("//input[@name='ExitTimeAMPM' and @value='PM']")).click();
		driver.findElement(By.id("ExitDate")).clear();
		driver.findElement(By.id("ExitDate")).sendKeys("11/21/2018");
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@name,'Submit')]"))).click();
		String ActualTitle = driver.getTitle();
		String ExpectedTitle = expectedTitle;
		Assert.assertEquals(ActualTitle, ExpectedTitle);
		assertTrue(driver.getCurrentUrl().contains(expectedurl));
	}

	@AfterTest
	public void tearDown() {
		driver.close();
	}
}
