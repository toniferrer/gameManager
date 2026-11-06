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
    
   
}
