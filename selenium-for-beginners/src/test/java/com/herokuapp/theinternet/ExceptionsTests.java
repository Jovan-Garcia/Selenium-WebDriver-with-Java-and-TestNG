package com.herokuapp.theinternet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTests {
	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional("chrome") String browser) {
		// Create Driver
		switch (browser) {
		case "chrome":
			driver = new ChromeDriver();
			break;

		case "firefox":
			driver = new FirefoxDriver();
			break;

		default:
			System.out.println("Do not know how to start" + browser + ", starting chrome instead");
			driver = new ChromeDriver();
			break;
		}

		driver = new ChromeDriver();

		// Maximize browser window
		driver.manage().window().maximize();

	}
	
	@Test
	public void noSuchElementExceptionTest() {
	//	Test case 1: NoSuchElementException
	//	Open page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		
	//	Click Add button
		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();
		
	// Explicit Wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
				
		
	//	Verify Row 2 input field is displayed
		Assert.assertTrue(row2Input.isDisplayed(), "Row 2 is not displayed");
		
	}
	
	@Test
	public void elementNotInteractableExceptionTest () {
	// Open page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		
	// Click Add button
		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();
		
	//	Wait for the second row to load
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		
	//	Type text into the second input field
		row2Input.sendKeys("Sushi");
		
		
	//	Push Save button using locator By.name(“Save”)
		WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
		saveButton.click();
		
		
	//	Verify text saved
		WebElement confirmationMessage = driver.findElement(By.id("confirmation"));
		String messageText = confirmationMessage.getText();
		Assert.assertEquals(messageText, "Row 2 was saved", "Confirmation message text is not expected");
	}
	
	
	@Test
	public void invalidElementStateExceptionTest () {
	//	Open page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		
	//	Clear input field
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement row1Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
		
		
		WebElement editButtonElement = driver.findElement(By.id("edit_btn"));
		editButtonElement.click();
		
		wait.until(ExpectedConditions.elementToBeClickable(row1Input));
		
		row1Input.clear();
		
	//	Type text into the input field
		row1Input.sendKeys("Sushi");
		
	// Click Save Button	
		WebElement saveButton = driver.findElement(By.xpath("//div[@id='row1']/button[@name='Save']"));
		saveButton.click();
		
		
	//	Verify text changed
		String value = row1Input.getAttribute("value");
		Assert.assertEquals(value, "Sushi", "Input 1 field value is not expected");
		
	//	Verify text saved
		WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
		String messageText = confirmationMessage.getText();
		Assert.assertEquals(messageText, "Row 1 was saved", "Confirmation message text is not expected");
	}
	
	@Test
	public void staleElementReferenceExceptionTest () {
	//	Open page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		
	//	Find the instructions text element
		WebElement instructionsTextElement = driver.findElement(By.id("instructions"));
		
		
	//	Push add button
		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();
		
	//	Verify instruction text element is no longer displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("instructions"))), "Instructions are still displayed");
	}

	@Test
	public void timeoutExceptionTest () {
	//	Open page
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		
	//	Click Add button
		WebElement addButtonElement = driver.findElement(By.id("add_btn"));
		addButtonElement.click();
		
		
	//	Wait for 3 seconds for the second input field to be displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		WebElement row2Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
		
	//	Verify second input field is displayed
		Assert.assertTrue(row2Input.isDisplayed(), "Row 2 is not displayed");

	}
	
	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// close driver
		driver.quit();
	}

}
