package week4day1.assignments;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class IRCTC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		TakeScreenShot sch = new TakeScreenShot();
		driver.manage().window().maximize();
		driver.get("https://www.irctc.co.in/nget/train-search");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.findElement(By.xpath("//form/div[2]/button")).click();

		// Keys.Chord string
		String clickl = Keys.chord(Keys.CONTROL, Keys.ENTER);
		// open the link in new tab, Keys.Chord string passed to sendKeys
		driver.findElement(By.linkText("FLIGHTS")).sendKeys(clickl);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// hold all window handles in array list
		ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
		sch.takeScreenShot("Flights", (ChromeDriver)driver);
		
		// switch to new tab
		driver.switchTo().window(newTb.get(1));
		driver.findElement(By.partialLinkText("Contact Us")).click();
		String res = driver.findElement(By.partialLinkText("@irctc")).getText();
		System.out.println(res);
		sch.takeScreenShot("IRCTC", (ChromeDriver)driver);
		driver.switchTo().window(newTb.get(0)).close();
		driver.quit();
	}

}
