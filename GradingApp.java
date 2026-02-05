import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GradingApp {
    private static final Scanner SCANNER = new Scanner(System.in);

    private static String[] gradeAndRemark(int score) {
        if (score >= 80 && score <= 100) return new String[]{"1", "D1"};
        if (score >= 75) return new String[]{"2", "D2"};
        if (score >= 66) return new String[]{"3", "C3"};
        if (score >= 60) return new String[]{"4", "C4"};
        if (score >= 50) return new String[]{"5", "C5"};
        if (score >= 45) return new String[]{"6", "C6"};
        if (score >= 35) return new String[]{"7", "P7"};
        if (score >= 30) return new String[]{"8", "P8"};
        if (score >= 0) return new String[]{"9", "F"};
        return new String[]{"-", "Invalid"};
    }

    private static int promptScore(String label) {
        while (true) {
            System.out.print(label + " score (0-100): ");
            if (SCANNER.hasNextInt()) {
                int s = SCANNER.nextInt();
                if (s >= 0 && s <= 100) return s;
            } else {
                SCANNER.next();
            }
            System.out.println("Invalid input. Enter an integer 0-100.");
        }
    }

    private static void singleStudentFlow() {
        int score = promptScore("Enter");
        String[] res = gradeAndRemark(score);
        System.out.printf("Score: %d | Grade: %s | Remark: %s%n", score, res[0], res[1]);
    }

    private static void fiveStudentsSummary() {
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            int score = promptScore("Student " + i + "");
            String[] res = gradeAndRemark(score);
            counts.merge(res[0], 1, Integer::sum);
            System.out.printf("Student %d -> Score: %d | Grade: %s | Remark: %s%n", i, score, res[0], res[1]);
        }
        System.out.println("\nSummary (Grade : Count)");
        for (int g = 1; g <= 9; g++) {
            String key = String.valueOf(g);
            System.out.printf("%s : %d%n", key, counts.getOrDefault(key, 0));
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Single student ---");
        singleStudentFlow();
        System.out.println("\n--- Five students batch ---");
        fiveStudentsSummary();
    }
}
