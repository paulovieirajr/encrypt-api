package br.dev.paulovieira.encrypt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * A DTO for the {@link br.dev.paulovieira.encrypt.model.UserModel} entity
 */
public record UserModelDto(
        @NotBlank(message = "Login is required")
        String login,

        @NotBlank(message = "Password is required")
        String password)

        implements Serializable {
}