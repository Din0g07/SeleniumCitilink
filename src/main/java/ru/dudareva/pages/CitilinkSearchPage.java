package ru.dudareva.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dudareva.helpers.Assertions;

import java.util.List;

/**
 * Page Object для страницы поиска Citilink.
 * Предоставляет методы для взаимодействия с элементами на странице результатов поиска.
 * @version 1.0
 * @author Дударева Диана
 */
public class CitilinkSearchPage extends BasePage{

    /**
     * XPath для элементов продуктов.
     */
    private String PRODUCT_ELEMENTS_XPATH = "//div[@data-meta-name='ProductVerticalSnippet']";

    /**
     * Список веб элементов результатов поиска.
     */
    private List<WebElement> productElements;

    /**
     * Конструктор объекта
     * @param driver ВэбДрайвер
     * @param wait Ожидание для ВэбДрайвера
     */
    public CitilinkSearchPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Метод для проверки, что результаты поиска содержат указанный товар.
     *
     * @param productName имя товара для поиска среди результатов.
     */
    public void searchResultsHasProductName(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ELEMENTS_XPATH)));
        productElements = driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH));
        Assertions.assertTrue(productElements.stream()
                .map(elem -> elem.findElement(By.xpath(".//a[@data-meta-name='Snippet__title']")))
                .anyMatch(elem -> elem.getAttribute("title").equals(productName)), "Результат поиска должен содержать нужный товар");

    }
}
