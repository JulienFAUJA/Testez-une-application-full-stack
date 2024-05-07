package com.openclassrooms.starterjwt.fixtures;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SessionFixture {

    public static Session sessionFixture1() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-05-07T10:25:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        Session session = new Session();
        session.setId(1L);
        session.setName("Session 1");
        session.setDate(Date.from(Instant.parse("2024-05-07T10:25:00Z")));
        session.setDescription("Description of session 1");
        session.setTeacher(TeacherFixture.teacherFixture1());
        session.setUsers(List.of(UserFixture.userFixture1(),  UserFixture.userFixture2()));

        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        return session;
    }

    public static SessionDto sessionDTOFixture1() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-05-07T10:25:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        SessionDto session = new SessionDto();
        session.setId(1L);
        session.setName("Session 1");
        session.setDate(Date.from(Instant.parse("2024-05-07T10:25:00Z")));
        session.setDescription("Description of session 1");
        session.setTeacher(TeacherFixture.teacherFixture1());
        session.setUsers(List.of(UserFixture.userFixture1(),  UserFixture.userFixture2()));

        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        return session;
    }

    public static Session sessionFixture2() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-02-11T22:58:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        Session session = new Session();
        session.setId(2L);
        session.setName("Session 2");
        session.setDate(Date.from(Instant.parse("2024-02-11T22:58:00Z")));
        session.setDescription("Description of session 2");
        session.setTeacher(TeacherFixture.teacherFixture1());
        session.setUsers(List.of(UserFixture.userFixture1(),  UserFixture.userFixture2()));

        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        return session;
    }

    public static SessionDto sessionDTOFixture2() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-02-11T22:58:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        SessionDto session = new SessionDto();
        session.setId(2L);
        session.setName("Session 2");
        session.setDate(Date.from(Instant.parse("2024-02-11T22:58:00Z")));
        session.setDescription("Description of session 2");
        session.setTeacher(TeacherFixture.teacherFixture1());
        session.setUsers(List.of(UserFixture.userFixture1(),  UserFixture.userFixture2()));

        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        return session;
    }

    private static List<Long> getUserIds() {
        return Arrays.asList(1L, 2L, 3L);
    }
}
