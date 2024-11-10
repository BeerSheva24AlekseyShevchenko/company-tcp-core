package telran.employees;

import java.util.function.Function;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements Protocol {
    private CompanyServices services;

    public CompanyProtocol(CompanyServices services) {
        this.services = services;
    }

    @Override
    public Response getResponse(Request request) {
        ResponseCode responseCode = null;
        String responseData = null;

        try {
            Function<String, String> service = services.get(request.requestType());
            responseData = service.apply(request.requestData());
            responseCode = ResponseCode.OK;
        } catch (Exception e) {
            responseCode = ResponseCode.WRONG_TYPE;
            responseData = "Invalid request type";
        }

        System.out.println("Request type: " + request.requestType());
        System.out.println("Request data: " + request.requestData());
        System.out.println("Response code: " + responseCode.name());
        System.out.println("Response data: " + responseData);

        return new Response(responseCode, responseData);
    }
}
