public class ManaPotion extends Item{
    public ManaPotion(String name, String description){
        super(name, description);
    }

    public void use (Character player){
        System.out.println(" ");
        if (player.mana < player.getMaxMana()) {
            int manaToRestore = player.getMaxMana()*40/100;
            int oldMana = player.mana;
            player.mana = player.mana + manaToRestore;
            if (player.mana > player.getMaxMana()) {
                player.mana = player.getMaxMana();
            }
            int manaPoints = player.mana - oldMana;
            System.out.println(player.getName() + " usó una poción de maná y restauró " + manaPoints + " puntos");
        }
        else {
            System.out.println(player.getName() + " ya tiene todo su maná completo");
        }
    }
}