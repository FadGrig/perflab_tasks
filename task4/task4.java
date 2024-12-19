import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;

public class task4 {
    public static void main(String[] args) {

        Path filePath = Paths.get(args[0]);
        List<Long> nums = null;

        try {
            nums = Files.readAllLines(filePath)
                           .stream()
                           .map(Long::parseLong)
                           .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка: " + e.getMessage());
        }

        double aver = nums.stream() // Ближайшее число для сокращения по среднему
                             .mapToLong(x -> x)
                             .average()
                             .orElseThrow(() -> new RuntimeException("ошибка EmptyList"));

        long roundedAver = Math.round(aver);
        long result  = nums.stream() // Через модули разниц получаем количество шагов
                                   .mapToLong(number -> Math.abs(roundedAver - number))
                                   .sum();
        System.out.println(result);
    }
}