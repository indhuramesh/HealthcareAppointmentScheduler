package Health;


import java.sql.SQLException;

public interface DoctorDAO {
	//100%abstraction
	//both methods are encapsulated without exposing the internal operations
    void addDoctor(String name, String email, String specialization) throws SQLException;
    void viewDoctorsDetails() throws SQLException;
}
