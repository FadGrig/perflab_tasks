public class task1 {

    public static void main(String[] args) {

        int n = -1, m  = -1, curPos = 0;

        try {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);

	    if (n < 0 || m < 0) {
            System.err.println("Аргументы должны быть положительными");
            System.exit(0);
        }

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(0);
        }

        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = i + 1;
        }

        StringBuilder resultBuilder = new StringBuilder();

        do {
            resultBuilder.append(array[curPos]);
            curPos = (curPos -1 + m) % n;
        }
        while (curPos != 0);

        String result = resultBuilder.toString();
        System.out.println(result);
    }
}