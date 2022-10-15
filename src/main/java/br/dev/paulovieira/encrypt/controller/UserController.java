package br.dev.paulovieira.encrypt.controller;

import br.dev.paulovieira.encrypt.dto.UserModelDto;
import br.dev.paulovieira.encrypt.model.UserModel;
import br.dev.paulovieira.encrypt.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    UserServiceImpl service;
    PasswordEncoder passwordEncoder;

    public UserController(UserServiceImpl service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll(){
        log.info("Find all users");
        return ResponseEntity.status(200).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable (value = "id") UUID id){
        log.info("Find user by id: " + id);

        if (service.findById(id).isPresent()){
            return ResponseEntity.status(200).body(service.findById(id).get());
        } else {
            log.error("User not found for id: " + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found with id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody UserModelDto dto){
        log.info("Save user: " + dto);

        if (service.findByLogin(dto.login()).isPresent()){
            log.error("User already exists for login: " + dto.login());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists for login: " + dto.login());
        }

        if (!dto.password().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")){
            log.error("Password must contain at least one digit, one lower case letter, one upper case letter, " +
                    "one special character and no whitespace");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must contain at least one digit, " +
                    "one lower case letter, one upper case letter, one special character and no whitespace");
        }

        return ResponseEntity.status(201).body(service.save(dto));

    }

    @GetMapping("/login")
    public ResponseEntity<Object> isLoginAccepted(@RequestParam String login, @RequestParam String password){
        log.info("Trying to login the user: " + login);

        if (service.findByLogin(login).isPresent()){
            var user = service.findByLogin(login).get();

            if (passwordEncoder.matches(password, user.getPassword())){
                log.info("Login accepted for user: " + login);
                return ResponseEntity.status(200).body("Login accepted for user: " + login);
            } else {
                log.error("The password is incorrect for user: " + login);
                return ResponseEntity.status(401).body("The password is incorrect for user: " + login);
            }
        } else {
            return ResponseEntity.status(401).body("Login denied");
        }
    }
}
