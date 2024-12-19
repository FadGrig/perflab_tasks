import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class task2 {

    static class Circle {
        private final double x, y, rd;

        public Circle(double x, double y, double rd) {
            this.x = x;
            this.y = y;
            this.rd = rd;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getRd() {
            return rd;
        }

        public int testCoordinate (Point point) {

            double distx = Math.abs(this.getX() - point.getX());
            double disty = Math.abs(this.getY() - point.getY());

            double pointsDist = Math.hypot(distx, disty); // Раст. через гипотенузу

            double difference = pointsDist - this.getRd();

	    if (Math.abs(difference) < 0.000001) { // Ноль с учетом погрешностей
		return 0;
	    } else if (difference < 0.0) { // Строго меньше нуля.
    		return 1;
	    } else {return 2;} // Вне круга
        }
    }

    static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return this.x;
        }
        public double getY() {
            return this.y;
        }
    }

    public static void main(String[] args) {

        Path circleCoordsPath = Paths.get(args[0]);
        Path pointsCoordsPath = Paths.get(args[1]);

        List<String> circleCoords = null;
        List<String> points = null;

        try {
            circleCoords = Files.readAllLines(circleCoordsPath); // Не стал усложнять чтение файла, т.к. txt
            points = Files.readAllLines(pointsCoordsPath);
        } catch (IOException e) {
	    System.out.println("Ошибка: " + e.getMessage());
        }

        double circleX = Double.parseDouble(circleCoords.get(0).split("\\s")[0]);
        double circleY = Double.parseDouble(circleCoords.get(0).split("\\s")[1]);
        double circleRd = Double.parseDouble(circleCoords.get(1));

        Circle circle = new Circle(circleX, circleY, circleRd);

        points.stream()
                .map((String str) -> new Point(Double.parseDouble(str.split("\\s")[0]),
                        Double.parseDouble(str.split("\\s")[1])))
                .map(circle::testCoordinate)
                .forEach(System.out::println);
    }
}