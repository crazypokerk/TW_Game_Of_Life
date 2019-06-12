package util;

/**
 * @Date 2019/6/12 14:53
 */
public class Game_Of_Life {
  public int countNum(int[][] multi_array, int x, int y) {
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
}
