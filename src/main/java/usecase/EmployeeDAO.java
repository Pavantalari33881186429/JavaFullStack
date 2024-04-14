package usecase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements EmployeeRepository{
	private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("department"),
                    rs.getInt("yearOfJoining"),
                    rs.getDouble("salary")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee findById(int id) {
        Employee employee = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                employee = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("department"),
                    rs.getInt("yearOfJoining"),
                    rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public boolean save(Employee employee) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setInt(3, employee.getAge());
            pstmt.setString(4, employee.getGender());
            pstmt.setString(5, employee.getDepartment());
            pstmt.setInt(6, employee.getYearOfJoining());
            pstmt.setDouble(7, employee.getSalary());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Employee employee) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE employees SET name=?, age=?, gender=?, department=?, yearOfJoining=?, salary=? WHERE id=?");
            pstmt.setString(1, employee.getName());
            pstmt.setInt(2, employee.getAge());
            pstmt.setString(3, employee.getGender());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setInt(5, employee.getYearOfJoining());
            pstmt.setDouble(6, employee.getSalary());
            pstmt.setInt(7, employee.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
