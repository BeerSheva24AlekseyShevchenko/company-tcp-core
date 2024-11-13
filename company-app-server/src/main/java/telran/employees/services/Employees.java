package telran.employees.services;

import org.json.JSONArray;

import telran.employees.Company;
import telran.employees.Employee;
import telran.employees.Manager;

public class Employees {
    public static String get(Company company, String data) {
        return company.getEmployee(Long.parseLong(data)).toString();
    }

    public static String add(Company company, String data) {
        Employee empl = Employee.getEmployee(data);
        company.addEmployee(empl);
        return empl.toString();
    }

    public static String remove(Company company, String data) {
        return company.removeEmployee(Long.parseLong(data)).toString();
    }

    public static String getManagersWithMostFactor(Company company, String data) {
        JSONArray json = new JSONArray();
        for (Manager manager : company.getManagersWithMostFactor()) {
            json.put(manager.toString());
        }
        return json.toString();
    }
}
