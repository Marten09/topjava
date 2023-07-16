package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(JPA)
@ContextConfiguration("classpath:spring/override-hibernate-cache.xml")
public class JpaUserServiceTest extends AbstractUserServiceTest {
}