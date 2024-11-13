package telran.employees;

import java.util.Iterator;

import org.json.JSONArray;

import telran.net.TcpClient;

public class CompanyTcpProxy implements Company {
    TcpClient tcpClient;

    public CompanyTcpProxy(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public Iterator<Employee> iterator() {
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
    

    @Override
    public void addEmployee(Employee empl) {
        tcpClient.sendAndReceive("Employees/add", empl.toString());
    }

    @Override
    public Employee getEmployee(long id) {
        String jsonStr = tcpClient.sendAndReceive("Employees/get", Long.toString(id));
        return Employee.getEmployee(jsonStr);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        String jsonStr = tcpClient.sendAndReceive("Employees/getManagersWithMostFactor", "");
        JSONArray managers = new JSONArray(jsonStr);
        return managers.toList().stream().map(i -> Employee.getEmployee(i.toString())).toArray(Manager[]::new);
    }

    @Override
    public Employee removeEmployee(long id) {
        String jsonStr = tcpClient.sendAndReceive("Employees/remove", Long.toString(id));
        return Employee.getEmployee(jsonStr);
    }

    @Override
    public int getDepartmentBudget(String department) {
        String budget = tcpClient.sendAndReceive("Departments/getBudget", department);
        return Integer.parseInt(budget);
    }

    @Override
    public String[] getDepartments() {
        String jsonStr = tcpClient.sendAndReceive("Departments/getList", "");
        JSONArray jsonArray = new JSONArray(jsonStr);
        String[] res = jsonArray.toList().toArray(String[]::new);
        return res;
    }
}