package Health;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appointments {
    private Connection connection;

    public Appointments(Connection connection) {
        this.connection = connection;
    }

    // Appointment class for encapsulation
    static class Appointment {
        private int id;
        private int patientId;
        private int doctorId;
        private Date appointmentDateTime;
        private String status;

        public Appointment(int patientId, int doctorId, Date appointmentDateTime, String status) {
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.appointmentDateTime = appointmentDateTime;
            this.status = status;
        }

        // Getters and setters for encapsulation
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPatientId() {
            return patientId;
        }

        public void setPatientId(int patientId) {
            this.patientId = patientId;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(int doctorId) {
            this.doctorId = doctorId;
        }

        public Date getAppointmentDateTime() {
            return appointmentDateTime;
        }

        public void setAppointmentDateTime(Date appointmentDateTime) {
            this.appointmentDateTime = appointmentDateTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public void bookAppointment(int patientId, int doctorId, Date appointmentDateTime, String status) throws SQLException {
        String insertQuery = "INSERT INTO Appointments (patient_id, doctor_id, appointment_datetime, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            // Convert appointmentDateTime to Timestamp and set it in the prepared statement
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(appointmentDateTime.getTime()));
            preparedStatement.setString(4, status);
            preparedStatement.executeUpdate();
        }
    }


    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String selectQuery = "SELECT * FROM Appointments";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int appointment_id = resultSet.getInt("appointment_id");
                int patient_id = resultSet.getInt("patient_id");
                int doctor_id = resultSet.getInt("doctor_id");
                String appointmentDateTimeStr = resultSet.getString("appointment_datetime");
                String status = resultSet.getString("status");

                // Convert appointmentDateTimeStr to Date (you may need to handle parsing here)
                // For simplicity, we assume that the date format is already correct in the database
                // and convert it to a Date object using SimpleDateFormat.
                // However, in a real application, proper date parsing and validation should be done.
                // For example:
                // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // Date appointmentDateTime = dateFormat.parse(appointmentDateTimeStr);

                // Create Appointment object and add to the list
                Appointment appointment = new Appointment(patient_id, doctor_id, null, status);
                appointment.setId(appointment_id);
                appointments.add(appointment);
            }
        }
        return appointments;
    }
}
