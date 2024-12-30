package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.ElementHelper;

import java.time.Duration;

public class HomePage {
    AppiumDriver driver;
    WebDriverWait wait;
    ElementHelper elementHelper;
    By popUpButonu  = MobileBy.AndroidUIAutomator("new UiSelector().text(\"Allow\")");
    By uyeOlmadanDevamButon  = MobileBy.AndroidUIAutomator("new UiSelector().text(\"Üye Olmadan Devam Et\")");
    By searchBar = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.akakce.akakce:id/searchTextView\")");

    By searchTextArea = MobileBy.AndroidUIAutomator("new UiSelector().text(\"Neyi ucuza almak istiyorsun?\")");

    By filterButton = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.akakce.akakce:id/filterText\")");

    By pcDonaimFilter  = MobileBy.AndroidUIAutomator("new UiSelector().text(\"Bilgisayar, Donanım\")");

    By urunleriGorButton  = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.akakce.akakce:id/applyFilterBtn\")");

    By siralaButton  = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.akakce.akakce:id/sortArea\")");

    By enYuksekFiyatFilter  = MobileBy.AndroidUIAutomator("new UiSelector().text(\"En Yüksek Fiyat\")");

    By uruneGitButon  = MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.akakce.akakce:id/detailBtnLayout\")");

    By saticiyaGitButon  = MobileBy.AndroidUIAutomator("new UiSelector().text(\"Satıcıya Git\")");


    public HomePage(AppiumDriver driver){
        this.driver=driver;
        this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        this.elementHelper=new ElementHelper(driver);
    }

    public void popUpKapat() {
        elementHelper.click(popUpButonu);
    }

    public void uyeOlmadanDevamEt() {
        elementHelper.click(uyeOlmadanDevamButon);
    }


    public void search() {
        elementHelper.click(searchBar);
        // Arama çubuğunu bul ve metni gönder
        elementHelper.click(searchTextArea);
        elementHelper.typeWithKeyboard(searchTextArea, "Laptop");
        //elementHelper.sendKeysWithJSExecutor(searchTextArea,"Laptop");
    }

    public void filtreleVesirala() {
        elementHelper.click(filterButton);
        elementHelper.click(pcDonaimFilter);
        elementHelper.click(urunleriGorButton);
        elementHelper.click(siralaButton);
        elementHelper.click(enYuksekFiyatFilter);

    }

    public void scrollveclick() {
       elementHelper.scrollToAndClickTenthProduct();
       elementHelper.scrollToElementAndCenter(uruneGitButon);
       elementHelper.click(uruneGitButon);
    }

    public void butonKontrol() {
        elementHelper.checkVisible(saticiyaGitButon);

    }



}
