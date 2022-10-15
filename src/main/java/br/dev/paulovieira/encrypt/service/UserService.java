package br.dev.paulovieira.encrypt.service;

import br.dev.paulovieira.encrypt.dto.UserModelDto;
import br.dev.paulovieira.encrypt.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<UserModel> findByLogin(String login);
    Optional<UserModel> findById(UUID id);
    List<UserModel> findAll();
    UserModel save(UserModelDto userModelDto);
    UserModel update(UUID id, UserModelDto userModelDto);
    void delete(UUID id);

}
