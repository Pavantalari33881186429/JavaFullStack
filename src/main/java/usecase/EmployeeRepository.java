package usecase;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> findAll();
    Employee findById(int id);
    boolean save(Employee employee);
    boolean update(Employee employee);
    boolean delete(int id);
}
