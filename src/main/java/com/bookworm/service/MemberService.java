package com.bookworm.service;

import com.bookworm.dto.DTOs.*;
import com.bookworm.model.Address;
import com.bookworm.model.User;
import com.bookworm.repository.AddressRepository;
import com.bookworm.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public MemberService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists: " + req.getEmail());
        }
        User user = new User(req.getFullName(), req.getEmail(), req.getPassword(), req.getPhone());
        User saved = userRepository.save(user);
        return mapToUserResponse(saved);
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String mockToken = "jwt-" + UUID.randomUUID().toString();
        return new AuthResponse(mockToken, user.getId(), user.getFullName(), user.getEmail(), user.getGiftPoints());
    }

    public UserResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return mapToUserResponse(user);
    }

    public List<AddressDto> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId).stream()
                .map(this::mapToAddressDto)
                .collect(Collectors.toList());
    }

    public AddressDto addAddress(Long userId, AddressDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        Address address = new Address();
        address.setFirstName(dto.getFirstName());
        address.setLastName(dto.getLastName());
        address.setAddressLine(dto.getAddressLine());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPin(dto.getPin());
        address.setCountry(dto.getCountry());
        address.setPhone(dto.getPhone());
        address.setEmail(dto.getEmail());
        address.setIsDefault(dto.getIsDefault() != null ? dto.getIsDefault() : false);
        address.setUser(user);

        Address saved = addressRepository.save(address);
        return mapToAddressDto(saved);
    }

    public UserResponse mapToUserResponse(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setFullName(user.getFullName());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setGiftPoints(user.getGiftPoints());
        res.setRole(user.getRole());
        return res;
    }

    public AddressDto mapToAddressDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setFirstName(address.getFirstName());
        dto.setLastName(address.getLastName());
        dto.setAddressLine(address.getAddressLine());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPin(address.getPin());
        dto.setCountry(address.getCountry());
        dto.setPhone(address.getPhone());
        dto.setEmail(address.getEmail());
        dto.setIsDefault(address.getIsDefault());
        return dto;
    }
}
