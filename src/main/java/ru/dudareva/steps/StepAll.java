package ru.dudareva.steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dudareva.helpers.Assertions;
import ru.dudareva.pages.CitilinkCatalogPage;
import ru.dudareva.pages.CitilinkCategoryPage;
import ru.dudareva.pages.CitilinkCategoryWithFilterPage;
import ru.dudareva.pages.CitilinkMainPage;
import ru.dudareva.pages.CitilinkSearchPage;

import java.util.List;

/**
 * Класс StepAll представляет собой шаги для автоматизации тестирования сайта Citilink.
 * Использует аннотации Allure для формирования отчётов о тестировании.
 * @version 1.0
 * @author Дударева Диана
 */
public class StepAll {

    /**
     * Переменная для ожидания загрузки объектов ВэбДрайвером.
     */
    private static WebDriverWait wait;

    /**
     * Переменная для ВэбДрайвера, берется из переменной среды в конфигурации.
     */
    private static WebDriver driver;

    /**
     * Экземпляр страницы CitilinkMainPage.
     */
    private static CitilinkMainPage citilinkMainPage;

    /**
     * Экземпляр страницы CitilinkCatalogPage.
     */
    private static CitilinkCatalogPage citilinkCatalogPage;

    /**
     * Экземпляр страницы CitilinkLaptopsPage.
     */
    private static CitilinkCategoryPage citilinkCategoryPage;

    /**
     * Экземпляр страницы CitilinkLaptopsWithFilterPage.
     */
    private static CitilinkCategoryWithFilterPage citilinkCategoryWithFilterPage;

    /**
     * Экземпляр страницы CitilinkSearchPage.
     */
    private static CitilinkSearchPage citilinkSearchPage;

    /**
     * Переход на сайт.
     *
     * @param url URL сайта для перехода.
     * @param currentDriver Текущий WebDriver для взаимодействия с веб-страницей.
     */
    @Step("Переход на сайт: {url}")
    public static void openSite(String url, WebDriver currentDriver){
        driver=currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver,10);
        citilinkMainPage = new CitilinkMainPage(driver, wait);
        citilinkCatalogPage = new CitilinkCatalogPage(driver, wait);
        citilinkCategoryPage = new CitilinkCategoryPage(driver, wait);
        citilinkCategoryWithFilterPage = new CitilinkCategoryWithFilterPage(driver, wait);
        citilinkSearchPage = new CitilinkSearchPage(driver, wait);
        Assertions.assertEquals(url, driver.getCurrentUrl(),
                "Должна открыться главная страница " + url);
    }

    /**
     * Переход в каталог товаров.
     */
    @Step("Переход в каталог товаров")
    public static void goToCatalog(){
        citilinkMainPage = new CitilinkMainPage(driver, wait);
        citilinkMainPage.clickOnCatalog();
    }

    /**
     * Наведение мыши на раздел и выбор подраздела по наименованию.
     *
     * @param categoryName наименование раздела
     */
    @Step("Наведение мыши на раздел с текстом {categoryName} и выбор подраздела {categoryName}")
    public static void clickOnCategory(String categoryName){
        citilinkCatalogPage.moveToCategory(categoryName);
        citilinkCatalogPage.clickOnSubCategory(categoryName);
    }

    /**
     * Проверка перехода в подраздел.
     */
    @Step("Проверка, перехода в раздел {categoryName}")
    public static void checkCategory(String categoryName) {
        Assertions.assertTrue(citilinkCategoryPage.isInCategory(categoryName), "Должен произойти переход в раздел " + categoryName);
    }

    /**
     * Фильтрация списка по цене.
     *
     * @param minPrice Минимальная цена.
     * @param maxPrice Максимальная цена.
     */
    @Step("Фильтрация списка по цене: {price}")
    public static void filterByPrice(int minPrice, int maxPrice){
        citilinkCategoryPage.setPriceRangeByURL(minPrice, maxPrice);
    }

    /**
     * Фильтрация списка по брендам.
     *
     * @param brands Список брендов для фильтрации.
     */
    @Step("Фильтрация списка по брендам: {brands}")
    public static void filterByBrands(List<String> brands){
        citilinkCategoryPage.acceptCookies();
        citilinkCategoryPage.clickShowAllButton();
        brands.forEach(brand -> citilinkCategoryPage.selectBrand(brand));
    }

    /**
     * Проверка, что на странице чем указанное количество товаров.
     *
     * @param number Минимальное количество товаров.
     */
    @Step("Проверка, что на странице более чем {number} товаров")
    public static void checkProductsByNumber(int number){
        citilinkCategoryWithFilterPage.hasMoreThanXElements(number);
    }

    /**
     * Проверка, что товары соответствуют условиям фильтрации.
     *
     * @param brands Список брендов.
     * @param minPrice Минимальная цена.
     * @param maxPrice Максимальная цена.
     */
    @Step("Проверка, что товары соответствуют условиям фильтрации")
    public static void checkResultsMatchFilters(List<String> brands, int minPrice, int maxPrice){
        citilinkCategoryWithFilterPage.resultsMatchFilters(brands, minPrice, maxPrice);
    }

    /**
     * Получение наименования товара по индексу.
     *
     * @param index Индекс товара.
     * @return Название товара.
     */
    @Step("Получение наименования товара по индексу {index}")
    public static String getProductNameByIndex(int index){
        return citilinkCategoryWithFilterPage.getProductNameByIndex(index);
    }

    /**
     * Поиск товара по имени.
     *
     * @param productName Имя товара для поиска.
     */
    @Step("Поиск по имени товара: {productName}")
    public static void searchByProductName(String productName){
        citilinkCategoryWithFilterPage.searchProduct(productName);
    }

    /**
     * Проверка результатов поиска.
     *
     * @param productName Имя товара для проверки в результатах поиска.
     */
    @Step("Проверка результатов поиска по имени товара: {productName}")
    public static void checkSearchResults(String productName){
        citilinkSearchPage.searchResultsHasProductName(productName);
    }

}
