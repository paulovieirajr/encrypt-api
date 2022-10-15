package br.dev.paulovieira.encrypt.service.impl;

import br.dev.paulovieira.encrypt.dto.UserModelDto;
import br.dev.paulovieira.encrypt.model.UserModel;
import br.dev.paulovieira.encrypt.repository.UserRepository;
import br.dev.paulovieira.encrypt.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<UserModel> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<UserModel> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public UserModel save(UserModelDto userModelDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userModelDto, userModel);

        // Encrypt password before saving in the database
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        return repository.save(userModel);
    }

    @Transactional
    @Override
    public UserModel update(UUID id, UserModelDto userModelDto) {
        findById(id);
        return save(userModelDto);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        findById(id);
        repository.deleteById(id);
    }
}
