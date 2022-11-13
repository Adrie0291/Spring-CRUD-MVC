package com.example.springdata9.web.mvc;

import com.example.springdata9.model.User;
import com.example.springdata9.service.UserService;
import com.example.springdata9.web.mvc.form.CreateUserForm;
import com.example.springdata9.web.mvc.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller // adnotacja dla MVC
@RequestMapping("mvc/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final String MESSAGE_KEY_CREATE = "message";
    private static final String MESSAGE_KEY_DELETE = "message2";
    private static final String MESSAGE_KEY_UPDATE = "message3";

    @GetMapping
    public String create(ModelMap map) {
        map.addAttribute("user", new CreateUserForm());

        return "create-user";
    }

    @PostMapping
    public String hadnleCreate(@ModelAttribute("user") @Valid CreateUserForm form, Errors errors, RedirectAttributes redirectAttributes) { // ala ResponseBody. Obiekt jest zserializowany do obiektu JAVA
        // musimy zmapować obiekt formularza na obiekt Usera żeby móc go zapisać
        log.info("Creating user from form {}: ", form);
        if (errors.hasErrors()) { // jeśli są błędy to :
            return "create-user"; // to załaduj jeszcze raz tę stronę.
        }
        userService.save(UserMapper.toEntity(form));
        redirectAttributes.addAttribute(MESSAGE_KEY_CREATE, "User by login: " + form.getLogin() + " has been successfully added");

        return "redirect:/mvc/user/list"; //return "redired:";  coś takiego oznacza, że będąc na ścieżce mvc/user to przekieruje nas do tej samej ścieżki
    }

    @GetMapping("/list")
    public String list(
            ModelMap map,
            @ModelAttribute(MESSAGE_KEY_CREATE) String message,
            @ModelAttribute(MESSAGE_KEY_DELETE) String message2,
            @ModelAttribute(MESSAGE_KEY_UPDATE) String message3) {
        map.addAttribute("users", userService.findAll());
        if (!message.isBlank()) {
            map.addAttribute(MESSAGE_KEY_CREATE, message);
        }
        return "user-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, CreateUserForm form) {
        String loginDeleteAccount = userService.findById(id).getLogin();
        userService.delete(id);
        redirectAttributes.addAttribute(MESSAGE_KEY_DELETE, "Użytkownik o loginie: " + loginDeleteAccount + " został usunięty");

        return "redirect:/mvc/user/list";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, ModelMap map) {
        User user = userService.findById(id);
        map.addAttribute("user", user);

        return "update-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, @Valid CreateUserForm form, RedirectAttributes redirectAttributes, Errors errors) {

        if (errors.hasErrors()) { // jeśli są błędy to :
            return "update-user"; // to załaduj jeszcze raz tę stronę.
        }
        userService.save(user);

        redirectAttributes.addAttribute(MESSAGE_KEY_UPDATE, "Użytkownik o loginie: " + user.getLogin() + " został pomyślnie zmodyfikowany");
        return "redirect:/mvc/user/list";
    }
}
