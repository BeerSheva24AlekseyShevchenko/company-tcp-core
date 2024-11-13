package telran.employees;

import telran.io.Persistable;
import telran.net.TcpServer;

public class Main {
    private static final int PORT = 4000;

    public static void main(String[] args) {
        Company company = new CompanyImpl();
        if (company instanceof Persistable persistable) {
            persistable.restoreFromFile("employees.data");
        }
        CompanyProtocol protocol = new CompanyProtocol(company);
        TcpServer tcpServer = new TcpServer(protocol, PORT);
        tcpServer.run();
    }
}