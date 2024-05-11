package com.openclassrooms.starterjwt.fixtures;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TeacherFixture {
    public static Teacher teacherFixture1() throws ParseException {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-05-07T10:25:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Margot");
        teacher.setLastName("DELAHAYE");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        return teacher;
    }

    public static TeacherDto teacherDTOFixture1() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-05-07T10:25:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        TeacherDto teacher = new TeacherDto();
        teacher.setId(1L);
        teacher.setFirstName("Margot");
        teacher.setLastName("DELAHAYE");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        return teacher;
    }

    public static TeacherDto teacherDtoFixture2() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-02-11T22:58:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        TeacherDto teacher = new TeacherDto();
        teacher.setId(2L);
        teacher.setFirstName("Jacob");
        teacher.setLastName("Jectif");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        return teacher;
    }

    public static Teacher teacherFixture2() {
        Clock customClock = Clock.fixed(
                Instant.parse("2024-02-11T22:58:00Z"),
                ZoneId.of("UTC"));
        LocalDateTime now = LocalDateTime.ofInstant(customClock.instant(), ZoneId.systemDefault());

        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstName("Jacob");
        teacher.setLastName("Jectif");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        return teacher;
    }
}

