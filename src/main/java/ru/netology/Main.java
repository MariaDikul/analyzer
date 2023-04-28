package ru.netology;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static ArrayBlockingQueue<String> forA = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> forB = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> forC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {
        Thread filler = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                String text = generateText("abc", 100_000);
                try {
                    forA.put(text);
                    forB.put(text);
                    forC.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread threadForA = new Thread(() -> {
            int count = 0;
            int max = 0;
            String text;
            String textWithMaxA = null;
            for (int i = 0; i < 10_000; i++) {
                try {
                    text = forA.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int j = 0; j < 100_000; j++) {
                    if (text.charAt(i) == 'a') {
                        count++;
                    }
                }
                if (count > max) {
                    max = count;
                    textWithMaxA = text;
                }
            }
            System.out.println("Самое большое количество а в строке: " + max);
            System.out.println(textWithMaxA);
        });

        Thread threadForB = new Thread(() -> {
            int count = 0;
            int max = 0;
            String text;
            String textWithMaxB = null;
            for (int i = 0; i < 10_000; i++) {
                try {
                    text = forB.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int j = 0; j < 100_000; j++) {
                    if (text.charAt(i) == 'a') {
                        count++;
                    }
                }
                if (count > max) {
                    max = count;
                    textWithMaxB = text;
                }
            }
            System.out.println("Самое большое количество b в строке: " + max);
            System.out.println(textWithMaxB);
        });

        Thread threadForC = new Thread(() -> {
            int count = 0;
            int max = 0;
            String text;
            String textWithMaxC = null;
            for (int i = 0; i < 10_000; i++) {
                try {
                    text = forC.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int j = 0; j < 100_000; j++) {
                    if (text.charAt(i) == 'a') {
                        count++;
                    }
                }
                if (count > max) {
                    max = count;
                    textWithMaxC = text;
                }
            }
            System.out.println("Самое большое количество с в строке: " + max);
            System.out.println(textWithMaxC);
        });

        filler.start();
        threadForA.start();
        threadForB.start();
        threadForC.start();

        filler.join();
        threadForA.join();
        threadForB.join();
        threadForC.join();

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}