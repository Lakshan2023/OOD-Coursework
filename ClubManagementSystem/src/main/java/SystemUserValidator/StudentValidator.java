package SystemUserValidator;

import java.sql.SQLException;

public interface StudentValidator {
    boolean validateStudentAdmissionNumber() throws SQLException;

}
