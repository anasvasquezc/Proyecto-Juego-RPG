import java.util.ArrayList;
import java.util.Scanner;

public abstract class Character {
    private final String name;
    protected int health;
    private final int maxHealth;
    protected int mana;
    private final int maxMana;
    private final int attackPower;
    private final int damageSpecialAbility;
    private final int manaCostSpecialAbility;

    public Character(String name, int health, int maxHealth, int mana, int maxMana, int attackPower, int damageSpecialAbility, int manaCostSpecialAbility) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.mana = mana;
        this.maxMana = maxMana;
        this.attackPower = attackPower;
        this.damageSpecialAbility = damageSpecialAbility;
        this.manaCostSpecialAbility = manaCostSpecialAbility;
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getDamageSpecialAbility() {
        return damageSpecialAbility;
    }

    public int getManaCostSpecialAbility() {
        return manaCostSpecialAbility;
    }

    public void attack(Enemy enemy, Character player) {
        System.out.println(" ");
        System.out.println(player.name + " ataca a " + enemy.getName() + " e inflige " +  player.attackPower + " puntos de daño");
        enemy.receiveDamage(player.attackPower);
    }

    public void receiveDamage(int damage) {
        this.health = this.health - damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void useItem(ArrayList<Item> items, Character player) {
        System.out.println(" ");
        Scanner teclado = new Scanner(System.in);

        int currentHealthPotions = 0;
        int currentManaPotions = 0;
        for (Item item : items) {
            if (item instanceof HealthPotion) {
                currentHealthPotions = currentHealthPotions + 1;
            }
            else if (item instanceof ManaPotion) {
                currentManaPotions = currentManaPotions + 1;
            }
        }
        int itemChoice;
        System.out.println("Selecciona un ítem para usar:");

        if (currentHealthPotions==0 || currentManaPotions==0){
            System.out.println(" ");
            if (currentHealthPotions==0){
                System.out.println("1. Pociones de mana: " + currentManaPotions);
            } else {
                System.out.println("1. Pociones de salud: " + currentHealthPotions);
            }
            itemChoice = teclado.nextInt();
            teclado.nextLine();
            while (itemChoice != 1) {
                System.out.println(" ");
                System.out.println("Selección no válida.");
                System.out.println("Selecciona un ítem para usar:");
                itemChoice = teclado.nextInt();
            }
        }
        else{
            System.out.println("1. Pociones de salud: " + currentHealthPotions);
            System.out.println("2. Pociones de maná: " + currentManaPotions);
            itemChoice = teclado.nextInt();
            teclado.nextLine();

            while (itemChoice !=1 && itemChoice != 2) {
                System.out.println("Selección no válida.");
                System.out.println("Selecciona un ítem para usar:");
                itemChoice = teclado.nextInt();
                teclado.nextLine();
            }
        }

        switch (itemChoice) {
            case 1:
                if (currentHealthPotions == 0) {
                    for (Item item : items) {
                        if (item instanceof ManaPotion) {
                            item.use(player);
                            items.remove(item);
                            break;
                        }
                    }
                }
                else{
                    for (Item item : items) {
                        if (item instanceof HealthPotion) {
                            item.use(player);
                            items.remove(item);
                            break;
                        }
                    }
                }
                break;
            case 2:
                for (Item item : items) {
                    if (item instanceof ManaPotion) {
                        item.use(player);
                        items.remove(item);
                        break;
                    }
                }
                break;
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public abstract void specialAbility(Enemy enemy, Character player);
}