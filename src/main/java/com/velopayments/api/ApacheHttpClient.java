package com.velopayments.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.ContentResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApacheHttpClient implements HttpClient {
    private static Logger LOG = Logger.getLogger(ApacheHttpClient.class.getCanonicalName());

    private HttpResponse execute(Request request, Collection<HttpHeader> headers) throws ClientProtocolException, IOException {

        request.connectTimeout(60000);

        if(LOG.isLoggable(Level.FINEST)) {
            headers.forEach(header -> LOG.log(Level.FINEST, "{}: {}", new String[] {header.getName(), header.getValue()}));
        }

        // Add our headers
        headers.forEach(header -> request.addHeader(header.getName(), header.getValue()));

        Response response = request.execute();

        org.apache.http.HttpResponse underlyingResponse = response.returnResponse();

        int code = underlyingResponse.getStatusLine().getStatusCode();

        System.out.println("Status Code:" + code);

        ContentResponseHandler handler = new ContentResponseHandler();
        Content content = handler.handleResponse(underlyingResponse);

        String body = content.asString();

        HttpResponse httpResponse = new HttpResponse(code, body);

        Arrays.stream(underlyingResponse.getAllHeaders()).map(header -> new HttpHeader(header.getName(), header.getValue())).forEach(header -> httpResponse.addHeader(header));

        return httpResponse;
    }


    public HttpResponse get(String url, Collection<HttpHeader> headers) throws IOException {
        return execute(Request.Get(url), headers);
    }

    public HttpResponse post(String url, Collection<HttpHeader> headers, String body, ContentType type) throws IOException {
        Request post = Request.Post(url);

        if(body != null) {
            post.bodyString(body, org.apache.http.entity.ContentType.APPLICATION_JSON);
        }

        return execute(post, headers);
    }
}
