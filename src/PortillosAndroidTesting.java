import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class PortillosAndroidTesting {
	private AndroidDriver driver;
	
	public void mobileSetup() throws MalformedURLException {
		// Android Mobile APK Setup
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "GT-I9500");
		capabilities.setCapability("platformVersion", "5.0.1");	
		capabilities.setCapability("platformName", "Android");
		File file = new File("C:\\Users\\Mital\\workspace\\PortillosMobileTesting\\apk\\Portillos_v2.2.1_apkpure.com.apk");
		capabilities.setCapability("app", file.getAbsolutePath());
		capabilities.setCapability("fullReset", false);
		driver = new AndroidDriver(new URL("http://192.168.0.100:4723/wd/hub"), capabilities);
	}
			
	@Test (priority=1)
	public void testForPortillosSignUp() throws MalformedURLException, InterruptedException {
		mobileSetup();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("com.olo.portillos:id/btn_Right")).click();
		driver.findElement(By.id("com.olo.portillos:id/btn_Right")).click();
		driver.findElement(By.id("com.olo.portillos:id/btn_Right")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("com.olo.portillos:id/signUp")).click();
		driver.findElement(By.id("com.olo.portillos:id/firstName")).sendKeys("Mital");
		driver.findElement(By.id("com.olo.portillos:id/lastName")).sendKeys("Patel");
		driver.findElement(By.id("com.olo.portillos:id/emailAddress")).sendKeys("mital263@gmail.com");
		driver.findElement(By.id("com.olo.portillos:id/password")).sendKeys("Swami_123");
		
		try {
			driver.hideKeyboard(); 
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {}
		
		// Choosing Favorite Location
		MobileElement element = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView("
				+ "new UiSelector().description(\"Required, Select Favorite Location\"))"));
		System.out.println(element.getLocation());
		// Perform the action on the element
        element.click();
        
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.findElement(By.id("com.olo.portillos:id/mapView")).click();
		driver.findElementsById("com.olo.portillos:id/storeDetail").get(0).click();
		driver.findElement(By.id("com.olo.portillos:id/termsAndConditionsSwitch")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("com.olo.portillos:id/createAccount")).click();	
		driver.findElement(By.id("com.olo.portillos:id/skip")).click();
		
		// Check for alert if present by clicking "Okay"
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		MobileElement alertElement = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiSelector().text(\"Okay\")"));
		System.out.println(alertElement.getLocation());
		alertElement.click();
		
		// Check if Order Now Logo gets displayed on the page
		Boolean isPresent = driver.findElementsById("com.olo.portillos:id/nomnom_logo").size() != 0;
		Assert.assertTrue(isPresent);
		
		// Clicking on the toggle button to access Logout button
		driver.findElement(By.className("android.widget.ImageButton")).click();
		// Clicking on the Logout button
		driver.findElement(By.name("Log Out")).click();
		
	}
	
	@Test (priority=2)
	public void testForPortillosLogin() throws MalformedURLException, InterruptedException {
		mobileSetup();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("com.olo.portillos:id/btn_Right")).click();
		driver.findElement(By.id("com.olo.portillos:id/btn_Right")).click();
		driver.findElement(By.id("com.olo.portillos:id/btn_Right")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("com.olo.portillos:id/logIn")).click();
		driver.findElement(By.id("com.olo.portillos:id/emailAddress")).sendKeys("mital263@gmail.com");
		driver.findElement(By.id("com.olo.portillos:id/password")).sendKeys("Swami_123");
		try {
			driver.hideKeyboard(); 
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} catch (Exception e) {}
		driver.findElement(By.id("com.olo.portillos:id/loginButton")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Check if Order Now Logo gets displayed on the page
	    Boolean isPresent = driver.findElementsById("com.olo.portillos:id/nomnom_logo").size() != 0;
		Assert.assertTrue(isPresent);
	}
	
    @Test (priority=3)
	public void testForAddItemToCart() throws MalformedURLException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Click on Menu Tab
		WebElement parentElement = driver.findElement(By.id("com.olo.portillos:id/bottomNavigation"));
		List<WebElement> childElements = parentElement.findElements(By.className("android.widget.LinearLayout"));
		WebElement mainElement = childElements.get(0);
		List<WebElement> subchildElements = mainElement.findElements(By.className("android.widget.FrameLayout"));
		WebElement menuElement = subchildElements.get(1);
		menuElement.click();
		
		// Add Item 
		driver.findElement(By.name("Catering")).click();
		driver.findElement(By.name("Group Beverages")).click();
		driver.findElement(By.name("Iced Tea for a Group")).click();
		driver.findElement(By.name("+")).click();
		
		// Check if the quantity has been changed to 2
		String quantity  = driver.findElement(By.id("com.olo.portillos:id/productQuantity")).getText();
		Assert.assertEquals(quantity, "2");
		String price = driver.findElement(By.id("com.olo.portillos:id/productCalories")).getText();
		double totalPrice = Double.parseDouble(price.substring(1)) * Integer.parseInt(quantity);
		
		// Make sure the Order total price is reflecting correctly (price * quantity)
		driver.findElement(By.name("Add To Order $" + totalPrice)).click();
		driver.findElement(By.name("Check Out")).click();
		driver.findElement(By.name("Continue to Checkout")).click();
	}
}
