package util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class ElementHelper {

    AppiumDriver driver;
    WebDriverWait wait;
    Actions actions;


    public ElementHelper(AppiumDriver driver){
        this.driver=driver;
        this.wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions=new Actions(driver);
    }
    public WebElement presenceElement(By key){

        return wait.until(ExpectedConditions.presenceOfElementLocated(key));

    }
    public WebElement findElement(By key){
        WebElement element = presenceElement(key);
        return element;
    }
    public void click(By key){
        findElement(key).click();
    }
    public void sendKeys(By key, String text){
        findElement(key).sendKeys(text);
    }
    public void typeWithKeyboard(By key, String text) {
        findElement(key); // Elementi bul
        click(key); // Elementi odakla
        sendKeys(key, text); // Klavye ile yazma işlemi
    }


    public void setValueDirect(By key, String text) {
        WebElement element = presenceElement(key);
        element.clear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', arguments[1])", element, text);
    }

    public void checkVisible(By key){
        wait.until(ExpectedConditions.presenceOfElementLocated(key));
    }

    public void scrollUntilTextFound(String text){
        while (!driver.findElements(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"" + text + "\"))")).isEmpty()) {
        }
    }

    public void scrollToAndClickTenthProduct() {
        By cardLocator = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.akakce.akakce:id/card\")");
        int scrollCount = 0;
        int maxScrolls = 4;
        boolean clicked = false;

        // 4 kere scroll yap
        while (scrollCount < maxScrolls && !clicked) {
            try {
                // Scroll işlemi
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.8);
                int endY = (int) (size.height * 0.2);

                new TouchAction((PerformsTouchActions) driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();

                // Her scroll sonrası 1 saniye bekle
                Thread.sleep(1000);
                scrollCount++;
            } catch (Exception e) {
                System.out.println("Scroll işlemi sırasında hata: " + e.getMessage());
            }
        }

        try {
            // Son scroll'dan sonra cardları bul ve ilkine tıkla
            List<WebElement> cards = driver.findElements(cardLocator);
            if (!cards.isEmpty()) {
                WebElement firstCard = cards.get(0);
                // Elementin tıklanabilir olmasını bekle
                wait.until(ExpectedConditions.elementToBeClickable(firstCard));
                firstCard.click();
                clicked = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Card'a tıklama işlemi başarısız oldu: " + e.getMessage());
        }

        if (!clicked) {
            throw new RuntimeException("4 scroll sonrasında card'a tıklanamadı!");
        }
    }


    public void scrollToElementAndCenter(By elementLocator) {
        try {
            // Elementi bul
            WebElement element = driver.findElement(elementLocator);

            // Ekran boyutlarını al
            Dimension windowSize = driver.manage().window().getSize();
            int screenHeight = windowSize.getHeight();
            int screenCenter = screenHeight / 2;

            // Elementin konumunu al
            Point elementLocation = element.getLocation();
            int elementY = elementLocation.getY();

            // Elementin ekranın ortasına gelmesi için gerekli scroll miktarını hesapla
            int scrollAmount = elementY - screenCenter;

            // Scroll işlemi
            if (scrollAmount != 0) {
                int startX = windowSize.width / 2;
                int startY, endY;

                if (scrollAmount > 0) {
                    // Aşağı scroll
                    startY = (int) (screenHeight * 0.8);
                    endY = (int) (screenHeight * 0.2);
                } else {
                    // Yukarı scroll
                    startY = (int) (screenHeight * 0.2);
                    endY = (int) (screenHeight * 0.8);
                }

                new TouchAction((PerformsTouchActions) driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();

                // Scroll sonrası kısa bir bekleme
                Thread.sleep(500);

                // Elementin görünür olduğunu kontrol et
                wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            }

            // Elementi vurgula (opsiyonel)
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='3px solid red'", element);

        } catch (Exception e) {
            System.out.println("Element bulunamadı veya scroll işlemi başarısız oldu: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void sendKeysWithJSExecutor(By key, String text) {
        try {
            WebElement element = findElement(key);

            // Önce elementi temizle
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);

            // Elementi görünür yap ve odakla
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);

            // Metni karakterler halinde gönder
            for (char c : text.toCharArray()) {
                // Her karakteri ayrı ayrı gönder
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[0].value + arguments[1];",
                        element,
                        String.valueOf(c)
                );

                // Karakter arası küçük gecikme (daha doğal görünmesi için)
                Thread.sleep(100);
            }

            // Input event'ini tetikle
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                    element
            );

            // Change event'ini tetikle
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    element
            );

        } catch (Exception e) {
            System.out.println("JavaScript ile metin yazma işlemi başarısız oldu: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
