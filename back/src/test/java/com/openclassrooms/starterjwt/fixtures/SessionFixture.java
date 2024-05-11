package com.openclassrooms.starterjwt.fixtures;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = PRIVATE)
public class SessionFixture {
    static String date_str = new String("2024-02-06T16:30:00Z");
    static Clock customClock = Clock.fixed(
            Instant.parse(date_str),
            ZoneId.of("UTC"));
    static LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.of("UTC"));



    public static SessionDto DtoSessionFix() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Nom de la session");
        sessionDto.setDate(Date.from(Instant.parse(date_str)));
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("Description de la session");

        // Créer une liste d'IDs d'utilisateurs
        List<Long> users = new ArrayList<>();
        users.add(1L);
        users.add(2L);
        // Définir la liste d'IDs d'utilisateurs pour la sessionDto
        sessionDto.setUsers(users);

        // Définir les champs createdAt et updatedAt si nécessaire
        sessionDto.setCreatedAt(now);
        sessionDto.setUpdatedAt(now);
        return sessionDto;
    }

    public static Session SessionFix() {
        return Session.builder()
                .id(2L)
                .name("Nom de la session 2")
                .date(Date.from(Instant.parse(date_str)))
                .teacher(new Teacher())
                .description("Description de la session")
                .users(List.of(new User()))
                .createdAt(now)
                .updatedAt(now)
                .build();
    }




    public static Session sessionFixture1() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Nom de la session");
        session.setDate(Date.from(Instant.parse(date_str)));
        session.setDescription("Description of session 1");
        session.setUsers(List.of(UserFixture.userFixture1(),  UserFixture.userFixture2()));

        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        return session;
    }


    public static Session sessionFixture2() {
        Clock customClock = Clock.fixed(
                Instant.parse(date_str),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        Session session = new Session();
        session.setId(2L);
        session.setName("Session 2");
        session.setDate(Date.from(Instant.parse(date_str)));
        session.setDescription("Description of session 2");
        //session.setTeacher(TeacherFixture.teacherFixture1());
        session.setUsers(List.of(UserFixture.userFixture1(),  UserFixture.userFixture2()));

        session.setCreatedAt(now);
        session.setUpdatedAt(now);

        return session;
    }

    public static SessionDto sessionDTOFixture2() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(2L);
        sessionDto.setName("Nom de la session 2");
        sessionDto.setDate(Date.from(Instant.parse(date_str)));
        sessionDto.setTeacher_id(2L);
        sessionDto.setDescription("Description de la session 2");

        List<Long> users = new ArrayList<>();
        users.add(1L);
        users.add(2L);
        sessionDto.setUsers(users);

        sessionDto.setCreatedAt(now);
        sessionDto.setUpdatedAt(now);
        return sessionDto;

    }

    private static List<Long> getUserIds() {
        return Arrays.asList(1L, 2L, 3L);
    }
}
