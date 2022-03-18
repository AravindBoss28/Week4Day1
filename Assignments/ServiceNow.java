package week4day1.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ServiceNow {
    
	ChromeDriver driver;

	
	private void takeScreenShot(String fname) {
		File screenshotAs = driver.getScreenshotAs(OutputType.FILE);
		try {
			File file = new File("./Snaps/" + fname + ".png");
			FileHandler.copy(screenshotAs, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("repective folder is not available or may be some other issue in file path");
		}
	}
	
	void initializeChrome() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	void loginServiceNow() {
		driver.get("https://dev85043.service-now.com");
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("Diya@2020May");
		driver.findElement(By.id("sysverb_login")).click();
	}
	
	void searchIncident() {
		driver.findElement(By.id("filter")).sendKeys("Incident");
		String xpathForAllButton = "//ul[@aria-label=\"Modules for Application: Incident\"]//li[6]//div[@class='sn-widget-list-title' and text()='All']";
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathForAllButton))).click();
	}
	
	void clickNewInstanceButton() {
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Incidents | ServiceNow']")));
		driver.findElement(By.xpath("//button[@id='sysverb_new']")).click();
	}
	
	String createInc() {
	  String incNum = driver.findElement(By.id("incident.number")).getAttribute("value");
	  driver.findElement(By.id("sys_display.incident.caller_id")).sendKeys("David Dan");
	  try {
		Thread.sleep(10000);
	  } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  driver.findElement(By.id("incident.short_description")).sendKeys("Desc for inc" + incNum, Keys.ENTER);
	  takeScreenShot(incNum);
	  driver.findElement(By.id("sysverb_insert_bottom")).click();
	  return incNum;
	}
	
	void searchBox(String incNum) {	
	  WebElement findElement = driver.findElement(By.xpath("//select[@role='listbox']"));
	  Select option = new Select(findElement);
	  option.selectByVisibleText("Number");
	  driver.findElement(By.xpath("//span[@id='incident_hide_search']//input")).sendKeys(incNum, Keys.ENTER);
	}
	
	String getIncNum() {
	  String acttext = driver.findElement(By.xpath("//tbody[@class='list2_body']/tr[1]/td[3]/a")).getText(); 
	  takeScreenShot("afterSearchingInc"+ acttext);
	  return acttext;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       ServiceNow s = new ServiceNow();
       s.initializeChrome();
       s.loginServiceNow();
       s.searchIncident();
       s.clickNewInstanceButton();
       String incNum = s.createInc();
       s.searchBox(incNum);
       String actIncNum = s.getIncNum();
       System.out.println("Incident num matched condition ? : "+incNum.equals(actIncNum));
       s.driver.close();
	}

}
