package com.shopify.testify;

import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;

import java.util.Arrays;

class StaticPasswordTransformationMethod implements TransformationMethod {

    //CHECKSTYLE:OFF
    private static char DOT = '\u2022';
    //CHECKSTYLE:ON
    private static StaticPasswordTransformationMethod instance;

    static StaticPasswordTransformationMethod getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new StaticPasswordTransformationMethod();
        return instance;
    }

    public CharSequence getTransformation(CharSequence source, View v) {
        return new ReplacementCharSequence(source);
    }

    @Override
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {
        // not used
    }

    private static class ReplacementCharSequence implements CharSequence {

        private CharSequence sourceCharSequence;

        ReplacementCharSequence(CharSequence source) {
            sourceCharSequence = source;
        }

        @Override
        public int length() {
            return sourceCharSequence.length();
        }

        @Override
        public char charAt(int index) {
            return DOT;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            char[] chars = new char[end - start];
            Arrays.fill(chars, DOT);
            return new String(chars);
        }
    }
}
