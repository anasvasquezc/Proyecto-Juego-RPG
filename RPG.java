import java.util.ArrayList;
import java.io.File;
public class RPG {
    public static void main(String[] args) {
        BattleManager battle = new BattleManager();
        // Crear una instancia de BattleManager para llamar funciones no estáticas
        File file = new File("pausarElJuego.txt");
        if (file.exists()) {
            battle.loadGame();
        }
        else{
            int opcionesJugador=battle.optionsPlayer();
            ArrayList<Enemy> enemies= battle.addEnemy();
            Enemy enemy = battle.chooseEnemy(enemies);
            ArrayList<Item> items = battle.createItems();
            battle.createPlayer(opcionesJugador, enemy);
            battle.startBattle(enemy, items);
        }
    }
}