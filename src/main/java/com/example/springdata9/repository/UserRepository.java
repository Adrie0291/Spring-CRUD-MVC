package com.example.springdata9.repository;

import com.example.springdata9.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);


}
