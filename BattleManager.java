import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.PrintWriter;

public class BattleManager {
    private Character player;
    private final ArrayList<Enemy> enemies;
    Scanner teclado = new Scanner(System.in);

    public BattleManager() {
        this.enemies = new ArrayList<>();
        this.player = null;
    }

    public ArrayList<Enemy> addEnemy() {
        Enemy goblin = new Goblin("Goblin", 50, 50, 10);
        Enemy orc = new Orc("Orc", 100, 100, 20);
        Enemy dragon = new Dragon("Dragon", 200, 200, 40);
        this.enemies.add(goblin);
        this.enemies.add(orc);
        this.enemies.add(dragon);
        return enemies;
    }

    public Enemy chooseEnemy(ArrayList<Enemy> enemies){
        Random enemyRandom = new Random();
        int enemyIndex = enemyRandom.nextInt(enemies.size());
        Enemy enemy = enemies.get(enemyIndex);
        return enemy;

        //Fuente: https://www.geeksforgeeks.org/java-util-random-nextint-java/
    }

    public ArrayList<Item> createItems(){
        ArrayList<Item> items = new ArrayList<>();

        Random amountOfHealthPotionsRandom = new Random();
        int amountOfHealthPotions = amountOfHealthPotionsRandom.nextInt(1, 3);

        Random amountOfManaPotionsRandom = new Random();
        int amountOfManaPotions = amountOfManaPotionsRandom.nextInt(1, 3);
        // Fuente: https://www.geeksforgeeks.org/java-util-random-nextint-java/

        while (amountOfHealthPotions>0){
            items.add(new HealthPotion("Poción de salud", "Restaura hasta el 30% de la salud máxima"));
            amountOfHealthPotions=amountOfHealthPotions-1;
        }
        while (amountOfManaPotions>0){
            items.add(new ManaPotion("Poción de mana", "Restaura hasta el 40% del maná máximo"));
            amountOfManaPotions=amountOfManaPotions-1;
        }
        return items;
    }

    public int optionsPlayer() {
        System.out.println("=== Bienvenido al Simulador de Batallas RPG ===");
        System.out.println("Elige tu personaje: ");
        System.out.println("1. Guerrero");
        System.out.println("2. Mago");
        System.out.println("3. Arquero");

        int opcionesJugador = teclado.nextInt();
        teclado.nextLine();

        while (opcionesJugador < 1 || opcionesJugador > 3) {
            System.out.println("Opción no válida, vuelva a ingresar su personaje");
            opcionesJugador = teclado.nextInt();
            teclado.nextLine();
        }
        return opcionesJugador;
    }

    public void createPlayer(int opcionesJugador, Enemy enemy) {
        System.out.println(" ");
        System.out.println("¿Cuál es el nombre de tu personaje?");
        String name = teclado.nextLine();
        System.out.println(" ");

        switch (opcionesJugador) {
            case 1:
                player = new Warrior(name, 120, 120, 30, 30, 20, 40, 15);
                System.out.println("Has elegido: Guerrero");
                break;
            case 2:
                player = new Mage(name, 70, 70, 120, 120, 15, 50, 30);
                System.out.println("Has elegido: Mago");
                break;
            case 3:
                player = new Archer(name, 90, 90, 60, 60, 18, 35, 20);
                System.out.println("Has elegido: Arquero");
                break;
            default:
                break;
        }
        System.out.println("Te enfrentarás a: " + enemy.getName());
        System.out.println("¡La batalla comienza!");
    }

