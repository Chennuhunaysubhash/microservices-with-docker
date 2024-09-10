package com.airline.customer_service.handler;



import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
public class DrmClientErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        System.out.println("Error Response!!!");
        switch (response.status()) {
            case 400:
                return new BadRequestException("Bad Request");
            case 404:
                return new NotFoundException("Not Found");
            case 500:
                return new InternalServerErrorException("Internal Server Error");
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}



