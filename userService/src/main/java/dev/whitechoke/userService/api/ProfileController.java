package dev.whitechoke.userService.api;

import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import http.ProfileGetByFilterRequestDto;
import dev.whitechoke.userService.api.dto.ProfileResponseDto;
import dev.whitechoke.userService.domain.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/profiles")
public class ProfileController {

    private final ProfileService service;

    @PostMapping
    public ResponseEntity<ProfileResponseDto> createProfile(
            @RequestBody ProfileCreateRequestDto request
    ) {
        var response = service.createProfile(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfileResponseDto> getProfileById(
            @PathVariable Long id
    ) {
        var response = service.getProfileById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<Long>> getProfileIdsByFilter(
            @RequestBody ProfileGetByFilterRequestDto request
    ) {
        var response = service.getProfilesByFilter(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("{telegramId}")
    public ResponseEntity<Long> deactivateProfile(
            @PathVariable Long telegramId
    ) {
        var id = service.deactivateProfile(telegramId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(id);
    }
}
