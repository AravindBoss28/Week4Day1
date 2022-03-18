package week4day1.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonOnePlus extends TakeScreenShot {
	ChromeDriver driver;

	void initializeChrome() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	void openAmazonAndSearch() {
		driver.get("https://www.amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("One Plus 9 pro", Keys.ENTER);
	}

	String findPrice() {
		String xpathforPrice = "//div[@class='sg-col-inner']//span/ancestor::div[@class='a-section'][1]//a/span[@data-a-size='l']//span[2]/span[2]";

		String xpathForStar = "//div[@class='sg-col-inner']//span/ancestor::div[@class='a-section'][1]//a/i[2]";

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement webElement = driver.findElements(By.xpath(xpathforPrice)).get(0);
		String price = webElement.getText();
		System.out.println("pricesss " + price);
        takeScreenShot("phoneprice"+price, driver);
		driver.findElements(By.xpath(xpathForStar)).get(0).click();
		String rating = driver.findElement(By.xpath("//table[@id='histogramTable']/tbody/tr[1]/td[1]/span[1]/a"))
				.getAttribute("title");
		Pattern pattern = Pattern.compile("(\\d{2})");
		Matcher matcher = pattern.matcher(rating);
		if (matcher.find()) {
			System.out.println("percentage of 5 star rating is : " + matcher.group());
		}
		takeScreenShot("5StarRating", driver);
		return price;
	}

	public void clickImg() {
		driver.navigate().refresh();
		String firstImg = "//div[@class='sg-col-inner']//span/ancestor::div[@class='a-section']/div//img";

		// open the link in new tab, Keys.Chord string passed to sendKeys
		driver.findElements(By.xpath(firstImg)).get(0).click();

		// hold all window handles in array list
		ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());

		// switch to new tab
		driver.switchTo().window(newTb.get(1));

	}

	String getSubTotal() {
		driver.findElement(By.id("add-to-cart-button")).click();
		driver.findElement(By.id("attach-close_sideSheet-link")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement carticon = driver.findElement(By.id("nav-cart-count"));
		carticon.click();
		WebElement sub = driver
				.findElement(By.xpath("(//span[contains(@class,'a-color-price sc-price-container')]//span)[3]"));
		String pricestring = sub.getText();
		String total = pricestring.replaceAll(".00", "");
		String subtotal = total.replaceAll(" ", "");
		takeScreenShot("addToCart"+subtotal, driver);
		System.out.println("subsss  " + subtotal);
		driver.quit();
		return subtotal;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AmazonOnePlus am = new AmazonOnePlus();
		am.initializeChrome();
		am.openAmazonAndSearch();
		String expectedPrice = am.findPrice();
		am.clickImg();
		String actualPrice = am.getSubTotal();
		System.out.println(expectedPrice.equals((String)actualPrice));
	}

}
