package ru.dudareva.helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

/**
 * Класс DataProvider предоставляет данные для параметризированных тестов.
 * @version 1.0
 * @author Дударева Диана
 */
public class DataProvider {

    /**
     * Метод для предоставления аргументов для тестов Citilink.
     * @return Поток аргументов для тестов, содержащий наименование раздела, минимальную цену,
     * максимальную цену, список брендов, минимальное количество товаров и индекс товара.
     */
    public static Stream<Arguments> providerCitilinkArgs(){
        return Stream.of(
                Arguments.of("Ноутбуки", 20000, 120000, List.of("HP", "Lenovo"), 12, 0)
        );
    }
}
