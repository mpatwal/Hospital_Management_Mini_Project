package HospitalMangementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalMangementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "#@wes0mE";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, sc);
            Doctors doctor = new Doctors(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println(" 1. Add Patient");
                System.out.println(" 2. View Patients");
                System.out.println(" 3. View Doctors");
                System.out.println(" 4. Book Appointment");
                System.out.println(" 5. Exit");
                System.out.println("Enter your choice : ");
                int ch = sc.nextInt();
                switch(ch){
                    case 1:
                        // add patient
                        patient.add_patient();
                        System.out.println();
                        break;
                    case 2:
                        // view patient
                        patient.view_patient();
                        System.out.println();
                    case 3:
                        // view doctor
                        doctor.view_doctors();
                        System.out.println();
                        break;
                    case 4:
                        // book appointment
                        book_appointment(patient,doctor,connection,sc);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter Valid Choice !!!");
                        break;
                }
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void book_appointment(Patient patient,Doctors doctors,Connection connection, Scanner scanner){
        System.out.println("Enter Patient ID ");
        int patient_id = scanner.nextInt();
        System.out.println("Enter Doctor ID ");
        int doctor_id = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD)");
        String appointment_date = scanner.next();
        if(patient.Check_Patient_byID(patient_id) && doctors.Check_Doctor_byID(doctor_id))
        {
            if(doctor_available(doctor_id,appointment_date,connection)){
                String appointment_query = "insert into appointments(patient_id,doctor_id,appointment_date)values(?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointment_query);
                    preparedStatement.setInt(1,patient_id);
                    preparedStatement.setInt(2,doctor_id);
                    preparedStatement.setString(3,appointment_date);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0)
                    {
                        System.out.println("Appointment booked Successfully :)");
                    }
                    else
                    {
                        System.out.println("Appointment can't be made :(");
                    }
                }catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Doctor not available on this date");
            }
        }
        else {
            System.out.println("Either Patient or Doctor are not available ");
        }
    }
    public static boolean doctor_available(int doctor_id,String appointment_date,Connection connection)
    {
        String query = "select count(*) from appointments where doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor_id);
            preparedStatement.setString(2,appointment_date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int c=resultSet.getInt(1);
                if(c==0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
