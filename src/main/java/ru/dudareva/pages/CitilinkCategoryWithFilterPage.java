package ru.dudareva.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dudareva.helpers.Assertions;

import java.util.List;

/**
 * Page Object для страницы с товарами с фильтрацией Citilink.
 * Предоставляет методы для взаимодействия с элементами на странице с товарами и применёнными фильтрами.
 * @version 1.0
 * @author Дударева Диана
 */
public class CitilinkCategoryWithFilterPage extends BasePage {

    /**
     * XPath для элементов продуктов.
     */
    private String PRODUCT_ELEMENTS_XPATH = "//div[@data-meta-name='ProductVerticalSnippet']";

    /**
     * XPath для элемента цены продукта.
     */
    private String ELEMENT_PRICE_XPATH = ".//span[@data-meta-name='Snippet__price']/span[@data-meta-price]";

    /**
     * XPath для поля поиска.
     */
    private String SEARCH_FIELD_XPATH = "//input[@type='search' and @name='text']";

    /**
     * XPath для кнопки поиска.
     */
    private String SEARCH_BUTTON_XPATH = "//button[@type='submit' and contains(@class, 'css-c064wa')]";

    /**
     * XPath для кнопки перехода на следующую страницу.
     */
    private String NEXT_PAGE_XPATH = "//a[@data-meta-name='PageLink__page-page-next']";

    /**
     * XPath для ссылки на первую страницу.
     */
    private String FIRST_PAGE_LINK_XPATH = "//a[@data-meta-name='PageLink__page-1']";

    /**
     * XPath для заголовка продукта.
     */
    private String ELEMENT_TITLE_XPATH = ".//a[@data-meta-name='Snippet__title']";

    /**
     * Веб-элемент поля поиска.
     */
    private WebElement inputSearch;

    /**
     * Веб-элемент кнопки поиска.
     */
    private WebElement searchButton;

    /**
     * Веб-элемент первого продукта в списке.
     */
    private WebElement firstProduct;

    /**
     * Список веб-элементов продуктов.
     */
    private List<WebElement> productElements;


    /**
     * Конструктор объекта
     * @param driver ВэбДрайвер
     * @param wait Ожидание для ВэбДрайвера
     */
    public CitilinkCategoryWithFilterPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Метод для проверки, что на странице есть более указанного числа элементов.
     *
     * @param number минимальное количество элементов, которые должны быть на странице.
     */
    public void hasMoreThanXElements(int number) {
        wait.until(ExpectedConditions.stalenessOf(driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH)).get(0)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ELEMENTS_XPATH)));
        Assertions.assertTrue(driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH)).size() > number,
                "На странице должно быть более " + number + " элементов");
    }

    /**
     * Метод для проверки, что результаты соответствуют заданным фильтрам по бренду и цене.
     *
     * @param expectedBrands список ожидаемых брендов.
     * @param minPrice минимальная цена.
     * @param maxPrice максимальная цена.
     */
    public void resultsMatchFilters(List<String> expectedBrands, int minPrice, int maxPrice) {
        productElements = driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH));
        while (true) {
            Assertions.assertTrue(productElements.stream()
                    .allMatch(product -> {
                        String title = product.getText();

                        boolean priceMatches = true;
                        if (!product.findElements(By.xpath(ELEMENT_PRICE_XPATH)).isEmpty()) {
                            WebElement priceElement = product.findElement(By.xpath(ELEMENT_PRICE_XPATH));
                            int price = Integer.parseInt(priceElement.getAttribute("data-meta-price"));
                            priceMatches = (price >= minPrice && price <= maxPrice);
                        }
                        boolean brandMatches = expectedBrands.stream().anyMatch(title::contains);

                        return brandMatches && priceMatches;
                    }), "Все результаты должны соответствовать фильтрам по бренду и цене");

            if (!driver.findElements(By.xpath(NEXT_PAGE_XPATH)).isEmpty()) {
                driver.findElement(By.xpath(NEXT_PAGE_XPATH)).click();
                wait.until(ExpectedConditions.stalenessOf(driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH)).get(0)));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ELEMENTS_XPATH)));
                productElements = driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH));
            } else {
                break;
            }
        }
    }

    /**
     * Метод для получения имени продукта по индексу.
     *
     * @param index индекс продукта в списке.
     * @return имя продукта.
     */
    public String getProductNameByIndex(int index) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FIRST_PAGE_LINK_XPATH)));
        driver.findElement(By.xpath(FIRST_PAGE_LINK_XPATH)).click();
        wait.until(ExpectedConditions.stalenessOf(driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH)).get(0)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRODUCT_ELEMENTS_XPATH)));
        firstProduct = driver.findElements(By.xpath(PRODUCT_ELEMENTS_XPATH)).get(index).findElement(
                By.xpath(ELEMENT_TITLE_XPATH));
        return firstProduct.getAttribute("title");
    }

    /**
     * Метод для поиска продукта по имени.
     *
     * @param productName имя продукта для поиска.
     */
    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_FIELD_XPATH)));
        inputSearch = driver.findElement(By.xpath(SEARCH_FIELD_XPATH));
        inputSearch.sendKeys(productName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_BUTTON_XPATH)));
        searchButton = driver.findElement(By.xpath(SEARCH_BUTTON_XPATH));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }
}
