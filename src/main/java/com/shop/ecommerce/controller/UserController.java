package com.shop.ecommerce.controller;



import com.shop.ecommerce.user.entity.User;
import com.shop.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")  // Basis-URL für User
public class UserController {

    @Autowired
    private UserService userService;

    // POST /users -> neuen User anlegen (registrieren)
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.save(user);  // User speichern
        return ResponseEntity.ok("User registriert!");
    }
}
