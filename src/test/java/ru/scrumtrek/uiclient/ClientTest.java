package ru.scrumtrek.uiclient;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "LinesProvider")
public class ClientTest {

    @Pact(provider="LinesProvider", consumer="TestConsumer")
    public RequestResponsePact interactions(PactDslWithProvider builder) {
        return builder.given("any state").
                uponReceiving("a request").
                method("GET").path("/lines").
                willRespondWith().
                status(200).
                body("[{\"lineId\":1,\"lineChars\":\"123\",\"lineCreateTime\":\"2020-01-20T05:12Z\"},{\"lineId\":2,\"lineChars\":\"321\",\"lineCreateTime\":\"2020-01-20T05:12Z\"}]").
                toPact();
    }

    @Test
    public void checkClient(MockServer server) throws IOException {
        Client client = new Client(server.getUrl());
        assertThat(client.getLinesChars()).containsOnly("123", "321");
    }
}
