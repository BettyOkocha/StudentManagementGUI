package sms;

import java.sql.*;
import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();
    private static final String DB_URL = "jdbc:sqlite:students.db";  // <-- Your connection string

    // Constructor to initialize database
    public StudentManager() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, email TEXT)");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new student to the database
    public void addStudent(Student student) {
        students.add(student);
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String sql = "INSERT INTO students (id, name, email) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all students from the database
    public ArrayList<Student> getStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                studentList.add(new Student(id, name, email));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
