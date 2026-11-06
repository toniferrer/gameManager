/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1_entrega.logic;

import com.mycompany.practica1_entrega.data.Game;
import com.mycompany.practica1_entrega.data.Rental;
import com.mycompany.practica1_entrega.data.User;
import java.util.ArrayList;

/**
 *
 * @author toniferrer
 */
public class RentalManager {
    private ArrayList<User> users;
    private ArrayList<Game> games;
    private ArrayList<Rental> rentals;

    public RentalManager() {
        users = new ArrayList<>();
        games = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    
}
