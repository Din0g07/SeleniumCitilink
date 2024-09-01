package ru.dudareva.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dudareva.helpers.Assertions;

/**
 * Page Object для страницы Citilink
 * Предоставляет методы для взаимодействия с элементами на главной странице.
 * @version 1.0
 * @author Дударева Диана
 */
public class CitilinkMainPage extends BasePage {

    /**
     * CSS селектор для кнопки каталога.
     */
    private String CATALOG_BUTTON_CSS = "a[data-meta-name='DesktopHeaderFixed__catalog-menu']";

    /**
     * Веб-элемент, представляющий кнопку каталога.
     */
    private WebElement catalogButton;

    /**
     * Конструктор объекта
     * @param driver ВэбДрайвер
     * @param wait Ожидание для ВэбДрайвера
     */
    public CitilinkMainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Метод для наведения курсора на кнопку «Каталог товаров» и отображения выпадающего меню
     * После нажатия проверяет, что текущий URL совпадает с URL каталога.
     */
    public void clickOnCatalog() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CATALOG_BUTTON_CSS)));
        catalogButton = driver.findElement(By.cssSelector(CATALOG_BUTTON_CSS));
        wait.until(ExpectedConditions.elementToBeClickable(catalogButton));
        catalogButton.click();
        Assertions.assertEquals("https://www.citilink.ru/catalog/", driver.getCurrentUrl(),
                "Должна открыться страница каталога");
    }


}
