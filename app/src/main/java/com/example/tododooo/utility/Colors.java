package com.example.tododooo.utility;

import android.graphics.Color;

//
// Created by Azmain Adel on 8/4/19.
//
public class Colors {

    private static final int[] COLORS = new int[]{
            Color.argb(255, 28, 160, 170),
            Color.argb(255, 99, 161, 247),
            Color.argb(255, 13, 79, 139),
            Color.argb(255, 89, 113, 173),
            Color.argb(255, 200, 213, 219),
            Color.argb(255, 99, 214, 74),
            Color.argb(255, 205, 92, 92),
            Color.argb(255, 105, 5, 98)
    };

    public static int generateColor(long id) {
        return COLORS[(int) (id % COLORS.length)];
    }

}
