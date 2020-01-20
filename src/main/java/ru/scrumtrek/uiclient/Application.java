package ru.scrumtrek.uiclient;

public class Application {
    public static void main(String[] args) {
        Client client = new Client("http://localhost:8080");
        try {
            client.getLinesChars().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
