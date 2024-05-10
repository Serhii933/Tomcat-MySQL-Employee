package org.example.app.repository;

import java.util.List;

public interface EmployeeRepository {
    void create(Employee employee);
    List<Employee> read();
    Employee readById(Long id);
    void update(Employee employee);
    void delete(Long id);
}
