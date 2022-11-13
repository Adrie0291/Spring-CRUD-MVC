package com.example.springdata9.web.mvc.mappers;

import com.example.springdata9.model.User;
import com.example.springdata9.web.mvc.form.CreateUserForm;

public class UserMapper {
    public static User toEntity(CreateUserForm form) {
        return new User(form.getFirstName(), form.getLastName(), form.getLogin(), form.getPassword());
    }
}
