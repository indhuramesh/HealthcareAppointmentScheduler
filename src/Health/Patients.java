package Health;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patients extends Person {
    private int age;
    private String phone;

    public Patients(int id, String name, String email, int age, String phone) {
        super(id, name, email);
        this.age = age;
        this.phone = phone;
    }

    public static void addPatient(Connection connection, Patients patient) throws SQLException {
        String insertQuery = "INSERT INTO Patients (name, age, email, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, patient.name);
            preparedStatement.setInt(2, patient.age);
            preparedStatement.setString(3, patient.email);
            preparedStatement.setString(4, patient.phone);
            preparedStatement.executeUpdate();
        }
    }

    public static void viewPatientsDetails(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM Patients ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patient Details:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("patient_id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Age: " + resultSet.getInt("age"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Phone: " + resultSet.getString("phone"));
                System.out.println("-------------------------------");
            }
        }
    }

    // Getters and setters for age and phone attributes (if needed)
    // ...
}
