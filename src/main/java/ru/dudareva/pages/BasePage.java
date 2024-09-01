package ru.dudareva.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Базовый класс для всех объектов страниц в приложении.
 * Содержит общие свойства и методы, используемые на различных страницах.
 * @version 1.0
 * @autor Дударева Диана
 */
public class BasePage {

    /**
     * Экземпляр WebDriver, используемый для взаимодействия с веб-браузером.
     */
    protected WebDriver driver;

    /**
     * Экземпляр WebDriverWait, используемый для реализации явных ожиданий.
     */
    protected WebDriverWait wait;

    /**
     * Конструктор для инициализации BasePage с WebDriver и WebDriverWait.
     *
     * @param driver Экземпляр WebDriver для взаимодействия с веб-браузером.
     * @param wait Экземпляр WebDriverWait для реализации явных ожиданий.
     */
    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
}
