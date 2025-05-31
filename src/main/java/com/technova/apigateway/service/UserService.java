package com.technova.apigateway.service;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.user.RabbitUserClient;
import com.technova.exceptions.BaseException;
import com.technova.user.dto.UserCreateDTO;
import com.technova.user.dto.UserResponseDTO;
import com.technova.user.dto.UserUpdateDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RabbitUserClient rabbitUserClient;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(RabbitUserClient rabbitUserClient, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.rabbitUserClient = rabbitUserClient;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void sendUserSaveRequest(UserCreateDTO user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Result<?> result = rabbitUserClient.sendCreateUser(user);
        if (result.isHasError()) {
            throw result.getError();
        }
    }

    public Result<UserResponseDTO> sendUserLoginRequest(String email) {
        Result<UserResponseDTO> result = rabbitUserClient.sendLoginRequest(email);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }

    public Result<UserResponseDTO> sendFindUserByIdRequest(String id) {
        Result<UserResponseDTO> result = rabbitUserClient.findUserById(id);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }

    public Result<UserResponseDTO> sendUserUpdateRequest(UserUpdateDTO user) {

        Result<UserResponseDTO> result = null;

        if (user.getPassword() != null) {
            result = rabbitUserClient.sendUserUpdateRequest(UserUpdateDTO.withPassword(user.getId(), bCryptPasswordEncoder.encode(user.getPassword())));
        }
        if (user.getEmail() != null) {
            result = rabbitUserClient.sendUserUpdateRequest(UserUpdateDTO.withEmail(user.getId(), user.getEmail()));
        }
        if (user.getAddress() != null) {
            result = rabbitUserClient.sendUserUpdateRequest(UserUpdateDTO.withAddress(user.getId(), user.getAddress()));
        }
        if (user.getPhoneNumber() != null) {
            result = rabbitUserClient.sendUserUpdateRequest(UserUpdateDTO.withPhoneNumber(user.getId(), user.getPhoneNumber()));
        }

        if (result != null) {
            if (result.isHasError()) {
                throw result.getError();
            }
            return result;
        }
        return Result.error(new BaseException("User not found"));
    }

    public void deleteUser(String id) {
        rabbitUserClient.deleteUser(id);
    }

    public void softDeleteUser(String id) {
        rabbitUserClient.softDeleteUser(id);
    }

    public Result<UserResponseDTO> activeUser(String id) {
        Result<UserResponseDTO> result = rabbitUserClient.activeUser(id);
        if (result.isHasError()) {
            throw result.getError();
        }
        return result;
    }
}
