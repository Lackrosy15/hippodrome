import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.anyDouble;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    @Test
    @DisplayName("Check Throwable and ThrowableMessage be null")
    public void exceptionTestConstructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0.1, 0.1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    static Stream<Arguments> provideDataForAddition() {
        return Stream.of(
                arguments("", 0.1, 0.1),
                arguments(" ", 0.1, 0.1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForAddition")
    @DisplayName("Check blank string")
    public void exceptionTest(String name, double speed, double distance) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    @DisplayName("Return negative exception")
    public void negativeTestConstructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", -2, 0.1));
        assertEquals("Speed cannot be negative.", exception.getMessage());

        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", 0.1, -2));
        assertEquals("Distance cannot be negative.", exception1.getMessage());
    }

    @Test
    public void testGetters() {
        Horse horse1 = new Horse("horse", 1, 1);
        Horse horse2 = new Horse("horse", 1);

        assertEquals("horse", horse1.getName());
        assertEquals(1, horse1.getSpeed());
        assertEquals(1, horse1.getDistance());

        assertEquals(0, horse2.getDistance());
    }

    @Test
    @DisplayName("Get random in move-method")
    public void testMove() {
        Horse horse1 = new Horse("horse", 1, 1);

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            horse1.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @DisplayName("right formula")
    @CsvSource({"0.1, 1, 1, 1.1", "0.4, 3, 4, 5.2"})
    public void testMoveFormula(double randomReturn, double speed, double distance, double expected) {
        Horse horse = new Horse("horse", speed, distance);
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomReturn);
            horse.move();
            assertEquals(expected, horse.getDistance());
        }
    }
}
