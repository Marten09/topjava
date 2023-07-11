package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
//https://www.baeldung.com/spring-autowire#2-autowiring-by-custom-qualifier
@FormatterType("JDBC")
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Override
    @Test
    public void createWithException() throws Exception {
    }
}