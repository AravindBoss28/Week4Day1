package week4day1.assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LeafGroundFrames {
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		LeafGroundFrames gf = new LeafGroundFrames();
		gf.driver = new ChromeDriver();
		gf.driver.manage().window().maximize();
		gf.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		gf.driver.get("http://leafground.com/pages/frame.html");
		List<WebElement> findElements = gf.driver.findElements(By.tagName("iframe"));
		System.out.println(findElements.size());
		gf.takeScreenShot("FrameCount");
	}

}
