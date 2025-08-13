public class Archer extends Character{
    public Archer(String name, int health, int maxHealth, int mana, int maxMana, int attackPower, int damageSpecialAbility, int manaCostSpecialAbility) {
        super(name, health, maxHealth, mana, maxMana, attackPower, damageSpecialAbility, manaCostSpecialAbility);
    }

    public void specialAbility(Enemy enemy, Character player) {
        System.out.println(" ");
        enemy.health= enemy.health - getDamageSpecialAbility();
        this.mana = this.mana - getManaCostSpecialAbility();
        if (this.mana < 0) {
            this.mana = 0;
        }
        System.out.println(player.getName() +  " ha usado su habilidad Piercing Shot contra " + enemy.getName() + " e inflige " +  getDamageSpecialAbility() + " puntos de daño");
    }
}