package com.diser.repository;

import com.diser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    User findByUsernameAndPassword(String userName, String password);

    User findByActivationcode(String code);
}
