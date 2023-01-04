package com.final_project_leesanghun_team2.fixture;


import com.final_project_leesanghun_team2.domain.entity.User;

import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityFixture {

    public static User get(String userName, String password) {
        User user = new User();
        user.setId(1);
        user.setUserName(userName);
        user.setPassword(password);
        //user.setRole(UserRole.USER);
        //user.setRegisteredAt(Timestamp.from(Instant.now()));
        return user;
    }

    public static User getUser(String userName, String password) {
        User user = new User();
        user.setId(1);
        //user.setUsername(userName);
        user.setPassword(password);
        //user.setRole(UserRole.USER);
        //user.setRegisteredAt(Timestamp.from(Instant.now()));
        return user;
    }
}
