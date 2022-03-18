package week4day1.assignments;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CherCherFrames extends TakeScreenShot {
	ChromeDriver driver;
	void initializeChrome() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       CherCherFrames fr = new CherCherFrames();
       fr.initializeChrome();
       fr.driver.get("https://chercher.tech/practice/frames-example-selenium-webdriver");
       fr.driver.switchTo().frame("frame1");
       fr.driver.findElement(By.xpath("/html/body/input")).sendKeys("hello");
       fr.driver.switchTo().frame("frame3");
       fr.driver.findElement(By.xpath("//*[@id='a']")).click();
       fr.driver.switchTo().defaultContent();
       fr.driver.switchTo().frame("frame2");
       WebElement xpathSelect = fr.driver.findElement(By.xpath("//*[@id='animals']"));
       Select s = new Select(xpathSelect);
       s.selectByIndex(1);
       fr.takeScreenShot("CherCher", fr.driver);
       fr.driver.close();
	}

}
