

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

      Enemy[] enemyArr = new Enemy[enemiesCount];
      for (int i = 0; i < enemiesCount; ++i) {
          enemyArr[i] = new Enemy(enemy_);
      }
      Obstacle[] obstaclesArr = new Obstacle[obstaclesCount]; 
      for (int i = 0; i < obstaclesCount; ++i) {
          obstaclesArr[i] = new Obstacle(obstacle_);
      }

      Target[] targetArr = new Target[1];
      targetArr[0] = new Target(target_);
      Player[] playerArr = new Player[1];
      playerArr[0] = new Player(player_);

      Field field = new Field(size, empty_);
      field.generateEntitiesPosition(enemyArr);
      field.generateEntitiesPosition(obstaclesArr);
      field.generateEntitiesPosition(targetArr);
      field.generateEntitiesPosition(playerArr);

      field.print();



  }
}
