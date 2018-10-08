/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
