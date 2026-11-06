/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1_entrega.data;

import java.time.LocalDate;

public class User {
    int id;
    String name;
    String surname;
    String surname2;
    String dni;
    int telephone;
    LocalDate date;

    public User(int id, String name, String surname, String surname2, String dni, int telephone, LocalDate date) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.surname2 = surname2;
        this.dni = dni;
        this.telephone = telephone;
        this.date = date;
    }
}
