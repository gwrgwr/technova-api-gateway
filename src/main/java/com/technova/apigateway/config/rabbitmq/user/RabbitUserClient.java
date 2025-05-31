package com.technova.apigateway.config.rabbitmq.user;

import com.technova.Result;
import com.technova.apigateway.config.rabbitmq.RabbitClient;
import com.technova.user.constants.RabbitUserConstants;
import com.technova.user.dto.UserConfirmEmailDTO;
import com.technova.user.dto.UserCreateDTO;
import com.technova.user.dto.UserResponseDTO;
import com.technova.user.dto.UserUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserClient {
    private final RabbitClient rabbitClient;

    public RabbitUserClient(RabbitClient rabbitClient) {
        this.rabbitClient = rabbitClient;
    }

    public Result<UserResponseDTO> sendLoginRequest(String email) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_LOGIN_REQUEST_ROUTING_KEY, email, UserResponseDTO.class);
    }

    public Result<UserResponseDTO> sendCreateUser(UserCreateDTO dto) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_SAVE_REQUEST_ROUTING_KEY, dto, UserResponseDTO.class);
    }

    public Result<UserResponseDTO> findUserById(String id) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_FIND_BY_ID_REQUEST_ROUTING_KEY, id, UserResponseDTO.class);
    }

    public Result<UserResponseDTO> sendUserUpdateRequest(UserUpdateDTO user) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_UPDATE_REQUEST_ROUTING_KEY, user, UserResponseDTO.class);
    }

    public void deleteUser(String id) {
        rabbitClient.sendMessage(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_DELETE_REQUEST_ROUTING_KEY, id);
    }

    public void softDeleteUser(String id) {
        rabbitClient.sendMessage(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_SOFT_DELETE_REQUEST_ROUTING_KEY, id);
    }

    public void updateUserApprovalStatus(UserConfirmEmailDTO dto) {
        rabbitClient.sendMessage(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_CONFIRM_EMAIL_ROUTING_KEY, dto);
    }

    public Result<UserResponseDTO> activeUser(String id) {
        return rabbitClient.sendMessageAndReceive(RabbitUserConstants.USER_EXCHANGE, RabbitUserConstants.USER_ACTIVE_REQUEST_ROUTING_KEY, id, UserResponseDTO.class);
    }
}
