package com.codecool.bruteforce.users.generator;

import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.users.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        List<User> users = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            PasswordGenerator passwordGenerator = getRandomPasswordGenerator();
            int passwordLength = getRandomPasswordLength(maxPasswordLength);
            String password = passwordGenerator.generate(passwordLength);
            String username = "user" + (i + 1);

            User user = new User(-1,username, password);
            users.add(user);
        }

        return users;
    }

    private PasswordGenerator getRandomPasswordGenerator() {
        Random random = new Random();
        int randomIndex = random.nextInt(passwordGenerators.size());
        return passwordGenerators.get(randomIndex);
    }

    private static int getRandomPasswordLength(int maxPasswordLength) {
        Random random = new Random();
        return random.nextInt(maxPasswordLength) + 1;
    }
}
