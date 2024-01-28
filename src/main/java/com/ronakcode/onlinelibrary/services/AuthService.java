package com.ronakcode.onlinelibrary.services;

import com.ronakcode.onlinelibrary.dto.AuthenticationRequest;
import com.ronakcode.onlinelibrary.dto.AuthenticationResponse;
import com.ronakcode.onlinelibrary.dto.MessageData;
import com.ronakcode.onlinelibrary.entities.User;
import com.ronakcode.onlinelibrary.exceptions.DuplicateEmailException;
import com.ronakcode.onlinelibrary.exceptions.UserNotFoundException;


public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws UserNotFoundException;
    MessageData register(User user) throws DuplicateEmailException;
    User findByEmail(String emailId) throws UserNotFoundException;
}
