package com.example.slcm.Student;

import java.util.HashMap;
import java.util.Map;

public class RomanToNumber {
    private static final Map<String, Integer> romanToDecimal = new HashMap<>();

    static {
        romanToDecimal.put("I", 1);
        romanToDecimal.put("II", 2);
        romanToDecimal.put("III", 3);
        romanToDecimal.put("IV", 4);
        romanToDecimal.put("V", 5);
        romanToDecimal.put("VI", 6);
        romanToDecimal.put("VII", 7);
        romanToDecimal.put("VII", 8);
    }

    public int romanToNumber(String roman) {
        return romanToDecimal.getOrDefault(roman, 0);
    }
}

