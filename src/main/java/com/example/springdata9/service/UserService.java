package com.example.springdata9.service;

import com.example.springdata9.model.User;
import com.example.springdata9.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id" + id + " not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public User update(User user) {
        User current = findById(user.getId()); // bieżąca, obecna wersja z bazy danych
        if (user.getFirstName() == null) {
            user.setFirstName(current.getFirstName());
        }
        if (user.getLastName() == null) {
            user.setLastName(current.getLastName());
        }
        if (user.getLogin() == null) {
            user.setLogin(current.getLogin());
        }
        if (user.getPassword() == null) {
            user.setPassword(current.getPassword());
        }
        return repository.save(user);
    }

    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findAllByFirstNameAndLastName(firstName, lastName);
    }
}