    public void loadGame() {
        File file = new File("pausarElJuego.txt");
        ArrayList<Item> items = new ArrayList<>();
        Enemy enemy = null;
        if (file.exists()) {
            try {
                Scanner entrada = new Scanner(file);

                //Cargar el personaje
                String linea = entrada.nextLine();
                int posicionPunto = linea.indexOf(".");

                String tipo = linea.substring(0, posicionPunto);
                String name = linea.substring(posicionPunto + 1, linea.indexOf(".", posicionPunto + 1));
                String health = linea.substring(linea.indexOf(".", posicionPunto + 1) + 1, linea.lastIndexOf("."));
                String mana = linea.substring(linea.lastIndexOf(".") + 1);

                switch (tipo) {
                    case ("Warrior"):
                        player = new Warrior(name, Integer.parseInt(health), 120, Integer.parseInt(mana), 30, 20, 40, 15);
                        break;
                    case ("Mage"):
                        player = new Mage(name, Integer.parseInt(health), 70, Integer.parseInt(mana), 120, 15, 50, 30);
                        break;
                    case ("Archer"):
                        player = new Archer(name, Integer.parseInt(health), 90, Integer.parseInt(mana), 60, 18, 35, 20);
                        break;
                }
                //Cargar el enemigo

                linea = entrada.nextLine();
                posicionPunto = linea.indexOf(".");

                tipo = linea.substring(0, posicionPunto);
                name = linea.substring(posicionPunto + 1, linea.indexOf(".", posicionPunto + 1));
                health = linea.substring(linea.indexOf(".", posicionPunto + 1) + 1);

                switch (tipo) {
                    case ("Goblin"):
                        enemy = new Goblin(name, Integer.parseInt(health), 50, 10);
                        break;
                    case ("Orc"):
                        enemy = new Orc(name, Integer.parseInt(health), 100, 20);
                        break;
                    case ("Dragon"):
                        enemy = new Dragon(name, Integer.parseInt(health), 200, 40);
                        break;

                    }
                while (entrada.hasNextLine()) {
                    linea = entrada.nextLine();
                    posicionPunto = linea.indexOf(".");

                    tipo = linea.substring(0, posicionPunto);
                    name = linea.substring(posicionPunto + 1, linea.indexOf(".", posicionPunto + 1));
                    String description = linea.substring(linea.indexOf(".", posicionPunto + 1) + 1);

                    switch (tipo) {
                        case ("HealthPotion"):
                            items.add(new HealthPotion(name, description));
                            break;
                        case ("ManaPotion"):
                            items.add(new ManaPotion(name, description));
                            break;
                    }
                }
                entrada.close();
                file.delete();
                startBattle(enemy, items);
            }
            catch (Exception e) {
                System.out.println("Error al leer el archivo de pausa: " + e.getMessage());
            }
        }
    }

    public void pauseGame(Enemy enemy, ArrayList<Item> items) {
        File file = new File("pausarElJuego.txt");
        PrintWriter output = null;
        try {
            output = new PrintWriter(file);
            output.println(player.getClass().getSimpleName() + "." + player.getName() + "." + player.health + "." + player.mana);
            output.println(enemy.getClass().getSimpleName() + "." + enemy.getName() + "." + enemy.health);
            for (Item item : items) {
                output.println(item.getClass().getSimpleName() + "." + item.getName() + "." + item.getDescription());
            }
            System.out.println("Juego pausado");
        }
        catch (Exception e) {
            System.out.println("Error al escribir en el archivo de pausa: " + e.getMessage());
        }
        finally {
            if (output != null) {
                output.close();
            }
        }
    }

