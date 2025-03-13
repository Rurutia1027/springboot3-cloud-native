package com.cloud.bookshop.web.support;


import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;

// as long as we can specify the expected return response body
// we can hand the mock datasets to GPT
public class MockServer {
    public static void main(String[] args) {
        WireMock.configureFor("127.0.0.1", 8080);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/book"))
                .willReturn(okJson("{\"name\": \"tom\"}")));
    }
}
