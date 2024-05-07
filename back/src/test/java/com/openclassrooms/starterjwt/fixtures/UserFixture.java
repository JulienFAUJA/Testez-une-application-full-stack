package com.openclassrooms.starterjwt.fixtures;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserFixture {
    public static User userFixture1() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-05-07T10:25:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        User user = new User();
        user.setId(1L);
        user.setEmail("jonas@example.com");
        user.setLastName("Perger");
        user.setFirstName("Jonas");
        user.setAdmin(false);
        user.setPassword("password");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }

    public static UserDto userDTOFixture1() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-05-07T10:25:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        UserDto user = new UserDto();
        user.setId(1L);
        user.setEmail("jonas@example.com");
        user.setLastName("Perger");
        user.setFirstName("Jonas");
        user.setAdmin(false);
        user.setPassword("password");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }

    public static User userFixture2() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-02-11T22:58:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        User user = new User();
        user.setId(2L);
        user.setEmail("jacob@example.com");
        user.setLastName("Jectif");
        user.setFirstName("Jacob");
        user.setAdmin(true);
        user.setPassword("password");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }

    public static UserDto userDTOFixture2() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-02-11T22:58:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        UserDto user = new UserDto();
        user.setId(2L);
        user.setEmail("jacob@example.com");
        user.setLastName("Jectif");
        user.setFirstName("Jacob");
        user.setAdmin(true);
        user.setPassword("password");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return user;
    }
}
