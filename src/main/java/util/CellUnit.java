package util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Date 2019/6/12 14:53
 */
@NoArgsConstructor
public class CellUnit {

  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  private int maxLen;
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  private int maxWid;
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  private int nowGeneration;
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  //一个数据代表一个细胞,0代表死亡，1代表存活
  private int[][] multi_array;

  public CellUnit(int maxLen, int maxWid) {
    this.maxLen = maxLen;
    this.maxWid = maxWid;
    nowGeneration = 0;
    multi_array = new int[maxLen + 2][maxWid + 2];
    for (int i = 0; i <= maxLen + 1; i++) {
      for (int j = 0; j <= maxWid + 1; j++)
        multi_array[i][j] = 0;
    }
  }

  //初始化细胞
  public void randomCell() {
    for (int i = 1; i < maxLen - 1; i++)
      for (int j = 1; j < maxWid - 1; j++)
        multi_array[i][j] = Math.random() > 0.5 ? 1 : 0;
  }

  //清除所有细胞
  public void killAllCell() {
    for (int i = 0; i < maxLen; i++) {
      for (int j = 0; j < maxWid; j++) {
        multi_array[i][j] = 0;
      }
    }
  }

  //细胞分裂
  public void division() {
    int[][] res = new int[maxLen + 2][maxWid + 2];
    for (int i = 1; i <= maxLen; i++) {
      for (int j = 1; j <= maxWid; j++) {
        int num = this.countNum(multi_array, i, j);
        if (num == 2) {
          res[i][j] = multi_array[i][j];
        } else if (num == 3) {
          res[i][j] = 1;
        } else {
          res[i][j] = 0;
        }
      }
    }
    multi_array = res;
    nowGeneration++;
  }

  //计算某个细胞周围存活细胞数
  private int countNum(int[][] multi_array, int x, int y) {
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


//  public static void main(String[] args) {
//    Scanner sc = new Scanner(System.in);
//
//    String next = sc.next();
//
//    CellUnit g = new CellUnit();
//    int[][] test = new int[10][10];
//
//
//    for (int i = 0; i < test.length; i++) {
//      for (int j = 0; j < test[0].length; j++) {
//        test[i][j] = 0;
//      }
//    }
//
//    test[3][3] =1;
//    test[4][4] = 1;
//    test[5][2] = 1;
//    test[5][3] = 1;
//    test[5][4] = 1;
//
//    for (int k = 0; k < 5; k++){
//      test = g.nextStatus(test);
//      for (int i = 0; i < test.length; i++) {
//        for (int j = 0; j < test[0].length; j++) {
//          System.out.print(test[i][j]);
//        }
//        System.out.println();
//      }
//
//      System.out.println("===================");
//    }
//
//  }
}
