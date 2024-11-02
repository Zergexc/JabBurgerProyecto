package com.jab.burger.jabburger.services;

import com.jab.burger.jabburger.models.TestUser;
import java.util.List;

public interface AdminUserServiceTest {
    List<TestUser> getAllUsers();
    TestUser getUserById(Long id);
    TestUser saveUser(TestUser user);
    TestUser updateUser(TestUser user);
    void deleteUser(Long id);
} 