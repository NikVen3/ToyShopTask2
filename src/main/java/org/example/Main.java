package org.example;

import java.util.*;
import java.io.*;

class Toy {
    private final int toyId;
    private final String title;
    private int quantity;
    private double dropRate;

    public Toy(int toyId, String title, int quantity, double dropRate) {
        this.toyId = toyId;
        this.title = title;
        this.quantity = quantity;
        this.dropRate = dropRate;
    }

    public void changeDropRate(double newDropRate) {
        if (newDropRate >= 0 && newDropRate <= 100) {
            this.dropRate = newDropRate;
        } else {
            System.out.println("Процент выпадения должен быть от 0 до 100");
        }
    }

    public int getToyId() {
        return toyId;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getDropRate() {
        return dropRate;
    }

    public void decreaseQuantity() {
        if (quantity > 0)
            quantity--;
    }
}

class ToyRaffle {
    private final ArrayList<Toy> toys;
    private final Random random;
    private final LinkedList<Toy> prizeToys;

    public ToyRaffle() {
        toys = new ArrayList<>();
        prizeToys = new LinkedList<>();
        random = new Random();
    }

    public void addToy(int toyId, String title, int quantity, double dropRate) {
        toys.add(new Toy(toyId, title, quantity, dropRate));
    }

    public void organizeRaffle() {
        ArrayList<Toy> droplist = new ArrayList<>();

        for (Toy toy : toys) {
            for (int i = 0; i < (int)toy.getDropRate(); i++) {
                if (toy.getQuantity() > 0)
                    droplist.add(toy);
            }
        }

        if (droplist.size() == 0) {
            System.out.println("Игрушек для розыгрыша нет.");
        } else {
            Toy chosenToy = droplist.get(random.nextInt(droplist.size()));
            chosenToy.decreaseQuantity();
            prizeToys.add(chosenToy);
            System.out.println("Выбранная игрушка: " + chosenToy.getTitle());
        }
    }

    public void claimPrize(){
        if(!prizeToys.isEmpty()){
            Toy claimedToy = prizeToys.pollFirst();
            System.out.println("Заявленная игрушка: "+claimedToy.getTitle());
            try{
                FileWriter writer = new FileWriter("prizes.txt", true);
                BufferedWriter buffer = new BufferedWriter(writer);
                buffer.write("Призовая игрушка : "+claimedToy.getTitle()+"\n");
                buffer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Нет призовых игрушек, доступных для получения.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ToyRaffle raffle = new ToyRaffle();
        raffle.addToy(1, "Teddy Bear", 10, 60);
        raffle.addToy(2, "Robot", 5, 40);
        raffle.addToy(3, "Robot", 5, 40);
        raffle.addToy(4, "Robot", 5, 40);

        raffle.organizeRaffle();
        raffle.claimPrize();
    }
}