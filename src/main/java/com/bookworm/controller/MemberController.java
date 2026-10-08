package com.bookworm.controller;

import com.bookworm.dto.DTOs.*;
import com.bookworm.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Member & Auth", description = "Endpoints for Customer Registration, Authentication, Profile and Address Management")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/auth/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest req) {
        return new ResponseEntity<>(memberService.register(req), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    @Operation(summary = "Authenticate user")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(memberService.login(req));
    }

    @GetMapping("/members/{userId}/profile")
    @Operation(summary = "Get user profile details")
    public ResponseEntity<UserResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(memberService.getProfile(userId));
    }

    @GetMapping("/members/{userId}/addresses")
    @Operation(summary = "Get user saved delivery addresses")
    public ResponseEntity<List<AddressDto>> getAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(memberService.getUserAddresses(userId));
    }

    @PostMapping("/members/{userId}/addresses")
    @Operation(summary = "Save new delivery address")
    public ResponseEntity<AddressDto> addAddress(@PathVariable Long userId, @RequestBody AddressDto dto) {
        return new ResponseEntity<>(memberService.addAddress(userId, dto), HttpStatus.CREATED);
    }
}
