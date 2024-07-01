package org.zerock.backendshop.service;

import org.zerock.backendshop.model.Role;
import org.zerock.backendshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> findUserByUsername(String username);

    void changeRole(Role newRole, String username);

    List<User> userAll();

}
