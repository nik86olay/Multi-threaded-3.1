package org.example;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static BlockingQueue<String> stringArray1 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> stringArray2 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> stringArray3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {

        new Thread(Main::addLine).start();

        new Thread(()->searchMaxCountLetter('a', stringArray1)).start();
        new Thread(()->searchMaxCountLetter('b', stringArray2)).start();
        new Thread(()->searchMaxCountLetter('c', stringArray3)).start();
    }

    public static void addLine() {
        for (int i = 0; i < 10_000; i++) {
            String line = generateText("abc", 100_000);
            try {
                stringArray1.put(line);
                stringArray2.put(line);
                stringArray3.put(line);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void searchMaxCountLetter(char letter, BlockingQueue<String> stringArray) {
        int maxCount = 0;
        for (int i = 0; i < 10_000; i++) {
            int count = 0;
            try {
                for (char symbol : stringArray.take().toCharArray()){
                    if(symbol == letter) count++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(count>maxCount) maxCount = count;
        }
        System.out.println("Максимальное количество символов " + letter + " содержащихся в стоке - " + maxCount + " шт.");
    }

}