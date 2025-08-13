public abstract class Enemy {
    private final String name;
    protected int health;
    private final int maxHealth;
    private final int attackPower;

    public Enemy(String name, int health, int maxHealth, int attackPower) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public abstract void attack(Enemy enemy, Character player);

    public void receiveDamage(int damage) {
        this.health = this.health - damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}