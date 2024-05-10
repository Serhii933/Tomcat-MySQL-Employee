package org.example.app.repository.impl;

import org.example.app.database.DBConn;
import org.example.app.entity.Employee;
import org.example.app.repository.EmployeeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public void create(Employee employee) {
        try (Connection conn = DBConn.connect();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO employees (name, position, phone) VALUES (?, ?, ?)")) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition());
            statement.setString(3, employee.getPhone());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Employee> read() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DBConn.connect();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM employees")) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");
                String phone = resultSet.getString("phone");
                employees.add(new Employee(id, name, position, phone));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee readById(Long id) {
        try (Connection conn = DBConn.connect();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String position = resultSet.getString("position");
                    String phone = resultSet.getString("phone");
                    return new Employee(id, name, position, phone);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Employee employee) {
        try (Connection conn = DBConn.connect();
             PreparedStatement statement = conn.prepareStatement(
                     "UPDATE employees SET name = ?, position = ?, phone = ? WHERE id = ?")) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPosition());
            statement.setString(3, employee.getPhone());
            statement.setLong(4, employee.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = DBConn.connect();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM employees WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
