package dev.whitechoke.deskService.http;

import dev.whitechoke.commonLibs.Gender;
import dev.whitechoke.commonLibs.http.profileDto.FormResponseDto;
import dev.whitechoke.commonLibs.http.profileDto.UserPreferencesResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetExchange(url = "/preferences/{telegramId}")
    UserPreferencesResponseDto getUserPreferences(@PathVariable Long telegramId);

    @GetExchange(url = "/form/{profileId}")
    FormResponseDto getForm(@PathVariable Long profileId);
}