    public void startBattle(Enemy enemy, ArrayList<Item> items) {
        boolean gameOver = false;
        while (!gameOver) {
            System.out.println(" ");
            System.out.println("Tu turno: ");
            System.out.println("1. Atacar");
            System.out.println("2. Ver estado");
            System.out.println("3. Pausar el juego");
            if (player.mana>=player.getManaCostSpecialAbility() && !items.isEmpty()){
                System.out.println("4. Usar habilidad adicional");
                System.out.println("5. Usar ítem");
            }
            if (player.mana<player.getManaCostSpecialAbility() && !items.isEmpty()){
                System.out.println("4. Usar ítem");
            }
            if (player.mana>=player.getManaCostSpecialAbility() && items.isEmpty()){
                System.out.println("4. Usar habilidad adicional");
            }

            int opcionesHacer = teclado.nextInt();
            teclado.nextLine();

            if (player.mana>=player.getManaCostSpecialAbility() && !items.isEmpty()){
                while (opcionesHacer < 1 || opcionesHacer > 5) {
                    System.out.println("Opción no válida, vuelva a ingresar una opción para ejecutar");
                    opcionesHacer = teclado.nextInt();
                }
            }

            else if (player.mana<player.getManaCostSpecialAbility() && !items.isEmpty() || player.mana>=player.getManaCostSpecialAbility() && items.isEmpty()){
                while (opcionesHacer < 1 || opcionesHacer > 4) {
                    System.out.println("Opción no válida, vuelva a ingresar una opción para ejecutar");
                    opcionesHacer = teclado.nextInt();
                }
            }
            else {
                while (opcionesHacer < 1 || opcionesHacer > 3) {
                    System.out.println("Opción no válida, vuelva a ingresar una opción para ejecutar");
                    opcionesHacer = teclado.nextInt();
                }
            }

            switch (opcionesHacer) {
                case 1:
                    playerTurn(enemy);
                    gameOver = isBattleOver(enemy);
                    if (!gameOver) {
                        enemiesTurn(enemy);
                        gameOver = isBattleOver(enemy);
                    }
                    break;
                case 2:
                    System.out.println(" ");
                    System.out.println("=== ESTADO DEL PERSONAJE ===");
                    System.out.println("Nombre: " + player.getName());
                    System.out.println("Clase: " + player.getClass().getSimpleName());
                    System.out.println("Salud: " + player.health + "/" + player.getMaxHealth());
                    System.out.println("Maná: " + player.mana + "/" + player.getMaxMana());
                    if (player.mana < player.getManaCostSpecialAbility()) {
                        System.out.println("Para usar una habilidad especial, debes tener al menos " + player.getManaCostSpecialAbility() + " de maná");
                    }
                    System.out.println("Estado: Vivo");
                    System.out.println("Ítems disponibles:");
                    if (items.isEmpty()) {
                        System.out.println("Ninguno");
                    }
                    if (!items.isEmpty()){
                        for (Item item : items) {
                            System.out.println(item.getName() + " - " + item.getDescription());
                        }
                    }
                    System.out.println(" ");
                    System.out.println("=== ENEMIGO ACTIVO ===");
                    System.out.println(enemy.getName() + " - Salud: " + enemy.health+"/"+ enemy.getMaxHealth() + " - Vivo");
                    break;
                case 3:
                    System.out.println(" ");
                    pauseGame(enemy, items);
                    gameOver=true;
                    break;
                case 4:
                    if (player.mana<player.getManaCostSpecialAbility() && !items.isEmpty()){
                        player.useItem(items, player);
                        enemiesTurn(enemy);
                        gameOver = isBattleOver(enemy);
                    }
                    else if (player.mana>=player.getManaCostSpecialAbility()) {
                        player.specialAbility(enemy, player);
                        gameOver = isBattleOver(enemy);
                        if (!gameOver) {
                            enemiesTurn(enemy);
                            gameOver = isBattleOver(enemy);
                        }
                    }
                    break;
                case 5:
                    if (player.mana>=player.getManaCostSpecialAbility() && !items.isEmpty()){
                        player.useItem(items, player);
                        enemiesTurn(enemy);
                        gameOver = isBattleOver(enemy);
                    }
                    break;
            }
        }
    }

    public void playerTurn(Enemy enemy) {
        player.attack(enemy, player);
    }

    public void enemiesTurn(Enemy enemy){
        enemy.attack(enemy, player);
    }

    public boolean isBattleOver(Enemy enemy) {
        boolean battleOver = false;
        if (!player.isAlive()) {
            System.out.println(" ");
            System.out.println("Has sido derrotado por el " + enemy.getName());
            System.out.println("Fin del juego");
            battleOver = true;
        }
        else if (!enemy.isAlive()) {
            System.out.println(" ");
            System.out.println("¡Has derrotado a " + enemy.getName() + "! ¡Felicidades!");
            System.out.println("Fin del juego");
            battleOver = true;
        }
        return battleOver;
    }
}