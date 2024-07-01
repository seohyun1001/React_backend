package org.zerock.backendshop.service;


import org.zerock.backendshop.model.User;

public interface AuthenticationService {
    public User signInAndReturnJWT(User signInRequest);
}
