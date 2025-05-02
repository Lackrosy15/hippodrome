import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {
    @Test
    public void nullArgConstructorTest() {
        List<Horse> horses = new ArrayList<>();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());

        Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", exception2.getMessage());
    }

    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(i + "", Math.random(), Math.random()));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(hippodrome.getHorses(), horses);
    }

    @Test
    public void move() {
        List<Horse> horses = new ArrayList<>();
        Horse horse = Mockito.mock(Horse.class);


        for (int i = 0; i < 50; i++) {
            horses.add(horse);
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        verify(horse, times(50)).move();
    }
}
