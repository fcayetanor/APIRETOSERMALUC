package com.sermaluc.api_reto.repositories;

import com.sermaluc.api_reto.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,String> {
    Optional<User> findByEmail(String email);
}
