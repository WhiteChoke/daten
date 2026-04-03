package dev.whitechoke.deskService.http;

import http.ProfileGetByFilterRequestDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(
        accept = "application/json",
        contentType = "application/json",
        url = "/api/v1/profiles"
)
public interface ProfileHttpClient {
    @GetExchange
    List<Long> getProfileIds(@RequestBody ProfileGetByFilterRequestDto request);
}
