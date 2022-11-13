package com.example.springdata9.web.mvc;

import com.example.springdata9.model.User;
import com.example.springdata9.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mvc") // wszelkie requestesty będa mapowane do obsługi w tym kontrolerze, musimy wpisać do adres mvc
public class WelcomeController {
    private final UserService userService;

    @GetMapping
    public String welcomePage(ModelMap map) {
        User user = userService.findById(1L);
        map.addAttribute("user", user);
        return "index";
    }
}
