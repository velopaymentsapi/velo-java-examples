package com.velopayments.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public interface HttpClient {
    enum ContentType {
        JSON
    }

    class HttpHeader {
        private String name;
        private String value;

        public HttpHeader(String name, String value) {
            super();
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }
        public String getValue() {
            return value;
        }


    }

    class HttpResponse {

        private int code;
        private String body;
        private Collection<HttpHeader> headers;

        HttpResponse(int code, String body) {
            super();
            this.code = code;
            this.body = body;
        }

        public int getCode() {
            return code;
        }
        public String getBody() {
            return body;
        }

        HttpResponse addHeader(HttpHeader header) {
            if(headers == null) {
                headers = new ArrayList<>();
            }

            headers.add(header);
            return this;
        }
    }

    HttpResponse get(String url, Collection<HttpHeader> headers) throws IOException;

    HttpResponse post(String url, Collection<HttpHeader> headers, String body, ContentType type) throws IOException;
}
