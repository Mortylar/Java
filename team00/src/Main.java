

public class Main {

    private static char player_ = '@';
    private static char enemy_ = 'X';
    private static char target_ = 'O';
    private static char obstacle_ = '#';
    private static char empty_ = ' ';

    public static void main(String[] args) {
        int size = 20;
        int enemiesCount = 20;
        int obstaclesCount = 20;

        EntityService service = new EntityService();
        service.setPlayersData(1, player_);
        service.setEnemiesData(enemiesCount, enemy_);
        service.setObstaclesData(obstaclesCount, obstacle_);
        service.setTargetsData(1, target_);
        service.build();

        Field field = new Field(size, empty_);
        field.generateEntitiesPosition(service.getEntityArr(player_));
        field.generateEntitiesPosition(service.getEntityArr(obstacle_));
        field.generateEntitiesPosition(service.getEntityArr(enemy_));
        field.generateEntitiesPosition(service.getEntityArr(target_));
        field.print();
    }
}
