package br.dev.paulovieira.encrypt.repository;

import br.dev.paulovieira.encrypt.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Query("SELECT u FROM UserModel u WHERE u.login = ?1")
    Optional<UserModel> findByLogin(String login);

}

