package com.home.monty_hall;

import org.junit.jupiter.api.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// Класс для тестирования логики MontyHallSimulator
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Используем общий жизненный цикл для тестов
public class MontyHallSimulatorTest {

    private MontyHallSimulator simulator;

    @BeforeAll
    static void setupAll() {
        System.out.println("Инициализация тестов для MontyHallSimulator...");
    }

    @BeforeEach
    void setup() {
        simulator = new MontyHallSimulator(); // Создаем новый экземпляр перед каждым тестом
    }

    @AfterEach
    void tearDown() {
        System.out.println("Тест завершен.");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Все тесты завершены.");
    }

    @Test
    void testInitializeGame() {
        // Given: Создаем симулятор
        MontyHallSimulator simulator = new MontyHallSimulator();
        simulator.totalDoors = 3;

        // When: Инициализируем игру
        simulator.initializeGame();

        // Then: Убеждаемся, что выигрышная дверь корректно выбрана
        assertTrue(simulator.winningDoor >= 0 && simulator.winningDoor < simulator.totalDoors,
                "Выигрышная дверь должна быть в пределах диапазона дверей");
    }

    @Test
    void testSwitchDoorStrategy() {
        // Given: Создаем симулятор и задаем параметры
        MontyHallSimulator simulator = new MontyHallSimulator();
        simulator.totalDoors = 3;
        int games = 1000;
        int wins = 0;

        // When: Запускаем 1000 игр со стратегией смены двери
        for (int i = 0; i < games; i++) {
            simulator.initializeGame();
            if (simulator.playRound(true)) { // Смена двери
                wins++;
            }
        }

        // Then: Проверяем, что процент выигрышей соответствует ожиданиям
        double winRate = (wins / (double) games) * 100;
        assertTrue(winRate > 60, "Процент выигрышей при смене двери должен быть больше 60%");
        System.out.printf("Процент выигрышей при смене двери: %.2f%%%n", winRate);
    }

    @Test
    void testKeepDoorStrategy() {
        // Given: Создаем симулятор и задаем параметры
        MontyHallSimulator simulator = new MontyHallSimulator();
        simulator.totalDoors = 3;
        int games = 1000;
        int wins = 0;

        // When: Запускаем 1000 игр без смены двери
        for (int i = 0; i < games; i++) {
            simulator.initializeGame();
            if (simulator.playRound(false)) { // Без смены двери
                wins++;
            }
        }

        // Then: Проверяем, что процент выигрышей соответствует ожиданиям
        double winRate = (wins / (double) games) * 100;
        assertTrue(winRate < 40, "Процент выигрышей без смены двери должен быть меньше 40%");
        System.out.printf("Процент выигрышей без смены двери: %.2f%%%n", winRate);
    }



    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testPlayRoundWithDifferentStrategies(boolean switchDoor) {
        // Given: Создаем симулятор
        MontyHallSimulator simulator = new MontyHallSimulator();
        simulator.totalDoors = 3;

        // When: Запускаем один раунд с указанной стратегией
        simulator.initializeGame();
        boolean result = simulator.playRound(switchDoor);

        // Then: Убеждаемся, что результат корректен
        assertNotNull(result, "Результат игры не должен быть null");
    }

    @Test
    void testInvalidNumberOfDoors() {
        // Given: Создаем симулятор с некорректным количеством дверей
        MontyHallSimulator simulator = new MontyHallSimulator();
        simulator.totalDoors = 2; // Некорректное значение

        // When: Пытаемся инициализировать игру
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, simulator::initializeGame);

        // Then: Убеждаемся, что выбрасывается исключение
        assertEquals("Количество дверей должно быть >= 3", exception.getMessage(),
                "Сообщение исключения должно быть корректным");
    }


    @Test
    void testInvalidNumberOfGames() {
        // Given: Создаем симулятор
        MontyHallSimulator simulator = new MontyHallSimulator();
        simulator.totalDoors = 3;

        // When: Пытаемся запустить симуляцию с некорректным количеством игр
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> simulator.simulateGames(0, true));

        // Then: Убеждаемся, что выбрасывается исключение
        assertEquals("Число игр должно быть больше 0", exception.getMessage(),
                "Сообщение исключения должно быть корректным");
    }


}

