package com.zavadimka.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.zavadimka.config.BrowserConfig;
import com.zavadimka.helpers.Attach;
import com.zavadimka.pages.ProjectDataConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    @BeforeAll
    static void beforeAll(){

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1480";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        // Подключаем удалённый браузер для выполнения тестов
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }

    @BeforeEach
    void beforeEach(){
        // Подключаем логгер и добавляем слушателя
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        ProjectDataConfig projectDataConfig = createProjectDataConfig();
        BrowserConfig browserConfig = new BrowserConfig();

        System.out.println("The test is run with the following parameters:");
        projectDataConfig.printProjectConfig();
        browserConfig.printBrowserConfig();
    }

    public ProjectDataConfig createProjectDataConfig() {
        String environment = System.getProperty("environment", "stage");

        System.setProperty("environment", environment);
        return new ProjectDataConfig(environment);
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
