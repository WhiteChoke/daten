package dev.whitechoke.userService.api;

import dev.whitechoke.userService.api.dto.ProfileCreateRequestDto;
import dev.whitechoke.userService.api.dto.ProfileCreateResponseDto;
import dev.whitechoke.userService.domain.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/profiles")
public class ProfileController {

    private final ProfileService service;

    @PostMapping
    public ResponseEntity<ProfileCreateResponseDto> createProfile(
            @RequestBody ProfileCreateRequestDto request
    ) {
        var response = service.createProfile(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
