package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counterForThree = new AtomicInteger();
    public static AtomicInteger counterForFour = new AtomicInteger();
    public static AtomicInteger counterForFive = new AtomicInteger();
    public static void main(String[] args) throws InterruptedException{
        Random random = new Random();
        String[] texts = new String[100000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread sameLettersCount = new Thread(() -> {
            for (String text : texts) {
                if (containsSameLetters(text)) {
                    counter(text.length());
                }
            }
        });
        sameLettersCount.start();

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (palindrome(text) && !containsSameLetters(text)) {
                    counter(text.length());
                }
            }
        });
        palindromeThread.start();

        Thread alphabeticOrderThread = new Thread(() -> {
            for (String text : texts) {
                if (lettersInAlphabeticOrder(text) && !containsSameLetters(text)) {
                    counter(text.length());
                }
            }
        });
        alphabeticOrderThread.start();

        sameLettersCount.join();
        palindromeThread.join();
        alphabeticOrderThread.join();

        System.out.println();
        System.out.println("Красивых слов с длиной 3: " + counterForThree + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterForFour + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterForFive + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean containsSameLetters(String text){
        for (int i = 0; i < text.length()-1; i ++){
            if (text.charAt(i) != text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean palindrome (String text){
        for (int i = 0; i < text.length(); i ++) {
            if (text.contentEquals(new StringBuilder(text).reverse())){
                return true;
            }
        }
        return false;
    }

    public static boolean lettersInAlphabeticOrder (String text){
        for (int i = 0; i < text.length()-1; i ++){
            if (text.charAt(i) < text.charAt(i+1)){
                return true;
            }
        }
        return false;
    }

    public static void counter (int length) {
        if (length == 3){
            counterForThree.getAndIncrement();
        } else if (length == 4){
            counterForFour.getAndIncrement();
        } else if (length == 5){
            counterForFive.getAndIncrement();
        }
    }
}