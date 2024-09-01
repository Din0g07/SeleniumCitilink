package ru.dudareva.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object для страницы с товарами Citilink.
 * Предоставляет методы для взаимодействия с элементами на странице с товарами.
 * @version 1.0
 * @author Дударева Диана
 */
public class CitilinkCategoryPage extends BasePage {

    /**
     * XPath для заголовка категории.
     */
    private String CATEGORY_TITLE_XPATH = "//div[@itemscope and @itemtype='https://schema.org/BreadcrumbList']//span[@itemprop='name']";

    /**
     * XPath для поля ввода минимальной цены.
     */
    private String MIN_PRICE_INPUT_XPATH = "//input[@data-meta-name='FilterRangeGroup__input-min']";

    /**
     * XPath для поля ввода максимальной цены.
     */
    private String MAX_PRICE_INPUT_XPATH = "//input[@data-meta-name='FilterRangeGroup__input-max']";

    /**
     * XPath для кнопки "Показать все".
     */
    private String SHOW_ALL_BUTTON_XPATH = "//button[@data-meta-name='FilterGroup__show-all']";

    /**
     * XPath для чекбокса с брендом.
     */
    private String BRAND_CHECKBOX_XPATH = "//div[@data-meta-name='FilterLabel' and @data-meta-value='%s']//input";

    /**
     * XPath для кнопки принятия файлов cookie.
     */
    private String ACCEPT_COOKIES_BUTTON_XPATH = "//button[span[text()='Я согласен']]";

    /**
     * Вэб-элемент заголовка категории.
     */
    private WebElement categoryTitle;

    /**
     * Вэб-элемент поля ввода минимальной цены.
     */
    private WebElement minPriceInput;

    /**
     * Вэб-элемент поля ввода максимальной цены.
     */
    private WebElement maxPriceInput;

    /**
     * Вэб-элемент кнопки "Показать все".
     */
    private WebElement showAllButton;

    /**
     * Вэб-элемент кнопки принятия файлов cookie.
     */
    private WebElement acceptCookiesButton;

    /**
     * Вэб-элемент чекбокса с брендом.
     */
    private WebElement checkbox;

    /**
     * Конструктор объекта
     * @param driver ВэбДрайвер
     * @param wait Ожидание для ВэбДрайвера
     */
    public CitilinkCategoryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        //Эта инициализация подходит для инициализации по кнопкам
//        initializeElements();
    }

    /**
     * Инициализация элементов страницы.
     */
    private void initializeElements() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(MIN_PRICE_INPUT_XPATH)));
        this.minPriceInput = driver.findElement(By.xpath(MIN_PRICE_INPUT_XPATH));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(MAX_PRICE_INPUT_XPATH)));
        this.maxPriceInput = driver.findElement(By.xpath(MAX_PRICE_INPUT_XPATH));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH)));
        this.acceptCookiesButton = driver.findElement(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SHOW_ALL_BUTTON_XPATH)));
        this.showAllButton = driver.findElement(By.xpath(SHOW_ALL_BUTTON_XPATH));
    }

    /**
     * Метод для проверки перехода в раздел.
     * @param categoryName наименование раздела
     * @return true если наименование раздела совпадает с выбранным++---
     */
    public boolean isInCategory(String categoryName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CATEGORY_TITLE_XPATH)));
        categoryTitle = driver.findElement(By.xpath(CATEGORY_TITLE_XPATH));
        return categoryTitle.getText().trim().contains(categoryName);
    }

    /**
     * Метод для принятия файлов cookie.
     */
    public void acceptCookies() {
        initializeElements();
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton));
        acceptCookiesButton.click();
    }

    /**
     * Метод для нажатия кнопки "Показать все".
     */
    public void clickShowAllButton() {
        wait.until(ExpectedConditions.elementToBeClickable(showAllButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showAllButton);
    }

    // Целевой метод работы с фильтром цены, который по какой-то причине не отрабатывает
    // Было опробовано множетсво способов изменения цены через input'ы, изменения проходят, но на web странице никак не отображаются
    // Приняла решение изменять значения через обнавление ссылки (симулирует изменение input'ов)
    /**
     * Метод для установки диапазона цен через JavaScript.
     *
     * @param minPrice минимальная цена.
     * @param maxPrice максимальная цена.
     */
    public void setPriceRangeByButtons(int minPrice, int maxPrice) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value','"+maxPrice+"')", maxPriceInput);
        js.executeScript("arguments[0].setAttribute('value','"+minPrice+"')", minPriceInput);
    }

    /**
     * Метод для установки диапазона цен через обновление URL.
     *
     * @param minPrice минимальная цена.
     * @param maxPrice максимальная цена.
     */
    public void setPriceRangeByURL(int minPrice, int maxPrice) {
        String currentUrl = driver.getCurrentUrl();
        String newUrl;
        if (currentUrl.contains("?")) {
            newUrl = currentUrl + "&pf=discount.any%2Crating.any&f=discount.any%2Crating.any&r=price_filter_group_id%3A" + minPrice + "-" + maxPrice;
        } else {
            newUrl = currentUrl + "?pf=discount.any%2Crating.any&f=discount.any%2Crating.any&r=price_filter_group_id%3A" + minPrice + "-" + maxPrice;
        }
        driver.get(newUrl);
        wait.until(ExpectedConditions.urlContains("price_filter_group_id%3A" + minPrice + "-" + maxPrice));
    }

    /**
     * Метод для выбора бренда ноутбука.
     *
     * @param brand название бренда.
     */
    public void selectBrand(String brand) {
        checkbox = driver.findElement(By.xpath(String.format(BRAND_CHECKBOX_XPATH, brand.toUpperCase())));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }
}
