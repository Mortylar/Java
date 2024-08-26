package game.app;

import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import game.logic.configuration.Configuration;
import game.logic.field.Field;
import game.logic.entity.EntityService;

import game.logic.entity.exception.EnemyGetPlayerException;
import game.logic.entity.exception.PlayerGetGoalException;

public class Main {

    @Parameter(names = {"-ec", "--enemiesCount="}, description = "Enemies Count")
    private static int enemiesCount_;

    @Parameter(names = {"-wc", "--wallsCount="}, description = "Obstacles Count")
    private static int wallsCount_;

    @Parameter(names = {"-s", "--size="}, description = "Field size")
    private static int size_;

    @Parameter(names = {"-p", "--profile="}, description = "User profile")
    private static String profile_;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).build().parse(args);

        Configuration conf = new Configuration();
        conf.setEnemyCount(enemiesCount_);
        conf.setWallCount(wallsCount_);
        conf.setPlayerCount(1);
        conf.setGoalCount(1);

        Field field = new Field(size_, conf.getEmptyIcon());

        EntityService service = new EntityService(field, conf);
        service.build();

        field.generateEntitiesPosition(
            service.getEntityArr(conf.getPlayerIcon()));
        field.generateEntitiesPosition(
            service.getEntityArr(conf.getWallIcon()));
        field.generateEntitiesPosition(
            service.getEntityArr(conf.getEnemyIcon()));
        field.generateEntitiesPosition(
            service.getEntityArr(conf.getGoalIcon()));
        field.print();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String ans = scanner.nextLine();
            try {
                while (!service.movePlayer(ans, 0)) {
                    ans = scanner.nextLine();
                }
            } catch (PlayerGetGoalException e) {
                System.out.print("\nWIN\n");
                System.exit(0);
            }
            field.print();
            System.out.printf("\n==========\nEnemies:\n");
            try {
                service.moveEnemies();
            } catch (EnemyGetPlayerException e) {
                System.out.print("\nYOU DIED\n");
                System.exit(0);
            }
            field.print();
            System.out.printf("\n==========\n, Player:\n");
        }

    }
}
