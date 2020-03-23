package builder;

import java.util.*;

public class ResponseDTO {
    public final Date date;
    public final Map<String, String> params;
    public final String responseCode;
    public final String response;

    public ResponseDTO(Date date, Map<String, String> params, String responseCode, String response) {
        this.date = date;
        this.params = params;
        this.responseCode = responseCode;
        this.response = response;
    }
}
