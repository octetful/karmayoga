package org.kritiniyoga.karmayoga.core.domain.repositories;

import org.kritiniyoga.karmayoga.core.domain.entities.User;

import java.util.List;

public interface UsersRepository {
    List<User> fetchAllUsers();

    User fetchById(String id);

    void add(User user);

    void delete(String id);
}
