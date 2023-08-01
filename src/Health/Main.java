package Health;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/healthcare";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Kalai@123";

    public static void main(String[] args) {
        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Start console application
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Healthcare Appointment Scheduler");
                System.out.println("1. Add patient");
                System.out.println("2. View patients details");
                System.out.println("3. Add doctor");
                System.out.println("4. View doctor details");
                System.out.println("5. Book an appointment");
                System.out.println("6. View appointments");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addPatient(scanner, connection);
                        break;
                    case 2:
                        viewPatientsDetails(connection);
                        break;
                    case 3:
                        addDoctor(scanner, connection);
                        break;
                    case 4:
                        viewDoctorsDetails(connection);
                        break;
                    case 5:
                        bookAppointment(scanner, connection);
                        break;
                    case 6:
                        viewAppointments(connection);
                        break;
                    case 7:
                        System.out.println("Exiting the application.");
                        connection.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addPatient(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();

        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter patient email: ");
        String email = scanner.nextLine();

        System.out.print("Enter patient phone number: ");
        String phone = scanner.nextLine();

        Patients patient = new Patients(-1, name, email, age, phone);
        Patients.addPatient(connection, patient);
        System.out.println("Patient added successfully!");
    }

    private static void viewPatientsDetails(Connection connection) throws SQLException {
        Patients.viewPatientsDetails(connection);
    }

    private static void addDoctor(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine();

        System.out.print("Enter doctor email: ");
        String email = scanner.nextLine();

        System.out.print("Enter doctor specialization: ");
        String specialization = scanner.nextLine();

        Doctors doctor = new Doctors(connection, -1, name, email);
        doctor.addDoctor(name, email, specialization);
        System.out.println("Doctor added successfully!");
    }

    private static void viewDoctorsDetails(Connection connection) throws SQLException {
        Doctors doctor = new Doctors(connection, -1, null, null);
        doctor.viewDoctorsDetails();
    }

    private static void bookAppointment(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm:ss): ");
        String appointmentDateTimeStr = scanner.nextLine();

        System.out.print("Enter appointment status: ");
        String status = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set time zone to UTC

        Date appointmentDateTime = null;
        try {
            appointmentDateTime = dateFormat.parse(appointmentDateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Invalid date and time format. Please use yyyy-MM-dd HH:mm:ss format.");
            return;
        }

        Appointments appointments = new Appointments(connection);
        appointments.bookAppointment(patientId, doctorId, appointmentDateTime, status);
        System.out.println("Appointment booked successfully!");
    }



    private static void viewAppointments(Connection connection) throws SQLException {
        Appointments appointments = new Appointments(connection);
        System.out.println("Appointment Details:");
        for (Appointments.Appointment appointment : appointments.getAllAppointments()) {
            System.out.println("ID: " + appointment.getId());
            System.out.println("Patient ID: " + appointment.getPatientId());
            System.out.println("Doctor ID: " + appointment.getDoctorId());
            System.out.println("Appointment Date and Time: " + appointment.getAppointmentDateTime());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println("-------------------------------");
        }
    }
}
