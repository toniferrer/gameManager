package com.mycompany.practica1_entrega.data;

import java.util.HashMap;

public class Game {
    private int id;
    private String name;
    private String genre;
    private HashMap<Platform, Integer> stock;
    
    
    public Game(int id, String name, String genre){
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.stock = new HashMap<>();
    }
    

    public void setStock(Platform platform, int quantity) {
        stock.put(platform, quantity);
    }

    public int getStock(Platform platform) {
        return stock.getOrDefault(platform, 0);
    }
}
