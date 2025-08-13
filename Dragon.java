public class Dragon extends Enemy{
    public Dragon(String name, int health, int maxHealth, int attackPower) {
        super(name, health, maxHealth, attackPower);
    }

    public void attack(Enemy enemy, Character player) {
        System.out.println(" ");
        System.out.println("Turno del " + enemy.getName() + ":");
        System.out.println("El " + enemy.getName() + " ataca a "+ player.getName() + " e inflige " +  enemy.getAttackPower() + " puntos de daño");
        player.receiveDamage(enemy.getAttackPower());
    }
}