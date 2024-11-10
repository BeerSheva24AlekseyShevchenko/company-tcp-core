package telran.employees;

import org.json.JSONArray;

public class CompanyServices {
    private Company company;

    public CompanyServices(Company company) {
        this.company = company;
    }

    @AppService
    public String getEmployee(String data) {
        return company.getEmployee(Long.parseLong(data)).toString();
    }

    @AppService
    public String addEmployee(String data) {
        Employee empl = Employee.getEmployee(data);
        company.addEmployee(empl);
        return empl.toString();
    }

    @AppService
    public String removeEmployee(String data) {
        return company.removeEmployee(Long.parseLong(data)).toString();
    }

    @AppService
    public String getDepartmentBudget(String data) {
        return String.valueOf(company.getDepartmentBudget(data)) ;
    }

    @AppService
    public String getDepartments(String data) {
        JSONArray json = new JSONArray(company.getDepartments());
        return json.toString();
    }

    @AppService
    public String getManagersWithMostFactor(String data) {
        JSONArray json = new JSONArray();
        for (Manager manager : company.getManagersWithMostFactor()) {
            json.put(manager.toString());
        }
        return json.toString();
    }
}
