public class HealthPotion extends Item{
    public HealthPotion(String name, String description){
        super(name, description);
    }

    public void use (Character player){
        System.out.println(" ");
        if (player.health < player.getMaxHealth()) {
            int healthToRestore = player.getMaxHealth()*30/100;
            int oldHealth = player.health;
            player.health = player.health + healthToRestore;
            if (player.health > player.getMaxHealth()) {
                player.health = player.getMaxHealth();
            }
            int healthPoints = player.health - oldHealth;
            System.out.println(player.getName() + " usó una poción de salud y restauró " + healthPoints + " puntos");
        }
        else {
            System.out.println(player.getName() + " ya tiene toda su salud completa");
        }
    }
}