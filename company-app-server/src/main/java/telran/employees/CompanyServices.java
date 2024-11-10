package telran.employees;

import java.util.function.Function;

import org.json.JSONArray;

public class CompanyServices {
    private static Company company;

    public CompanyServices(Company company) {
        CompanyServices.company = company;
    }

    public Function<String, String> get(String type) {
        return switch(type) {
            case "getEmployee" -> CompanyServices::getEmployee;
            case "addEmployee" -> CompanyServices::addEmployee;
            case "removeEmployee" -> CompanyServices::removeEmployee;
            case "getDepartmentBudget" -> CompanyServices::getDepartmentBudget;
            case "getDepartments" -> CompanyServices::getDepartments;
            case "getManagersWithMostFactor" -> CompanyServices::getManagersWithMostFactor;
            default -> null;
        };
    }

    public static String getEmployee(String data) {
        return company.getEmployee(Long.parseLong(data)).toString();
    }

    public static String addEmployee(String data) {
        Employee empl = Employee.getEmployee(data);
        company.addEmployee(empl);
        return empl.toString();
    }

    public static String removeEmployee(String data) {
        return company.removeEmployee(Long.parseLong(data)).toString();
    }

    public static String getDepartmentBudget(String data) {
        return String.valueOf(company.getDepartmentBudget(data)) ;
    }

    public static String getDepartments(String data) {
        JSONArray json = new JSONArray(company.getDepartments());
        return json.toString();
    }

    public static String getManagersWithMostFactor(String data) {
        JSONArray json = new JSONArray();
        for (Manager manager : company.getManagersWithMostFactor()) {
            json.put(manager.toString());
        }
        return json.toString();
    }
}
