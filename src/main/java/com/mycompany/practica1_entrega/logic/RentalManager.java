/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1_entrega.logic;

import com.mycompany.practica1_entrega.data.Game;
import com.mycompany.practica1_entrega.data.Platform;
import static com.mycompany.practica1_entrega.data.Platform.PC;
import static com.mycompany.practica1_entrega.data.Platform.PS5;
import static com.mycompany.practica1_entrega.data.Platform.SWITCH_2;
import static com.mycompany.practica1_entrega.data.Platform.XBOX;
import com.mycompany.practica1_entrega.data.Rental;
import com.mycompany.practica1_entrega.data.User;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author toniferrer
 */
public class RentalManager {
    private final ArrayList<User> users;
    private final ArrayList<Game> games;
    private final ArrayList<Rental> rentals;

    public RentalManager() {
        users = new ArrayList<>();
        games = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    
    //Inicializamos los juegos
    public void addGames(){
        games.add(new Game(1, "The Legend of Zelda: Breath of the Wild", "Aventura"));
        games.add(new Game(2, "Elden Ring", "Action RPG"));
        games.add(new Game(3, "God of War Ragnarök", "Acción"));
        games.add(new Game(4, "Red Dead Redemption 2", "Aventura"));
        games.add(new Game(5, "Cyberpunk 2077", "RPG"));
        games.add(new Game(6, "Minecraft", "Sandbox"));
        games.add(new Game(7, "FIFA 24", "Deportes"));
        games.add(new Game(8, "Call of Duty: Modern Warfare III", "Shooter"));
        games.add(new Game(9, "Grand Theft Auto V", "Acción"));
        games.add(new Game(10, "Super Mario Odyssey", "Plataformas"));
        games.add(new Game(11, "Hollow Knight", "Metroidvania"));
        games.add(new Game(12, "The Witcher 3: Wild Hunt", "RPG"));
        games.add(new Game(13, "Dark Souls III", "Action RPG"));
        games.add(new Game(14, "Sekiro: Shadows Die Twice", "Acción"));
        games.add(new Game(15, "Persona 5 Royal", "JRPG"));
        games.add(new Game(16, "Animal Crossing: New Horizons", "Simulación"));
        games.add(new Game(17, "Stardew Valley", "Simulación"));
        games.add(new Game(18, "Terraria", "Sandbox"));
        games.add(new Game(19, "Overwatch 2", "Shooter"));
        games.add(new Game(20, "Fortnite", "Battle Royale"));
        games.add(new Game(21, "Valorant", "Shooter"));
        games.add(new Game(22, "League of Legends", "MOBA"));
        games.add(new Game(23, "Among Us", "Party"));
        games.add(new Game(24, "It Takes Two", "Cooperativo"));
        games.add(new Game(25, "Portal 2", "Puzzle"));
        games.add(new Game(26, "Tetris Effect", "Puzzle"));
        games.add(new Game(27, "Rocket League", "Deportes"));
        games.add(new Game(28, "Street Fighter 6", "Lucha"));
        games.add(new Game(29, "Resident Evil 4 Remake", "Terror"));
        games.add(new Game(30, "Silent Hill 2 Remake", "Terror"));
    }
    
    //Inicializamos los usuarios
    public void addUsers(){
        users.add(new User(1, "Toni", "Ferrer", "Sanchez", "45696723R", 656483947, LocalDate.now()));
        users.add(new User(2, "Marta", "Perello", "Salom", "35362734R", 634672843, LocalDate.now()));
    }
    
    //Añadimos stock de cada juego
    public void addStock(){
        games.get(0).setStock(PS5, 10);
        games.get(0).setStock(SWITCH_2, 2);
        games.get(20).setStock(PC, 4);
        games.get(20).setStock(XBOX, 2);
    }
    
    public void addRentals() {
        Game zelda = games.get(0);
        Game valorant = games.get(20);

        if (zelda.rentUnit(PS5)) {
            rentals.add(new Rental(users.get(0), zelda, PS5, LocalDate.now(), LocalDate.now().plusDays(7), false));
        }

        if (valorant.rentUnit(PC)) {
            rentals.add(new Rental(users.get(1), valorant, PC, LocalDate.now(), LocalDate.now().plusDays(5), false));
        }
    }
    
    //Obtener los alquileres
    public ArrayList<Rental> getRentals() {
        return rentals;
    }
    
    //Añadir un nuevo usuario
    public void addUser(String name, String surname, String surname2, String dni, int telephone) {
        int newId = 1;

        for (User user : users) {
            if (user.getId() >= newId) {
                newId = user.getId() + 1;
            }
        }

        users.add(new User(
            newId, name, surname, surname2,
            dni, telephone, LocalDate.now()
        ));
    }
    
    //Obtener los usuarios
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    //Saber si tienen alquileres activos o no
    public boolean hasRentals(User user) {
        for (Rental rental : rentals) {
            if (rental.getUser().getId() == user.getId()) {
                return true;
            }
        }

        return false;
    }

    //Eliminar un usuario
    public boolean deleteUser(User user) {
        if (hasRentals(user)) {
            return false;
        }

        return users.remove(user);
    }
    
    //Añadir un juego nuevo
    public void addGame(String name, String genre, java.util.Map<Platform, Integer> platformStock) {
        int newId = 1;

        for (Game game : games) {
            if (game.getId() >= newId) {
                newId = game.getId() + 1;
            }
        }

        Game game = new Game(newId, name, genre);

        for (java.util.Map.Entry<Platform, Integer> entry
                : platformStock.entrySet()) {
            game.setStock(entry.getKey(), entry.getValue());
        }

        games.add(game);
    }
    
        //Obtener el listado de juegos
    public ArrayList<Game> getGames() {
        return new ArrayList<>(games);
    }

    //Comprobar si hay algún alquiler
    public boolean hasRentals(Game game) {
        for (Rental rental : rentals) {
            if (rental.getGame().getId() == game.getId()) {
                return true;
            }
        }

        return false;
    }

    //Eliminar un juego
    public boolean deleteGame(Game game) {
        if (hasRentals(game)) {
            return false;
        }

        return games.remove(game);
    }

}
