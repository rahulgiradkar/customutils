package com.rg.rahul.customutils;

import java.util.List;

public class ArrayUtils {
   public static boolean isNullOrEmpty(List array) {
        return array == null || array.size() == 0;
    }
}
