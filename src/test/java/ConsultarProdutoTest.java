// This sample code supports Appium Java client >=9
// https://github.com/appium/java-client
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.MalformedURLException;
import io.appium.java_client.android.AndroidDriver;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

public class ConsultarProdutoTest {

  private AndroidDriver driver;

  private URL getUrl() {
    try {
      return new URL("https://oauth-bah.rodrigues222-f4867:5c2bdc74-0354-45fb-8c72-8f26fa6b93c8@ondemand.us-west-1.saucelabs.com:443/wd/hub");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } 
    return null;
  }

  @BeforeEach
  public void setUp() {
    var options = new BaseOptions()
      .amend("platformName", "Android")
      .amend("appium:platformVersion", "9.0")
      .amend("appium:deviceName", "Samsung Galaxy S9 FHD GoogleAPI Emulator")
      .amend("appium:deviceOrientation", "portrait")
      .amend("appium:app", "storage:filename=mda-2.2.0-25.apk")
      .amend("appium:appPackage", "com.saucelabs.mydemoapp.android")
      .amend("appium:appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
      .amend("appium:automationName", "UiAutomator2")
      .amend("browserName", "")
      .amend("appium:ensureWebviewsHavePages", true)
      .amend("appium:nativeWebScreenshot", true)
      .amend("appium:newCommandTimeout", 3600)
      .amend("appium:connectHardwareKeyboard", true);

    driver = new AndroidDriver(this.getUrl(), options);
  }

  @Test
  public void sampleTest() {
    var el5 = driver.findElement(AppiumBy.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]"));
    el5.click();
    var el6 = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
    assertEquals("Sauce Labs Backpack", el6.getText());
    var el7 = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV"));
    assertEquals("$ 29.99", el7.getText());
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }
}