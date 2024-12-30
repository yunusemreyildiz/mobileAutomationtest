package util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    static AppiumDriver driver;
    
    public static AppiumDriver initializeDriver(String platform) throws MalformedURLException {
        if (platform == null || platform.isEmpty()) {
            platform = "android"; // Varsayılan platform olarak Android'i ayarla
        }
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        switch (platform.toLowerCase()) {
            case "android":
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("automationName", "UiAutomator2");
                capabilities.setCapability("deviceName", "emulator-5554");
                capabilities.setCapability("appPackage", "com.akakce.akakce");
                capabilities.setCapability("appActivity", "com.akakce.akakce.ui.splash.SplashActivity");
                capabilities.setCapability("noReset", false);
                
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                break;
                
            case "ios":
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("automationName", "XCUITest");
                capabilities.setCapability("deviceName", "iPhone Simulator");
                capabilities.setCapability("bundleId", "com.n11.mobile");
                capabilities.setCapability("noReset", false);
                
                driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                break;
                
            default:
                throw new IllegalArgumentException("Geçersiz platform tipi: " + platform);
        }
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
    
    public static AppiumDriver getDriver() {
        return driver;
    }
} 