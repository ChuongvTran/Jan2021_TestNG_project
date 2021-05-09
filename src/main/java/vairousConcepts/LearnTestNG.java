package vairousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {
	
	WebDriver driver;
	String browser;
	String URL;
	
	@BeforeClass
	public void readConfig() {
		//BufferReader // InputStream // FileReader // Scanner - these four classes are capabliable of reading any file.
		
		
	// reading files must be done with a try and catch
		Properties prop = new Properties();
		
		try {
			
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("browser used:" + browser);
			URL = prop.getProperty("URL");
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	@BeforeMethod
	public void init() {
		
		// use this if/else you have different browsers, because you cant open both chrome and firefox at the same time. so here we use if and else, so it will run chrome if chrome is on comp else itll use firefox
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
			
		}else if(browser.equalsIgnoreCase("FireFox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

//		System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
//		driver = new ChromeDriver();
//		
//		System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
//		driver = new FirefoxDriver();

		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	//@Test (priority=1)
	public void windowHandleTest() throws InterruptedException{
		
		Assert.assertEquals(driver.getTitle(),"Login - iBilling", "Wrong Page");
		
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
		
//		login data
		String loginId = "demo@techfios.com";
		String password = "abc123";
		
		USER_NAME_ELEMENT.sendKeys(loginId);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();
		
		WebElement Dashborad_Title_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), ' Dashboard ')]"));
//		System.out.println("===============" + Dashborad_Title_ELEMENT.getText()); // manual debug, this prints out the result of Dashborad_Title_ELEMENT.getText().
		Assert.assertEquals(Dashborad_Title_ELEMENT.getText(), "Dashboard", "Wrong Page"); // get text get the text in the object ( in this case Dashborad_Title_ELEMENT will get text "//h2[contains(text(), 'Dashboard')]" 
	
	}
	
	@Test (priority=2)
	public void addCustomer() throws InterruptedException {
		
		Assert.assertEquals(driver.getTitle(),"Login - iBilling", "Wrong Page");
		
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
		
		String loginId = "demo@techfios.com";
		String password = "abc123";
		
		//Test data or mock data
		
		String fullName = "Test January";
		String companyName = "Google";
		String email = "tT@gmail.com";
		String phone = "8675309";
		
		USER_NAME_ELEMENT.sendKeys(loginId);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();
		
		WebElement Dashborad_Title_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), ' Dashboard ')]"));
		Assert.assertEquals(Dashborad_Title_ELEMENT.getText(), "Dashboard", "Wrong Page");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebElement Customer_Element = driver.findElement(By.xpath("//span[contains(text(), 'Customers')]"));
		WebElement Add_Customer_Element = driver.findElement(By.xpath("//a[contains(text(), 'Add Customer')]"));
				
		Customer_Element.click();
		
		waitForElement(driver, 2, Add_Customer_Element);
		
		Add_Customer_Element.click();
		
		WebElement Full_Name_Element = driver.findElement(By.xpath("//input[@name='account']"));
		WebElement Company_dropdown_Element = driver.findElement(By.xpath("//select[@name='cid']"));
		WebElement Email_Element = driver.findElement(By.xpath("//input[@id='email']"));
		WebElement Phone_Element = driver.findElement(By.xpath("//input[@id='phone']"));
		WebElement Address_Element = driver.findElement(By.xpath("//input[@id='address']"));
		WebElement City_Element_Element = driver.findElement(By.xpath("//input[@name='city']"));
		WebElement State_Region_Element = driver.findElement(By.xpath("//input[@name='state']"));
		WebElement Zip_Postal_Code_Element = driver.findElement(By.xpath("//input[@name='zip']"));
		
		waitForElement(driver, 5, Full_Name_Element);
		
		Full_Name_Element.sendKeys(fullName);
		
		waitForElement(driver, 5, Company_dropdown_Element);
		
		//dropdown
		Select sel = new Select(Company_dropdown_Element);
		sel.selectByVisibleText(companyName);
		
		waitForElement(driver, 5, Email_Element);
		
		//Generate Random Number
		Random rnd = new Random();
		int randomNumber = rnd.nextInt(999);
		
		//fill email with random number
		Email_Element.sendKeys(randomNumber + email);
		
		
		Thread.sleep(1000);
		
	}
	
	
	
	public void waitForElement(WebDriver driver, int waitTime, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}

	//@AfterMethod
	public void teardown() {
		
		driver.close();
		driver.quit();
		
	}

}
