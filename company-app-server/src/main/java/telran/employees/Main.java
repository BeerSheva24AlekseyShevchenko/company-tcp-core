package telran.employees;

import telran.net.TcpServer;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) {
        Company company = new CompanyImpl();
        DataManager.of(company).setSaveInterval(5000).run();
        CompanyProtocol protocol = new CompanyProtocol(company);
        TcpServer tcpServer = new TcpServer(protocol, PORT);
        tcpServer.run();
    }
}