//import org.junit.Assert;
//import util.Game_Of_Life;
//
///**
// * @Date 2019/6/12 14:39
// */
//public class Test {
//  @org.junit.Test
//  public void test() {
//
//    int[][] test = new int[10][10];
//
//    for (int i = 0; i < test.length; i++) {
//      for (int j = 0; j < test[0].length; j++) {
//        test[i][j] = 0;
//      }
//    }
//
//    for (int i = 0; i < test.length; i++) {
//      for (int j = 0; j < test[0].length; j=j+i+1) {
//          test[i][j] = 1;
//      }
//    }
//
//    for (int i = 0; i < test.length; i++) {
//      for (int j = 0; j < test[0].length; j++) {
//        System.out.print(test[i][j]);
//      }
//      System.out.println();
//    }
//
//    Game_Of_Life gof = new Game_Of_Life();
//
//    //测试左上角
//    int res = gof.countNum(test, 0, 0);
//    //测试右上角
//    int res1 = gof.countNum(test, 0, 9);
//    //测试左下角
//    int res2 = gof.countNum(test, 9, 0);
//    //测试右下角
//    int res3 = gof.countNum(test, 9, 9);
//    //测试中间元素
//    int res4 = gof.countNum(test, 2, 3);
//    //测试边界元素
//    int res5 = gof.countNum(test, 2, 0);
//
//    Assert.assertEquals(2,res);
//    Assert.assertEquals(2, res1);
//    Assert.assertEquals(1, res2);
//    Assert.assertEquals(1,res3);
//    Assert.assertEquals(3,res4);
//    Assert.assertEquals(2,res5);
//
//  }
//
//  @org.junit.Test
//  public void test2() {
//    //测试isAlive方法 和 nextStatus方法
//
//    //初始化数组
//    int[][] test = new int[10][10];
//
//    for (int i = 0; i < test.length; i++) {
//      for (int j = 0; j < test[0].length; j++) {
//        test[i][j] = 0;
//      }
//    }
//
//    test[0][0] =1;
//    test[0][1] = 1;
//    test[1][0] = 1;
//    test[1][2] = 1;
//    test[2][5] = 1;
//    test[2][6] = 1;
//    test[3][3] = 1;
//    test[3][4] = 1;
//    test[3][5] = 1;
//    test[4][5] = 1;
//    test[3][2] = 1;
//
//
//    int res = Game_Of_Life.isAlive(test, 3, 4);
//    int res1 = Game_Of_Life.isAlive(test, 3, 6);
//    int res2 = Game_Of_Life.isAlive(test, 4, 3);
//
//    Assert.assertEquals(0,res);
//    Assert.assertEquals(0, res1);
//    Assert.assertEquals(1, res2);
//
//
//  }
//
//}
