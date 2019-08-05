package lesson3;

import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        // Первое задание
        HashMap<String, Integer> stats; // Храним статистику в хэш мапе, из ее же ключей возьмем уникальные строки
        String[] task1 = {"one", "two", "three", "one", "two", "three", "one", "two", "three", "four"}; // вальсируем

        stats = countArrayDensity(task1);
        System.out.println("Первое задание\n==============");
        System.out.println("Сколько раз каждая строка входила в массив:");
        /*
            Ожидаем:
            four - 1
            one - 3
            two - 3
            three - 3
         */
        for(Map.Entry<String, Integer> entry: stats.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.println(key + " " + value);
        }
        System.out.println();

        System.out.println("Только уникальные элементы массива:");
        /*
            Ожидаем:
            four one two three
         */
        for(String key: stats.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
        System.out.println();
        // Конец первого задания

        //Тест второго задания
        System.out.println("Второе задание\n==============");
        PhoneBook someBook = new PhoneBook();
        someBook.add("Прачечная", "+7 (495) 629-10-10");
        someBook.add("Коннор", "555-555");
        someBook.add("Коннор", "444-111");
        someBook.add("Коннор", "333-222");
        someBook.add("Коннор", "222-333");
        someBook.add("Коннор", "111-444");

        // Геттер телефонов по фамилии используется внутри этого метода
        someBook.printPhonesBySecondName("Прачечная"); // Ожидаем 1 номер
        someBook.printPhonesBySecondName("Коннор"); // Ожидаем 5 номеров
        someBook.printPhonesBySecondName("404"); // По такой фамилии ничего нет в справочнике
    }

    // Метод для первого задания. Считает количество вхождений уникальных строк в массив
    static HashMap<String, Integer> countArrayDensity(String[] array) {
        HashMap<String, Integer>  statistics = new HashMap<>();

        for (String element: array) {
            // Если такой строки еще нет в ключах, добавляем такой ключ, сразу добавляем одно вхождение
            if(statistics.get(element) == null) {
                statistics.put(element, 1);
                continue;
            }
            // Если такая строка уже есть в ключах, получаем по ней статистику и инкрементируем ее
            int count = statistics.get(element);
            statistics.put(element, ++count);
        }

        return statistics;
    }
}
