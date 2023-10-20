package org.jadg;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
//mvnimport org.apache.jmeter.threads.JMeterContext;
//import org.apache.jmeter.threads.JMeterVariables;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MercadoLibreSearchSampler extends AbstractJavaSamplerClient {
    private static final String SEARCH_URL = "https://listado.mercadolibre.com.ar/celular-samsung";

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult result = new SampleResult();
        result.sampleStart();

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new java.net.URI(SEARCH_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            result.sampleEnd();
            result.setResponseCode(response.statusCode() + "");
            result.setResponseMessage("OK");
            result.setSuccessful(true);
            result.setResponseData(response.body(), "UTF-8");
        } catch (Exception e) {
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseCode("500");
            result.setResponseMessage("Error: " + e.getMessage());
        }

        return result;
    }
}

