package com.codecool.bruteforce.users.generator;

import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.users.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserGeneratorImplTest {
    @Mock
    private Logger loggerMock;

    @Mock
    private PasswordGenerator passwordGeneratorMock;

    @Test
    void getRandomPasswordGenerator_ShouldReturnRandomGenerator() {
        MockitoAnnotations.initMocks(this);
        PasswordGenerator expectedGenerator = Mockito.mock(PasswordGenerator.class);
        List<PasswordGenerator> passwordGenerators = List.of(expectedGenerator);
        UserGeneratorImpl userGenerator = new UserGeneratorImpl(loggerMock, passwordGenerators);

        PasswordGenerator result = userGenerator.getRandomPasswordGenerator();

        assertTrue(passwordGenerators.contains(result));
    }

    @Test
    void getRandomPasswordLength_ShouldReturnRandomLength() {
        MockitoAnnotations.openMocks(this);
        int maxPasswordLength = 10;
        UserGeneratorImpl userGenerator = new UserGeneratorImpl(loggerMock, List.of(passwordGeneratorMock));

        int result = userGenerator.getRandomPasswordLength(maxPasswordLength);

        assertTrue(result >= 1 && result <= maxPasswordLength);
    }

    @Test
    void generate_ShouldGenerateCorrectNumberOfUsers() {
        MockitoAnnotations.openMocks(this);
        int count = 5;
        int maxPasswordLength = 10;
        UserGeneratorImpl userGenerator = new UserGeneratorImpl(loggerMock, List.of(passwordGeneratorMock));

        List<User> users = userGenerator.generate(count, maxPasswordLength);

        assertEquals(count, users.size());
    }
}
