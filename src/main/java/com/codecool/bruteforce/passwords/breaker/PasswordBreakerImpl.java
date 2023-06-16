package com.codecool.bruteforce.passwords.breaker;

import java.util.ArrayList;
import java.util.List;

public class PasswordBreakerImpl implements PasswordBreaker {

    @Override
    public List<String> getCombinations(int passwordLength) {
        return null;
    }

    private static List<String> getAllPossibleCombos(List<List<String>> strings) {
        List<String> combos = new ArrayList<>(List.of(""));

        combos = strings
                .stream()
                .reduce(combos, (current, inner) -> current.stream()
                        .flatMap(c -> inner.stream().map(c::concat))
                        .toList(), (a, b) -> {
                    a.addAll(b);
                    return a;
                });

        return combos;
    }
}
