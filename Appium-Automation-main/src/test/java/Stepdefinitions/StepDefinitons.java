package Stepdefinitions;

import Pages.HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import util.DriverFactory;

public class StepDefinitons {

    HomePage homePage = new HomePage(DriverFactory.getDriver());

    @When("Arama yapilir")
    public void kategorilereTiklanir() {
        //Apk kaynaklı olması gerek ki, hiçbir input text metodu varyasyonları ile ekrana gönderilen text gelmiyor.
        //Manuel olarak geçmek durumunda kalabiliyoruz. (Search adımı için)
        homePage.popUpKapat();
        homePage.uyeOlmadanDevamEt();
        homePage.search();
    }

    @When("Filtreleme ve siralama yapilir")
    public void filtrelevesirala() {
        homePage.filtreleVesirala();
    }

    @When("Onuncu urune scroll edilip secilir")
    public void scrolledipsecilir() {
        homePage.scrollveclick();
    }

    @Then("Saticiye git butonu kontrol edilir")
    public void butonkontrol() {
        homePage.butonKontrol();
    }


}
