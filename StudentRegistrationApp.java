import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StudentRegistrationApp extends Application {
    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField email = new TextField();
    private final TextField emailConfirm = new TextField();
    private final PasswordField password = new PasswordField();
    private final PasswordField passwordConfirm = new PasswordField();
    private final ComboBox<Integer> yearBox = new ComboBox<>();
    private final ComboBox<Month> monthBox = new ComboBox<>();
    private final ComboBox<Integer> dayBox = new ComboBox<>();
    private final ComboBox<String> genderBox = new ComboBox<>();
    private final ComboBox<String> deptBox = new ComboBox<>();
    private final TextArea output = new TextArea();

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$");
    private static final ConcurrentHashMap<Integer, AtomicInteger> COUNTERS = new ConcurrentHashMap<>();
    private static final Path CSV_PATH = Path.of("students.csv");

    @Override
    public void start(Stage stage) {
        output.setEditable(false);
        output.setPrefWidth(360);

        setupDateBoxes();
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        deptBox.setItems(FXCollections.observableArrayList("Civil", "CSE", "Electrical", "E&C", "Mechanical"));

        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        submit.setOnAction(e -> handleSubmit());
        cancel.setOnAction(e -> clearForm());

        GridPane form = buildForm(submit, cancel);

        BorderPane root = new BorderPane();
        root.setLeft(form);
        root.setCenter(buildOutputArea());
        root.setPadding(new Insets(12));

        stage.setTitle("New Student Registration");
        stage.setScene(new Scene(root, 940, 420));
        stage.show();
    }

    private GridPane buildForm(Button submit, Button cancel) {
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(12));

        int r = 0;
        grid.add(new Label("First Name"), 0, r);
        grid.add(firstName, 1, r++);
        grid.add(new Label("Last Name"), 0, r);
        grid.add(lastName, 1, r++);
        grid.add(new Label("Email"), 0, r);
        grid.add(email, 1, r++);
        grid.add(new Label("Confirm Email"), 0, r);
        grid.add(emailConfirm, 1, r++);
        grid.add(new Label("Password"), 0, r);
        grid.add(password, 1, r++);
        grid.add(new Label("Confirm Password"), 0, r);
        grid.add(passwordConfirm, 1, r++);

        grid.add(new Label("DOB (Y/M/D)"), 0, r);
        HBox dobBox = new HBox(6, yearBox, monthBox, dayBox);
        grid.add(dobBox, 1, r++);

        grid.add(new Label("Gender"), 0, r);
        grid.add(genderBox, 1, r++);
        grid.add(new Label("Department"), 0, r);
        grid.add(deptBox, 1, r++);

        HBox buttons = new HBox(10, submit, cancel);
        buttons.setAlignment(Pos.CENTER_LEFT);
        grid.add(buttons, 1, r);

        return grid;
    }

    private BorderPane buildOutputArea() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(12));
        pane.setTop(new Label("Your Data is Below:"));
        pane.setCenter(output);
        return pane;
    }

    private void setupDateBoxes() {
        int currentYear = LocalDate.now().getYear();
        yearBox.setItems(FXCollections.observableArrayList(range(currentYear - 60, currentYear - 16)));
        monthBox.setItems(FXCollections.observableArrayList(Month.values()));
        yearBox.setOnAction(e -> refreshDays());
        monthBox.setOnAction(e -> refreshDays());
    }

    private void refreshDays() {
        Integer y = yearBox.getValue();
        Month m = monthBox.getValue();
        if (y == null || m == null) return;
        int days = m.length(java.time.Year.isLeap(y));
        dayBox.setItems(FXCollections.observableArrayList(range(1, days)));
    }

    private static java.util.List<Integer> range(int startInclusive, int endInclusive) {
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int i = startInclusive; i <= endInclusive; i++) {
            list.add(i);
        }
        return list;
    }

    private void handleSubmit() {
        String fn = firstName.getText().trim();
        String ln = lastName.getText().trim();
        String em = email.getText().trim();
        String emc = emailConfirm.getText().trim();
        String pw = password.getText();
        String pwc = passwordConfirm.getText();
        Integer y = yearBox.getValue();
        Month m = monthBox.getValue();
        Integer d = dayBox.getValue();
        String gender = genderBox.getValue();
        String dept = deptBox.getValue();

        StringBuilder errors = new StringBuilder();
        if (fn.isEmpty()) errors.append("First name required\n");
        if (ln.isEmpty()) errors.append("Last name required\n");
        if (em.isEmpty() || emc.isEmpty()) errors.append("Email and confirmation required\n");
        if (!em.equals(emc)) errors.append("Emails do not match\n");
        if (!EMAIL_PATTERN.matcher(em).matches()) errors.append("Email invalid\n");
        if (pw.isEmpty() || pwc.isEmpty()) errors.append("Password and confirmation required\n");
        if (!pw.equals(pwc)) errors.append("Passwords do not match\n");
        if (!PASSWORD_PATTERN.matcher(pw).matches()) errors.append("Password must be 8-20 chars, at least one letter and digit\n");
        if (y == null || m == null || d == null) errors.append("DOB required\n");
        LocalDate dob = null;
        if (y != null && m != null && d != null) {
            dob = LocalDate.of(y, m, d);
            int age = Period.between(dob, LocalDate.now()).getYears();
            if (age < 16 || age > 60) errors.append("Age must be 16-60\n");
        }
        if (gender == null) errors.append("Gender required\n");
        if (dept == null) errors.append("Department required\n");

        if (errors.length() > 0) {
            showError(errors.toString());
            return;
        }

        String id = generateId(LocalDate.now().getYear());
        String record = String.format(Locale.US, "ID: %s | %s %s | %s | %s | %s | %s", id, fn, ln, gender.charAt(0), dept, dob, em);
        output.appendText(record + System.lineSeparator());
        appendCsv(id, fn + " " + ln, gender, dept, dob.toString(), em);
        clearForm();
    }

    private static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Errors");
        alert.setHeaderText("Please fix the issues below");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void clearForm() {
        firstName.clear();
        lastName.clear();
        email.clear();
        emailConfirm.clear();
        password.clear();
        passwordConfirm.clear();
        yearBox.setValue(null);
        monthBox.setValue(null);
        dayBox.setValue(null);
        genderBox.setValue(null);
        deptBox.setValue(null);
    }

    private static String generateId(int year) {
        AtomicInteger counter = COUNTERS.computeIfAbsent(year, y -> new AtomicInteger(loadExistingCount(y)));
        int seq = counter.incrementAndGet();
        return String.format(Locale.US, "%04d-%05d", year, seq);
    }

    private static int loadExistingCount(int year) {
        if (!Files.exists(CSV_PATH)) return 0;
        try {
            return (int) Files.lines(CSV_PATH)
                    .filter(line -> line.startsWith(year + "-"))
                    .count();
        } catch (IOException e) {
            return 0;
        }
    }

    private static void appendCsv(String id, String name, String gender, String dept, String dob, String email) {
        File file = CSV_PATH.toFile();
        boolean newFile = !file.exists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (newFile) {
                bw.write("id,name,gender,department,dob,email");
                bw.newLine();
            }
            bw.write(String.join(",", escape(id), escape(name), escape(gender), escape(dept), escape(dob), escape(email)));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escape(String s) {
        if (s.contains(",")) return '"' + s.replace("\"", "\"\"") + '"';
        return s;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
