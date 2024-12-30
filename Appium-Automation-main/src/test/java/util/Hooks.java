package util;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.appium.java_client.AppiumDriver;

public class Hooks {
    
    @Before
    public void before() {
        try {
            String platform = System.getProperty("platform", "android");
            DriverFactory.initializeDriver(platform);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void after() {
        AppiumDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
} 