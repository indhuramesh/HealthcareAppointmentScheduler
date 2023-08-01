package Health;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors extends Person implements DoctorDAO {
    private Connection connection;

    public Doctors(Connection connection, int id, String name, String email) {
        super(id, name, email);
        this.connection = connection;
    }

    @Override
    public void addDoctor(String name, String email, String specialization) throws SQLException {
        String insertQuery = "INSERT INTO Doctors (name, email, specialization) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, specialization);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void viewDoctorsDetails() throws SQLException {
        String selectQuery = "SELECT * FROM Doctors";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctor Details:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("doctor_id"));
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("Specialization: " + resultSet.getString("specialization"));
                System.out.println("-------------------------------");
            }
        }
    }
}
