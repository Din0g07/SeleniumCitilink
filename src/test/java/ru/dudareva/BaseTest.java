package ru.dudareva;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Базовый класс для тестов.
 * Настраивает WebDriver и управляет его жизненным циклом.
 * В этом классе настраивается WebDriver для работы с браузером Chrome,
 * инициализируется перед каждым тестом и завершается после каждого теста.
 * @version 1.0
 * @author Дударева Диана
 */
public class BaseTest {

    /**
     * WebDriver для взаимодействия с браузером.
     * Объявляется как защищенный, чтобы подклассы могли использовать его напрямую.
     */
    protected WebDriver webDriver;

    /**
     * Метод для инициализации WebDriver перед каждым тестом.
     * Настраивает свойства WebDriver и запускает браузер Chrome.
     * Устанавливает стратегию загрузки страницы на "none" для ускорения тестов.
     */
    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver-win64/chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY,"none");
        webDriver = new ChromeDriver(capabilities);
        webDriver.manage().window().maximize();
    }

    /**
     * Метод для завершения работы WebDriver после каждого теста.
     * Закрывает браузер и завершает сессию WebDriver.
     */
    @AfterEach
    public void after() {
        webDriver.quit();
    }
}
