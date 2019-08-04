package lesson3;

import java.util.ArrayList;
import java.util.HashMap;

class PhoneBook {
    // Т.к. по заданию не требуется особенный конструктор, не описываю его явно, а просто объявляю приватную мапу
    private HashMap<String, ArrayList<String>> book = new HashMap<>();

    void add(String secondName, String phoneNumber) {
        ArrayList<String> numbers = new ArrayList<>(); // Объявляем пустой список

        // Если такая фамилия уже есть, запишем имеющиеся номера в список numbers, иначе он так и останется пустым
        if(book.get(secondName) != null) {
            numbers = book.get(secondName);
        }
        numbers.add(phoneNumber); // В любом случае добавим в конец списка новый телефон
        book.put(secondName, numbers); // Если в справочнике уже есть такая фамилия, перезапишем, иначе добавим впервые
    }

    ArrayList<String> get(String secondName) {
        // Просто вернем список телефонов по ключу-фамилии
        return book.get(secondName);
    }

    /*
        Для печати результатов сделал отдельный метод, который использует геттер номеров телефонов.
        Для целей дз печать можно было бы сделать сразу в геттере, но тогда кажется теряется смысл геттера.
        Поэтому сделал так, что геттер возвращает фамилии, а отдельный метод может напечатать их.
     */
    void printPhonesBySecondName(String secondName) {
        ArrayList<String> phones = this.get(secondName);

        if(phones == null) {
            System.out.println("Не найдено телефонов по фамилии " + secondName);
            return;
        }

        System.out.println("Телефоны по фамилии " + secondName + ":");
        for(String phone: phones) {
            System.out.println(phone);
        }
        System.out.println();
    }
}
