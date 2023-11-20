package DataBaseManager;

import ClubManager.Club;
import ClubManager.Event;
import SystemUsers.ClubAdvisor;
import SystemUsers.Student;
import com.example.clubmanagementsystem.HelloApplication;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ClubAdvisorDataBaseManager {
    public static int loginClubAdvisorId;

    public static ArrayList<Integer> clubIdList = new ArrayList<>();

    public static HashMap<Integer, Club> requiredClub = new HashMap<>();

    private String userName;

    private int ClubAdvisorId;

    public ClubAdvisorDataBaseManager(String userName) throws SQLException {
        System.out.println("DataBase connector bn !!!");
        this.userName = userName;
        this.ClubAdvisorId = selectClubAdvisorId(this.userName);
        System.out.println(this.ClubAdvisorId);
        populateClubAdvisorArray();
        populateClubDetailArray(Club.clubDetailsList, this.ClubAdvisorId);
        populateEventsDetailArray();
        populateStudentDetailArray();
    }

    public ClubAdvisorDataBaseManager(){

    }

    public int selectClubAdvisorId(String userName){
        int teacherInChargeId = 0;
        String selectTeacherInChargeIdQuery = "SELECT teacherInChargeId FROM TeacherCredentials WHERE teacherUserName = ?";
        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(selectTeacherInChargeIdQuery)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacherInChargeId = resultSet.getInt("teacherInChargeId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherInChargeId;

    }

    // for club advisor login
    public void populateClubAdvisorArray() throws SQLException {
        ClubAdvisor.clubAdvisorDetailsList.clear();

        String query = "SELECT TIC.teacherInChargeId, TC.teacherUserName, TC.teacherPassword, TIC.TICFName, TIC.TICLName, TIC.teacherContactNum " +
                "FROM TeacherCredentials TC " +
                "JOIN TeacherInCharge TIC ON TC.teacherInChargeId = TIC.teacherInChargeId " +
                "WHERE TC.teacherUserName = ?";

        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
            preparedStatement.setString(1, this.userName);

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    ClubAdvisor clubAdvisor = new ClubAdvisor(
                            result.getString("teacherUserName"),
                            result.getString("teacherPassword"),
                            result.getString("TICFName"),
                            result.getString("TICLName"),
                            result.getString("teacherContactNum"),
                            result.getInt("teacherInChargeId")
                    );

                    ClubAdvisor.clubAdvisorDetailsList.add(clubAdvisor);
                    System.out.println("Elama");
                }
            }
        }
    }


    public void populateStudentDetailArray(){
        Student.studentDetailArray.clear();
        String query = "SELECT STC.studentUserName, STC.studentPassword, STD.studentAdmissionNum, STD.studentFName, " +
                "STD.studentLName, STD.studentGrade, STD.studentContactNum, STD.Gender " +
                "FROM Student STD " +
                "JOIN studentCredentials STC ON STD.studentAdmissionNum = STC.studentAdmissionNum";


        try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {

            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    Student student  = new Student(
                            result.getString("studentUserName"),
                            result.getString("studentPassword"),
                            result.getString("studentFName"),
                            result.getString("studentLName"),
                            result.getString("studentContactNum"),
                            result.getInt("studentAdmissionNum"),
                            result.getInt("studentGrade"),
                            result.getString("Gender").charAt(0)
                    );

                    Student.studentDetailArray.add(student);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



   public void populateStudentClubArray(){
        String query = "SELECT ";
   }


   public void populateClubDetailArray(ArrayList<Club> clubDetailArray, int clubAdvisorId){
       Club.clubDetailsList.clear();
       clubIdList.clear();
       String query = "SELECT C.clubId, C.clubName, C.clubDescription, C.clubLogo " +
               "FROM Club C JOIN TeacherInCharge TIC ON C.teacherInChargeId = TIC.teacherInChargeId " +
               "WHERE TIC.teacherInChargeId = ?";

       try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
           preparedStatement.setInt(1, clubAdvisorId);
           try (ResultSet result = preparedStatement.executeQuery()) {
               while (result.next()) {
                   Club club  = new Club(
                           result.getInt("clubId"),
                           result.getString("clubName"),
                           result.getString("clubDescription"),
                           result.getString("clubLogo")
                   );

                   System.out.println(result.getString("clubLogo"));

                   clubDetailArray.add(club);
                   clubIdList.add(result.getInt("clubId"));
                   requiredClub.put(result.getInt("clubId"), club);
               }
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }

   public void populateEventsDetailArray(){
       Event.eventDetails.clear();
       for(int clubId : clubIdList){
           String query = "SELECT EventId, eventName, eventDate, eventTime, eventLocation, eventType, eventDeliveryType, eventDescription " +
                   "FROM EventDetails WHERE clubId = ?";

           try (PreparedStatement preparedStatement = HelloApplication.connection.prepareStatement(query)) {
               preparedStatement.setInt(1, clubId);

               try (ResultSet result = preparedStatement.executeQuery()) {
                   while (result.next()) {
                       Date eventDate = result.getDate("eventDate");
                       Time eventTime = result.getTime("eventTime");
                       LocalDate localDate = eventDate.toLocalDate();
                       LocalTime localTime = eventTime.toLocalTime();

                       Event event = new Event(
                               result.getString("eventName"),
                               result.getString("eventLocation"),
                               result.getString("eventType"),
                               result.getString("eventDeliveryType"),
                               localDate,
                               localTime,
                               requiredClub.get(clubId),
                               result.getString("eventDescription"),
                               result.getInt("EventId")
                       );

                       Event.eventDetails.add(event);
                   }
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }

       }

   }
}

