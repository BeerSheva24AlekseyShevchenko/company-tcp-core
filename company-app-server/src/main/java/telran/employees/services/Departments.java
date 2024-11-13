package telran.employees.services;

import org.json.JSONArray;

import telran.employees.Company;

public class Departments {
    public static String getBudget(Company company, String data) {
        return String.valueOf(company.getDepartmentBudget(data)) ;
    }

    public static String getList(Company company, String data) {
        JSONArray json = new JSONArray(company.getDepartments());
        return json.toString();
    }
}
