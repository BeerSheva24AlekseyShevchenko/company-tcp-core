package telran.employees;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements Protocol {
    private Map<String, Function<String, String>> services = new HashMap<>();

    public CompanyProtocol(Object services) {
        for (Method method : services.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(AppService.class)) {
                this.services.put(method.getName(), str -> {
                    try {
                        return (String) method.invoke(services, str);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };
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
