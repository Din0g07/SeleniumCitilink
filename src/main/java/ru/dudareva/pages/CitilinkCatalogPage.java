package ru.dudareva.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object для страницы каталога Citilink.
 * Предоставляет методы для взаимодействия с элементами на странице каталога.
 * @version 1.0
 * @author Дударева Диана
 */
public class CitilinkCatalogPage extends BasePage {

    /**
     * Переменная для действий с элементами на странице.
     */
    private final Actions actions;

    /**
     * XPath для раздела.
     */
    private String CATEGORY_XPATH = "//a[contains(@class, 'CatalogLayout__link_level-1') and contains(., '%s')]";

    /**
     * XPath для подраздела.
     */
    private String SUBCATEGORY_XPATH = "//li[@class='CatalogLayout__children-item']//a[text()='%s']";

    /**
     * Вэб-элемент раздела.
     */
    private WebElement category;

    /**
     * Вэб-элемент подраздела.
     */
    private WebElement subcategory;

    /**
     * Конструктор объекта
     * @param driver ВэбДрайвер
     * @param wait Ожидание для ВэбДрайвера
     */
    public CitilinkCatalogPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.actions = new Actions(driver);
    }

    /**
     * Метод для наведения курсора на раздел.
     *
     * @param categoryName наименовие раздела
     */
    public void moveToCategory(String categoryName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(CATEGORY_XPATH, categoryName))));
        category = driver.findElement(By.xpath(String.format(CATEGORY_XPATH, categoryName)));
        actions.moveToElement(category).perform();
    }

    /**
     * Метод для клика на элемент подраздела.
     *
     * @param categoryName наименовие подраздела
     */
    public void clickOnSubCategory(String categoryName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(SUBCATEGORY_XPATH, categoryName))));
        subcategory = driver.findElement(By.xpath(String.format(SUBCATEGORY_XPATH, categoryName)));
        wait.until(ExpectedConditions.elementToBeClickable(subcategory));
        subcategory.click();
    }
}
