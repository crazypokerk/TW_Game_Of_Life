package util;

import java.util.Scanner;

/**
 * @Date 2019/6/12 14:53
 */
public class Game_Of_Life {
  //计算某一细胞周围存活数
  public static int countNum(int[][] multi_array, int x, int y) {
    if (x < 0 || y < 0 || x > multi_array.length - 1 || y > multi_array[0].length - 1) {
      return -1;
    }

    int num = 0;

    //左上角
    if (x >= 1 && y >= 1 && multi_array[x - 1][y - 1] == 1) {
      num += 1;
    }
    //上方
    if (x >= 1 && multi_array[x - 1][y] == 1) {
      num += 1;
    }
    //右上角
    if (x >= 1 && y <= multi_array[0].length - 2 && multi_array[x - 1][y + 1] == 1) {
      num += 1;
    }
    //左方
    if (y >= 1 && multi_array[x][y - 1] == 1) {
      num += 1;
    }
    //右方
    if (y <= multi_array[0].length - 2 && multi_array[x][y + 1] == 1) {
      num += 1;
    }
    //左下方
    if (x <= multi_array.length - 2 && y >= 1 && multi_array[x + 1][y - 1] == 1) {
      num += 1;
    }
    //下方
    if (x <= multi_array.length - 2 && multi_array[x + 1][y] == 1) {
      num += 1;
    }
    //右下方
    if (x <= multi_array.length - 2 && y <= multi_array[0].length - 2 && multi_array[x + 1][y + 1] == 1) {

      num += 1;
    }
    return num;
  }

  //判断某一细胞下一轮的状态
  public static int isAlive(int[][] multi_array, int x, int y){

    int num = Game_Of_Life.countNum(multi_array, x, y);

    if(num < 2 || num > 3){
      return 0;
    }else if(num == 3){
      return 1;
    }else {
      return multi_array[x][y];
    }
  }

  //循环输出变化的二维数组
  public int[][] nextStatus(int[][] multi_array) {
    int[][] res = new int[multi_array.length][multi_array[0].length];
    for(int i = 0; i < multi_array.length; i++){
      for(int j = 0; j < multi_array[0].length; j++){
        res[i][j] = isAlive(multi_array, i, j);
      }
    }
    return res;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    String next = sc.next();

    Game_Of_Life g = new Game_Of_Life();
    int[][] test = new int[10][10];


    for (int i = 0; i < test.length; i++) {
      for (int j = 0; j < test[0].length; j++) {
        test[i][j] = 0;
      }
    }

    test[3][3] =1;
    test[4][4] = 1;
    test[5][2] = 1;
    test[5][3] = 1;
    test[5][4] = 1;

    for (int k = 0; k < 5; k++){
      test = g.nextStatus(test);
      for (int i = 0; i < test.length; i++) {
        for (int j = 0; j < test[0].length; j++) {
          System.out.print(test[i][j]);
        }
        System.out.println();
      }

      System.out.println("===================");
    }

  }
}
