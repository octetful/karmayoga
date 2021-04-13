package org.kritiniyoga.karmayoga.rest.controllers;

import org.kritiniyoga.karmayoga.core.domain.entities.User;
import org.kritiniyoga.karmayoga.core.domain.repositories.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("karmayoga/users")
public class UserController {

    private UsersRepository usersRepository;

    @GetMapping("{id}")
    public User getUserById(@PathVariable String id){
        return  usersRepository.fetchById(id);
    }

    @GetMapping
    public List<User> getUsers(){
        return usersRepository.fetchAllUsers();
    }

    @PostMapping
    public void saveUser(@RequestBody User user){
        usersRepository.add(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id){
        usersRepository.delete(id);
    }
}
