package ru.dudareva;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static ru.dudareva.steps.StepAll.*;

/**
 * Класс для тестирования функциональности сайта Citilink.
 * @version 1.0
 * @author Дударева Диана
 */
public class CitilinkTest extends BaseTest {

    /**
     * Тест для проверки секции ноутбуков на сайте Citilink.
     * Выполняет последовательность шагов для фильтрации ноутбуков по цене и бренду, а также проверки результатов поиска.
     *
     * @param categoryName название раздела
     * @param minPrice минимальная цена для фильтрации
     * @param maxPrice максимальная цена для фильтрации
     * @param brands список брендов для фильтрации
     * @param number минимальное количество ожидаемых товаров
     * @param index индекс товара для поиска
     */
    @Feature("Проверка секции ноутбуки")
    @DisplayName("Проверка секции ноутбуки - проверки в степах")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("ru.dudareva.helpers.DataProvider#providerCitilinkArgs")
    public void testCitilinkLaptopCategoryWithSteps(String categoryName, int minPrice, int maxPrice, List<String> brands, int number, int index) {
        openSite("https://www.citilink.ru/", webDriver);
        goToCatalog();
        clickOnCategory(categoryName);
        checkCategory(categoryName);
        filterByPrice(minPrice, maxPrice);
        filterByBrands(brands);
        checkProductsByNumber(number);
        checkResultsMatchFilters(brands, minPrice, maxPrice);
        String name = getProductNameByIndex(index);
        searchByProductName(name);
        checkSearchResults(name);
    }

}
