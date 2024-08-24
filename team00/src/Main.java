import java.util.Scanner;

public class Main {

    private static char player_ = '@';
    private static char enemy_ = 'X';
    private static char target_ = 'O';
    private static char obstacle_ = '#';
    private static char empty_ = ' ';

    public static void main(String[] args) {
        int size = 10;
        int enemiesCount = 3;
        int obstaclesCount = 20;

        Field field = new Field(size, empty_);

        EntityService service = new EntityService(field);
        service.setPlayersData(1, player_);
        service.setEnemiesData(enemiesCount, enemy_);
        service.setObstaclesData(obstaclesCount, obstacle_);
        service.setTargetsData(1, target_);
        service.build();

        // Field field = new Field(size, empty_);
        field.generateEntitiesPosition(service.getEntityArr(player_));
        field.generateEntitiesPosition(service.getEntityArr(obstacle_));
        field.generateEntitiesPosition(service.getEntityArr(enemy_));
        field.generateEntitiesPosition(service.getEntityArr(target_));
        field.print();
/*
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String ans = scanner.nextLine();
            while (!service.movePlayer(ans, 0)) {
                ans = scanner.nextLine();
            }
            field.print();
        }
        */
        int[][] sample = field.getCopy();
        WaveManager manager = new WaveManager(sample);
        manager.configure(player_, enemy_, obstacle_);
        sample = manager.run();
        System.out.printf("========================\n=====================\n");
        for (int i = 0; i < sample.length; ++i) {
            for (int j = 0; j < sample[0].length; ++j) {
                System.out.printf("%3d ", sample[i][j]);
            }
            System.out.print("\n");
        }
    }
}
