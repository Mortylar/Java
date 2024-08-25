import java.util.Scanner;

public class Main {

    // private static char player_ = '@';
    // private static char enemy_ = 'X';
    // private static char target_ = 'O';
    // private static char obstacle_ = '#';
    // private static char empty_ = ' ';

    public static void main(String[] args) {
        int size = 10;
        int enemiesCount = 3;
        int obstaclesCount = 20;

        Configuration conf = new Configuration();
        conf.setEnemyCount(enemiesCount);
        conf.setWallCount(obstaclesCount);
        conf.setPlayerCount(1);
        conf.setGoalCount(1);

        Field field = new Field(size, conf.getEmptyIcon());

        EntityService service = new EntityService(field, conf);
        // service.setPlayersData(1, player_);
        // service.setEnemiesData(enemiesCount, enemy_);
        // service.setObstaclesData(obstaclesCount, obstacle_);
        // service.setTargetsData(1, target_);
        service.build();

        // Field field = new Field(size, empty_);
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

        /*int[][] sample = field.getCopy();
        WaveManager manager = new WaveManager(sample);
        int[] obstaclesObj = new int[2];
        manager.configure(conf.getPlayerIcon(), conf.getEnemyIcon(),
                           new int[] {conf.getWallIcon(), conf.getGoalIcon()});
        sample = manager.run();
        System.out.printf("========================\n=====================\n");
        for (int i = 0; i < sample.length; ++i) {
            for (int j = 0; j < sample[0].length; ++j) {
                System.out.printf("%3d ", sample[i][j]);
            }
            System.out.print("\n");
        }*/

        // service.moveEnemies();
    }
}
