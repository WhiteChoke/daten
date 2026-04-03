package dev.whitechoke.deskService.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProfileHttpClientConfig {

    @Value("${http-url.profile-service}")
    private String profileServiceUrl;

    @Bean
    public RestClient profileRestClient() {
        return RestClient.builder()
                .baseUrl(profileServiceUrl)
                .build();
    }

    @Bean
    public ProfileHttpClient profileHttpClient()  {
        return HttpServiceProxyFactory.builder()
                .exchangeAdapter(RestClientAdapter.create(profileRestClient()))
                .build()
                .createClient(ProfileHttpClient.class);
    }
}
