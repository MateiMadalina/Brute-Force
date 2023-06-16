package com.codecool.bruteforce.users.generator;

import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.users.model.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserGeneratorImpl implements UserGenerator {
    private Logger logger;
    private final List<PasswordGenerator> passwordGenerators;

    private int userCount;

    public UserGeneratorImpl(Logger logger, List<PasswordGenerator> passwordGenerators) {
        this.logger = logger;
        this.passwordGenerators = passwordGenerators;
    }

    @Override
    public List<User> generate(int count, int maxPasswordLength) {
        return null;
    }

    private PasswordGenerator getRandomPasswordGenerator() {
        return null;
    }

    private static int getRandomPasswordLength(int maxPasswordLength) {
        return 0;
    }
}
