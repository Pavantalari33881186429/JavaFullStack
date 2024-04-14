package usecase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class StreamAPIIMP {
	static List<Employee> employees ;
	
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
            
             employees = employeeService.getAllEmployees();
            
            
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
   
	

    // How many male and female employees are there in the organisation?
    
        Map<String, Long> genderCount = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println("Male employees: " + genderCount.getOrDefault("Male", 0L));
        System.out.println("Female employees: " + genderCount.getOrDefault("Female", 0L));
    

    // Print the name of all departments in the organisation

        Set<Object> departments = employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toSet());
        System.out.println("Departments: " + departments);
    

    // What is the average age of male and female employees?
    
        Map<String, Double> avgAgeByGender = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
      
        System.out.println("Average age of male employees: " + avgAgeByGender.getOrDefault("Male",0.0).intValue());
        System.out.println("Average age of female employees: " + avgAgeByGender.getOrDefault("Female", 0.0).intValue());
    

    // Get the details of the highest paid employee
   
        Optional<Employee> highestPaid = employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
        highestPaid.ifPresent(employee -> System.out.println("Highest paid employee: " + employee.getName() + ", Salary: " + employee.getSalary()));
    

    // Get the names of all employees who joined after 2015
    
        List<String> names = employees.stream()
                .filter(employee -> employee.getYearOfJoining() > 2015)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("Employees who joined after 2015: " + names);
    

    // Count the number of employees in each department
   
        Map<String, Long> countByDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println("Employee count by department: " + countByDepartment);
    

    // What is the average salary of each department?
  
        Map<String, Double> avgSalaryByDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println("Average salary by department: " + avgSalaryByDepartment);
    

    // Get the details of the youngest male employee in the product development department
    
        Optional<Employee> youngestMaleInPD = employees.stream()
                .filter(employee -> "Male".equals(employee.getGender()) && "Product Development".equals(employee.getDepartment()))
                .min(Comparator.comparingInt(Employee::getAge));
        youngestMaleInPD.ifPresent(employee -> System.out.println("Youngest male in Product Development: " + employee.getName() + ", Age: " + employee.getAge()));
    

    // Who has the most working experience in the organisation?
 
        Optional<Employee> mostExperienced = employees.stream()
                .min(Comparator.comparingInt(Employee::getYearOfJoining));
        mostExperienced.ifPresent(employee -> System.out.println("Most experienced employee: " + employee.getName() + ", Years of experience: " + (2024 - employee.getYearOfJoining())));
    

    // How many male and female employees are there in Sales and Marketing department?
    
        Map<String, Long> countByGenderInSAM = employees.stream()
                .filter(employee -> "Sales And Marketing".equals(employee.getDepartment()))
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println("Male employees in Sales and Marketing: " + countByGenderInSAM.getOrDefault("Male", 0L));
        System.out.println("Female employees in Sales and Marketing: " + countByGenderInSAM.getOrDefault("Female", 0L));
  

    // What is the average salary of male and female employees?
    
        Map<String, Double> avgSalaryByGender = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println("Average salary of male employees: " + avgSalaryByGender.getOrDefault("Male", 0.0).intValue());
        System.out.println("Average salary of female employees: " + avgSalaryByGender.getOrDefault("Female", 0.0).intValue());
    

    // List down the names of all employees in each department
    
        Map<String, List<String>> employeesByDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getName, Collectors.toList())));
        System.out.println("Employees by department: " + employeesByDepartment);
    

    // What is the average salary and total salary of the whole organisation?
    
        DoubleSummaryStatistics stats = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        Double Average=stats.getAverage();
        Double Salary=stats.getSum();
        System.out.println("Average salary of the organisation: " + Average.intValue());
        System.out.println("Total salary of the organisation: " + Salary.intValue());


    // Separate the employees who are younger or equal to 25 from those employees who are older than 25 years
    
        Map<Boolean, List<Employee>> separatedEmployees = employees.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getAge() <= 25));
        System.out.println("Employees younger or equal to 25: " + separatedEmployees.get(true));
        System.out.println("Employees older than 25: " + separatedEmployees.get(false));


    // Who is the oldest employee in the organisation? What is his age and which department he belongs to?
    
        Optional<Employee> oldestEmployee = employees.stream()
                .max(Comparator.comparingInt(Employee::getAge));
        System.out.println("Oldest Employee: " + oldestEmployee.get().getName()+" "+"AGE: "+oldestEmployee.get().getAge() +" "+ "Department: "+oldestEmployee.get().getDepartment());
   
	}
	

    public StreamAPIIMP(List<Employee> employees) {
        
    }
       
	

}
