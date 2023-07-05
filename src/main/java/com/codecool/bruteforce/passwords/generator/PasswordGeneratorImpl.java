package com.codecool.bruteforce.passwords.generator;

import com.codecool.bruteforce.logger.Logger;
import com.codecool.bruteforce.passwords.model.AsciiTableRange;

import java.util.Random;

public class PasswordGeneratorImpl implements PasswordGenerator {
    private static final Random random = new Random();
    private final AsciiTableRange[] characterSets;

    public PasswordGeneratorImpl(AsciiTableRange... characterSets) {
        this.characterSets = characterSets;
    }

    @Override
    public String generate(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            AsciiTableRange randomCharacterSet = getRandomCharacterSet();
            char randomCharacter = getRandomCharacter(randomCharacterSet);
            password.append(randomCharacter);
        }
        return password.toString();
    }


    private AsciiTableRange getRandomCharacterSet() {
        int randomIndex = random.nextInt(characterSets.length);
        return characterSets[randomIndex];
    }


    private static char getRandomCharacter(AsciiTableRange characterSet) {
        int randomAsciiValue = random.nextInt(characterSet.end() - characterSet.start() + 1) + characterSet.start();
        return (char) randomAsciiValue;
    }

}
