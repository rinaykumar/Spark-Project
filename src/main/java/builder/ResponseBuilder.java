package builder;

import java.util.*;

public class ResponseBuilder {
    private Date date;
    private Map<String, String> params;
    private String responseCode;
    private String response;

    public ResponseBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public ResponseBuilder setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public ResponseBuilder setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public ResponseBuilder setResponse(String response) {
        this.response = response;
        return this;
    }

    public ResponseDTO build() {
        return new ResponseDTO(this.date, this.params, this.responseCode, this.response);
    }
}