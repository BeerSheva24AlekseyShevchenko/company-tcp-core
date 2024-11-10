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
        tcpClient.sendAndReceive("addEmployee", empl.toString());
    }

    @Override
    public int getDepartmentBudget(String department) {
        String budget = tcpClient.sendAndReceive("getDepartmentBudget", department);
        return Integer.parseInt(budget);
    }

    @Override
    public String[] getDepartments() {
        String jsonStr = tcpClient.sendAndReceive("getDepartments", "");
        JSONArray jsonArray = new JSONArray(jsonStr);
        String[] res = jsonArray.toList().toArray(String[]::new);
        return res;
    }

    @Override
    public Employee getEmployee(long id) {
        String jsonStr = tcpClient.sendAndReceive("getEmployee", Long.toString(id));
        return Employee.getEmployee(jsonStr);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        String jsonStr = tcpClient.sendAndReceive("getManagersWithMostFactor", "");
        JSONArray managers = new JSONArray(jsonStr);
        return managers.toList().stream().map(i -> Employee.getEmployee(i.toString())).toArray(Manager[]::new);
    }

    @Override
    public Employee removeEmployee(long id) {
        String jsonStr = tcpClient.sendAndReceive("removeEmployee", Long.toString(id));
        return Employee.getEmployee(jsonStr);
    }
}