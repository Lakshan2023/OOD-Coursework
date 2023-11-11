package SystemUsers;

import SystemUserValidator.StudentValidator;
import com.example.clubmanagementsystem.HelloApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User implements StudentValidator {
    private int studentAdmissionNum;
    private int studentGrade;
    private char Gender;
    public static ArrayList<Student> studentDetailArray = new ArrayList<>();

    public Student(String userName,String password,
                   String firstName, String lastName,
                   String contactNumber, int studentAdmissionNum,
                   int studentGrade, char Gender){
        super(userName, password, firstName, lastName, contactNumber);
        this.studentAdmissionNum = studentAdmissionNum;
        this.studentGrade = studentGrade;
        this.Gender = Gender;
    }

    public Student(){

    }

    @Override
    public void registerToSystem() {

    }

    public int getStudentAdmissionNum() {
        return studentAdmissionNum;
    }

    public void setStudentAdmissionNum(int studentAdmissionNum) {
        this.studentAdmissionNum = studentAdmissionNum;
    }

    public int getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(int studentGrade) {
        this.studentGrade = studentGrade;
    }

    public char getGender() {
        return Gender;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    @Override
    public boolean validateStudentAdmissionNumber() throws SQLException {
        if(String.valueOf(this.getStudentAdmissionNum()).isEmpty()){
            return false;
        }

        if(String.valueOf(this.getStudentAdmissionNum()).length() > 4){
            return false;
        }
        String dbClubAdvisorId = null;
        String sql = "SELECT * FROM TeacherInCharge  WHERE teacherInChargeId = ?";
        PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(this.getStudentAdmissionNum()));
        ResultSet results = preparedStatement.executeQuery();

        while(results.next()){
            dbClubAdvisorId = results.getString(1);
            System.out.println(dbClubAdvisorId);
        }

        assert dbClubAdvisorId != null;
        if(this.getStudentAdmissionNum() == Integer.parseInt(dbClubAdvisorId)){
            return false;
        }else{
            return true;
        }
    }






}
