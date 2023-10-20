
    package com.example;

    import org.apache.jmeter.config.Arguments;
    import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
    import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
    import org.apache.jmeter.samplers.SampleResult;

    import java.net.URI;
    import java.net.http.HttpClient;
    import java.net.http.HttpRequest;
    import java.net.http.HttpResponse;

    public class MercadoLibreSearchSampler implements JavaSamplerClient {

        @Override
        public SampleResult runTest(JavaSamplerContext context) {
            SampleResult result = new SampleResult();
            result.sampleStart();

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://listado.mercadolibre.com.ar/celular-samsung"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                result.sampleEnd();
                result.setSuccessful(response.statusCode() == 200);
                result.setResponseCode(Integer.toString(response.statusCode()));
                result.setResponseMessage(response.body().substring(0, 200) + "..."); // Mostrar solo los primeros 200 caracteres de la respuesta para no sobrecargar
                result.setResponseData(response.body(), "UTF-8");

            } catch (Exception e) {
                result.sampleEnd();
                result.setSuccessful(false);
                result.setResponseCode("500");
                result.setResponseMessage("Error: " + e.getMessage());
            }

            return result;
        }

        @Override
        public Arguments getDefaultParameters() {
            throw new UnsupportedOperationException("Unimplemented method 'getDefaultParameters'");
        }

        @Override
        public void setupTest(JavaSamplerContext context) {
            throw new UnsupportedOperationException("Unimplemented method 'setupTest'");
        }

        @Override
        public void teardownTest(JavaSamplerContext context) {
            throw new UnsupportedOperationException("Unimplemented method 'teardownTest'");
        }
    }
