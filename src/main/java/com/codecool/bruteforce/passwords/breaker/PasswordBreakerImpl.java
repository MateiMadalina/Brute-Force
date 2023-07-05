package com.codecool.bruteforce.passwords.breaker;

import com.codecool.bruteforce.passwords.generator.PasswordGenerator;
import com.codecool.bruteforce.passwords.model.AsciiTableRange;

import java.util.ArrayList;
import java.util.List;

public class PasswordBreakerImpl implements PasswordBreaker {

    @Override
    public List<String> getCombinations(int passwordLength) {
        List<List<String>> charLists = new ArrayList<>();

        for (int i = 0; i < passwordLength; i++) {
            List<String> charList = new ArrayList<>();
            for (int j = 0; j <= 128; j++) {
                charList.add(String.valueOf((char) j));
            }
            charLists.add(charList);
        }

        return getAllPossibleCombos(charLists);
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
