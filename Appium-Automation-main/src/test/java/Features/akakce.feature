@AkakceTest
  Feature:Akakce Mobil Uygulaması icin Test
    Scenario:Akakce Mobil Uygulaması ile Telefon Satin Alma Uygulaması
      When Arama yapilir
      When Filtreleme ve siralama yapilir
      When Onuncu urune scroll edilip secilir
      Then Saticiye git butonu kontrol edilir
