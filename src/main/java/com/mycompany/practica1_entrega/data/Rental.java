/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1_entrega.data;

import java.time.LocalDate;

public class Rental {
    User user;
    Game game;
    Platform platform;
    LocalDate startDate;
    LocalDate finalDate;
    boolean returned;

    public Rental(User user, Game game, Platform platform, LocalDate startDate, LocalDate finalDate, boolean returned) {
        this.user = user;
        this.game = game;
        this.platform = platform;
        this.startDate = startDate;
        this.finalDate = finalDate;
        this.returned = returned;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
   
}
