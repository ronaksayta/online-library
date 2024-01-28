package com.ronakcode.onlinelibrary.controllers;

import com.ronakcode.onlinelibrary.dto.AuthenticationRequest;
import com.ronakcode.onlinelibrary.dto.AuthenticationResponse;
import com.ronakcode.onlinelibrary.dto.MessageData;
import com.ronakcode.onlinelibrary.dto.UserDTO;
import com.ronakcode.onlinelibrary.entities.User;
import com.ronakcode.onlinelibrary.exceptions.DuplicateEmailException;
import com.ronakcode.onlinelibrary.exceptions.UserNotFoundException;
import com.ronakcode.onlinelibrary.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageData> registerUser(@RequestBody User user) throws DuplicateEmailException {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) throws UserNotFoundException {
        return new ResponseEntity<>(authService.login(authenticationRequest), HttpStatus.OK);
    }

}
