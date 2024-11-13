package telran.employees;

import java.lang.reflect.Method;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements Protocol {
    private static final String BASE_PACKAGE = "telran.employees.services";
    private static final String TYPE_SEPARATOR = "/";
    private Company company;

    public CompanyProtocol(Company company) {
        this.company = company;
    }

    @Override
    public Response getResponse(Request request) {
        ResponseCode responseCode = null;
        String responseData = null;

        try {
            String[] type = parseType(request.requestType());
            String className = BASE_PACKAGE + "." + type[0];
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(type[1], Company.class, String.class);
            responseData = (String) method.invoke(null, company, request.requestData());
            responseCode = ResponseCode.OK;
        } catch (Exception e) {
            responseCode = ResponseCode.WRONG_TYPE;
            responseData = e.getMessage();
        }

        System.out.println("Request type: " + request.requestType());
        System.out.println("Request data: " + request.requestData());
        System.out.println("Response code: " + responseCode.name());
        System.out.println("Response data: " + responseData);

        return new Response(responseCode, responseData);
    }
    
    private String[] parseType(String type) {
        String[] res = type.split(TYPE_SEPARATOR);

        if (res.length != 2) {
            throw new IllegalArgumentException("Invalid type format. Available format: \"<entity>/<action>\"");
        }

        return res;
    }
}
