package com.lazarev.flashcards.service.mapper;

import com.lazarev.flashcards.dto.auth.AuthRequest;
import com.lazarev.flashcards.dto.auth.AuthResponse;
import com.lazarev.flashcards.entity.ApplicationUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    ApplicationUser toApplicationUser(AuthRequest authRequest);

    @Mapping(target = "token", source = "token")
    AuthResponse toAuthResponse(ApplicationUser user, String token);
}
