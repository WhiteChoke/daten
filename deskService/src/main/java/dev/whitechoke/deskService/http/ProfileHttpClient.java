package dev.whitechoke.deskService.http;

import dev.whitechoke.commonLibs.Gender;
import dev.whitechoke.commonLibs.http.FormResponseDto;
import dev.whitechoke.commonLibs.http.ProfileResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    List<Long> getProfileIds(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("maxAge") Short maxAge,
            @RequestParam("minAge") Short minAge,
            @RequestParam("searchRadius") Double searchRadius,
            @RequestParam("gender") Gender gender
    );

    @GetExchange(url = "{telegramId}")
    ProfileResponseDto getUserPreferences(Long telegramId);

    @GetExchange(url = "form/{profileId}")
    FormResponseDto getForm(Long profileId);
}
