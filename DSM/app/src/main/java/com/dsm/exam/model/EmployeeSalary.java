package com.dsm.exam.model;

public class EmployeeSalary {
    private Employee employee;
    private double salary;
    private double totalSalary;

    public EmployeeSalary(Employee employee, double salary, double totalSalary) {
        this.employee = employee;
        this.salary = salary;
        this.totalSalary = totalSalary;
    }

    public double getSalary() {
        return salary;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public Employee getEmployee() {
        return employee;
    }
}
