package com.cloud.web.support;


import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;

public class MockServer {
    public static void main(String[] args) {
        WireMock.configureFor("127.0.0.1", 8080);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/book"))
                .willReturn(okJson("{\"name\": \"tom\"}")));
    }
}
