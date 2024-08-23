import java.util.Random;

public class FieldGenerator {

  private char player_ = 'o';
  private char obstakle_ = '#';
  private char enemy_ = 'x';
  private char target_ = 'O';
  private char empty_ = ' ';

  private int height_ = 20;
  private int width_ = 20;

  public FieldGenerator() {}

  public FieldGenerator(int h, int w) {
      height_ = h;
      width_ = w;
  }

  public int[][] generate(int obstaklesCount, int enemiesCount) {
      if (obstaklesCount + enemiesCount + 4 > height_ * width_) {
          System.out.printf("\nSOME SIZE ERROR\n");
          System.exit(-1);
      }
      int[][] field = createField();
      generateEntity(obstaklesCount, obstakle_, field);
      generateEntity(enemiesCount, enemy_, field);
      generateEntity(1, target_,field);
      generateEntity(1, player_, field);
      // Add check for existence of a path to the target
      printField(field);



      return field;
  }

  private void generateEntity(int count, char entity, int[][] field) { 
      Random random = new Random();
      while (count > 0) {
          for (int i = 0; (i < field.length) && (count > 0); ++i) {
              for (int j = 0; (j < field[0].length) && (count > 0); ++j) {
                  int randomNumber = random.nextInt(100);
                  if ((randomNumber == 0) && isEmpty(field[i][j])) {
                      field[i][j] = entity;
                      --count;
                  }
              }
          }
      }
  }

  private void printField(int[][] field) {
      for (int i = 0; i < field.length; ++i) {
          for (int j = 0; j < field[0].length; ++j) {
              System.out.print((char) field[i][j]);
          }
          System.out.print("\n");
      }
  }

  private int[][] createField() {
      int[][] field = new int[height_][width_];
      initField(field);
      return field;
  }

  private void initField(int[][] field) {
      for (int i = 0; i < field.length; ++i) {
          for (int j = 0; j < field[0].length; ++j) {
              field[i][j] = empty_;
          }
      }
  }

  private boolean isEmpty(int place) {
      return place == empty_;
  }
}
