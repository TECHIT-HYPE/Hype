package com.ll.hype.global.util;

import java.util.ArrayList;
import java.util.List;

public class ShoesSizeGenerator {
    private static final int MIN_SIZE = 220;
    private static final int MAX_SIZE = 320;
    private static final int STEP = 5;

    public static List<Integer> getSizes() {
        List<Integer> sizes = new ArrayList<>();
        for (int size = MIN_SIZE; size <= MAX_SIZE; size += STEP) {
            sizes.add(size);
        }
        return sizes;
    }
}
