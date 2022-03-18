package week4day1.assignments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MergeContact extends TakeScreenShot{
	ChromeDriver driver;
	void initializeChrome() {

		// it helps to avoid manual chrome driver path setup
		WebDriverManager.chromedriver().setup();

		// initialize chrome
		driver = new ChromeDriver();

		// maximize screen
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}

	boolean loginAndNavigateToHomepage() {

		/**
		 * login to application
		 */
		boolean flag = true;
		driver.get("http://leaftaps.com/opentaps/control/main");
		String title = driver.getTitle();
		if (title.contains("Leaftaps")) {
			flag = true;
		}
		driver.findElement(By.id("username")).sendKeys("DemoSalesManager");
		driver.findElement(By.id("password")).sendKeys("crmsfa");
		driver.findElement(By.className("decorativeSubmit")).click();
		return flag;
	}

	void navigateToContactTab() {
		driver.findElement(By.linkText("CRM/SFA")).click();
		driver.findElement(By.xpath("//a[text()='Contacts']")).click();
	}

	void selectMergeContactOption() {
		driver.findElement(By.xpath("//a[text()='Merge Contacts']")).click();
	}

	String firstXpath = "//tbody//tr[1]/td[2]//table[@id='widget_ComboBox_partyIdFrom']/following-sibling::a/img";
	String secondXpath = "//span[text()='To Contact']/ancestor::tr//td[2]/a/img";
	String parentWindow;
    
	// this methods helps to select button icon from contact and to contact
	// it will return parent window id
	private String selectContacts(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
		parentWindow = driver.getWindowHandle();
		return parentWindow;
	}

	void selectFromAndToContact() {
		// re-using method
		selectContacts(firstXpath);
		
		// to store multiple windows
		Set<String> windowHandles = driver.getWindowHandles();
		
		// re-assigning it to arraylist to use get method
		// it will store parent window and child window data
		ArrayList<String> windows = new ArrayList<String>(windowHandles);
		
		
		driver.switchTo().window(windows.get(1));
		driver.manage().window().maximize();
		
		// used to select first contact id  in child window
		// passing xpath for contact id it will return arraylist
		// passing index id to select value
		selectContactsOption(contactIds, 0);
		
		// returns the parent window
		driver.switchTo().window(parentWindow);
		
		// same action like above performed for to contact
		selectContacts(secondXpath);
		windowHandles = driver.getWindowHandles();
		windows = new ArrayList<String>(windowHandles);
		driver.switchTo().window(windows.get(windows.size()-1));
		selectContactsOption(contactIds, 1);
		driver.switchTo().window(parentWindow);
	}

	String contactIds = "//div[text()='Contact ID']/ancestor::div[@class='x-grid3-viewport' and contains(@id,'ext-gen')]//tbody//td[1]/div/a[1]";
	private void selectContactsOption(String contactIds, int i) {
		driver.findElements(By.xpath(contactIds)).get(i).click();
	}

	void clickMerge() {
		takeScreenShot("mergeContact", driver);
		driver.findElement(By.linkText("Merge")).click();
	}

	void handleAlert() {
		driver.switchTo().alert().accept();
		takeScreenShot("viewMergeContact", driver);
		System.out.println(driver.getTitle());
	}
   
	void closeBrowser() {
	  driver.close();	
	}
	
	public static void main(String[] args) {

		// simplified the code for easy debugging
		MergeContact mg = new MergeContact();
		mg.initializeChrome();
		mg.loginAndNavigateToHomepage();
		mg.navigateToContactTab();
		mg.selectMergeContactOption();
		mg.selectFromAndToContact();
		mg.clickMerge();
		mg.handleAlert();
		mg.closeBrowser();
	}

}
