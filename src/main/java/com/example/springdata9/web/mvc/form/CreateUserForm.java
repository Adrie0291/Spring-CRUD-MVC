package com.example.springdata9.web.mvc.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter

public class CreateUserForm {
    private String firstName;
    private String lastName;
    @NotBlank (message = "Pole nie może być puste")
    @Email(message = "Login powinien być poprawnym formatem email")
    private String login;
    @Size(min = 8, message = "Minimalna liczba znaków: 8")
    @NotBlank(message = "Pole nie może być puste")
    private String password;
}
