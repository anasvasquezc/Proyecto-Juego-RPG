public class Mage extends Character{
    public Mage(String name, int health, int maxHealth, int mana, int maxMana, int attackPower, int damageSpecialAbility, int manaCostSpecialAbility) {
        super(name, health, maxHealth, mana, maxMana, attackPower, damageSpecialAbility, manaCostSpecialAbility);
    }

    public void specialAbility(Enemy enemy, Character player) {
        System.out.println(" ");
        enemy.health= enemy.health - getDamageSpecialAbility();
        this.mana = this.mana - getManaCostSpecialAbility();
        if (this.mana < 0) {
            this.mana = 0;
        }
        System.out.println(player.getName() +  " ha usado su habilidad Fireball contra " + enemy.getName() + " e inflige " +  getDamageSpecialAbility() + " puntos de daño");
    }
}