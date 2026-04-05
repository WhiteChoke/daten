package dev.whitechoke.matchService.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class DeckHttpClientConfig {

    @Value("${http-url.deck-service}")
    private String deckServiceUrl;

    @Bean
    public RestClient deckRestClient() {
        return RestClient.builder()
                .baseUrl(deckServiceUrl)
                .build();
    }

    @Bean
    public DeckHttpClient deckHttpClient() {
        return HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(deckRestClient()))
                .build()
                .createClient(DeckHttpClient.class);
    }
}
