package usecase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


public class EmployeeService {
	
	private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    public boolean addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean updateEmployee(Employee employee) {
        return employeeRepository.update(employee);
    }

    public boolean deleteEmployee(int id) {
        return employeeRepository.delete(id);
    }

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/employeedb";
        String username = "root";
        String password = "Talari@123";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            EmployeeRepository employeeRepository = new EmployeeDAO(connection);
            EmployeeService employeeService = new EmployeeService(employeeRepository);

            // Example usage
            Employee employee = new Employee(1, "John Doe", 30, "Male", "IT", 2020, 50000.0);
            //employeeService.addEmployee(employee);
            
            List<Employee> employees = employeeService.getAllEmployees();
            for (Employee emp : employees) {
                System.out.println(emp);
            }
            
            
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
