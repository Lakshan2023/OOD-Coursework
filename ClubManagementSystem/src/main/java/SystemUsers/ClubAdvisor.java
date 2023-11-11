package SystemUsers;

import SystemUserValidator.ClubAdvisorValidator;
import com.example.clubmanagementsystem.HelloApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClubAdvisor extends User implements ClubAdvisorValidator {
    private int clubAdvisorId;
    public static ArrayList<ClubAdvisor> clubAdvisorDetailsList = new ArrayList<>();
    public static String advisorIdStatus = "";


    public ClubAdvisor(String userName,String password,
                       String firstName, String lastName,
                       String contactNumber, int clubAdvisorId){
        super(userName, password, firstName, lastName, contactNumber);
        this.clubAdvisorId = clubAdvisorId;
    }

    public ClubAdvisor(String contactNumber){
        super(contactNumber);
    }

    @Override
    public void registerToSystem() {

    }

    public ClubAdvisor(String userName,String password,
                       String firstName, String lastName){
        super(userName, password, firstName, lastName);
    }

    public ClubAdvisor(int clubAdvisorId){
        super();
        this.clubAdvisorId = clubAdvisorId;
    }

    public int getClubAdvisorId() {
        return clubAdvisorId;
    }

    public void setClubAdvisorId(int clubAdvisorId) {
        this.clubAdvisorId = clubAdvisorId;
    }

    public void passwordChecker(){

    }

    @Override
    public boolean validateClubAdvisorId() throws SQLException {
        if(String.valueOf(this.getClubAdvisorId()).isEmpty()){
            advisorIdStatus = "empty";
            System.out.println("Empty");
            return false;
        }

        if(String.valueOf(this.getClubAdvisorId()).length() != 6){
            advisorIdStatus = "length";
            System.out.println("more than 6");
            return false;
        }

        String sql = "SELECT * FROM TeacherInCharge  WHERE teacherInChargeId = ?";
        PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(this.getClubAdvisorId()));
        ResultSet results = preparedStatement.executeQuery();

        int dbClubAdvisorId = 0;
        while(results.next()){
            dbClubAdvisorId = Integer.parseInt(results.getString(1));
            System.out.println(dbClubAdvisorId);
        }

        if(this.getClubAdvisorId() == dbClubAdvisorId){
            System.out.println("Club Advisor already exists !!!");
            advisorIdStatus = "exist";
            return false;
        }else{
            return true;
        }
    }
}
