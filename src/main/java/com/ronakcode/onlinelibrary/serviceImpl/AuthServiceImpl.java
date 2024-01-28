package com.ronakcode.onlinelibrary.serviceImpl;

import com.ronakcode.onlinelibrary.config.JWTService;
import com.ronakcode.onlinelibrary.config.UserInfoUserDetails;
import com.ronakcode.onlinelibrary.dto.AuthenticationRequest;
import com.ronakcode.onlinelibrary.dto.AuthenticationResponse;
import com.ronakcode.onlinelibrary.dto.MessageData;
import com.ronakcode.onlinelibrary.dto.UserDTO;
import com.ronakcode.onlinelibrary.entities.User;
import com.ronakcode.onlinelibrary.exceptions.DuplicateEmailException;
import com.ronakcode.onlinelibrary.exceptions.UserNotFoundException;
import com.ronakcode.onlinelibrary.repositories.UserRepository;
import com.ronakcode.onlinelibrary.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Override
    public User findByEmail(String emailId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(emailId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("Unable to find user for email id: " + emailId);
        }
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        User user = findByEmail(authenticationRequest.getEmail());
        String jwtToken = jwtService.generateToken(new UserInfoUserDetails(user));
        UserDTO userDTO = new UserDTO(user.getEmail(), user.getUserId(), user.getName());
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(userDTO, jwtToken);
        return authenticationResponse;
    }

    @Override
    public MessageData register(User user) throws DuplicateEmailException {
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User registeredUser = userRepository.save(user);
            return new MessageData(registeredUser.getName() + " named user created successfully!");
        } catch (Exception exception) {
            throw new DuplicateEmailException("This email is already registered");
        }
    }
}
