package org.kritiniyoga.karmayoga.core.domain.repositories;

import org.kritiniyoga.karmayoga.core.domain.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UsersRepository{

    private final List<User> users;

    public InMemoryUserRepository(){
        users = new ArrayList<>();
    }
    @Override
    public List<User> fetchAllUsers() {
        return users;
    }

    @Override
    public User fetchById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public void delete(String id) {
        users.remove(fetchById(id));
    }
}
