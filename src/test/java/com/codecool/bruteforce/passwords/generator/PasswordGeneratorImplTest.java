package com.codecool.bruteforce.passwords.generator;

import com.codecool.bruteforce.passwords.model.AsciiTableRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorImplTest {

    @Test
    void testGeneratePasswordWithSpecifiedLength() {
        AsciiTableRange lowercaseChars = new AsciiTableRange(97, 122);
        PasswordGenerator passwordGenerator = new PasswordGeneratorImpl(lowercaseChars);
        int length = 8;

        String password = passwordGenerator.generate(length);

        assertEquals(length, password.length());
    }

    @Test
    void testGeneratePasswordWithMultipleCharacterSets() {
        AsciiTableRange lowercaseChars = new AsciiTableRange(97, 122);
        AsciiTableRange uppercaseChars = new AsciiTableRange(65, 90);
        PasswordGenerator passwordGenerator = new PasswordGeneratorImpl(lowercaseChars, uppercaseChars);
        int length = 8;

        String password = passwordGenerator.generate(length);

        assertTrue(password.matches(".*[a-z].*"));
        assertTrue(password.matches(".*[A-Z].*"));
        assertEquals(length, password.length());
    }

    @Test
    void testGenerateMultiplePasswords() {
        AsciiTableRange lowercaseChars = new AsciiTableRange(97, 122);
        PasswordGenerator passwordGenerator = new PasswordGeneratorImpl(lowercaseChars);
        int length = 8;

        String password1 = passwordGenerator.generate(length);
        String password2 = passwordGenerator.generate(length);

        assertNotEquals(password1, password2);
    }
}

