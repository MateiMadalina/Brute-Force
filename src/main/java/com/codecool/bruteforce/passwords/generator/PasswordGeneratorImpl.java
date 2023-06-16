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
        return null;
    }

    private AsciiTableRange getRandomCharacterSet() {
        return null;
    }

    private static char getRandomCharacter(AsciiTableRange characterSet) {
        return (char) 1;
    }
}
