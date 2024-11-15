package telran.employees;

import telran.net.TcpServer;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) {
        CompanyImpl company = new CompanyImpl();
        DataManager dataManager = new DataManager(company);
        dataManager.run();
        CompanyProtocol protocol = new CompanyProtocol(company);
        TcpServer tcpServer = new TcpServer(protocol, PORT);
        tcpServer.run();
    }
}