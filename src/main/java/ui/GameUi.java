package ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import util.CellUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @Date 2019/6/12 21:31
 */
public class GameUi extends JFrame implements ActionListener {
  private static GameUi frame;//静态初始化游戏
  private CellUnit cellUnit;//初始化细胞单元
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  private int maxLength, maxWidth; //长和宽
  private JButton[][] new_array; //一个按钮表示一个细胞
  private boolean[][] isSelected;  //按钮（即细胞）是否被选中
  private JButton OKEY, BUTTON_NOW_GENERATION, RANDOM_INIT_CELL, CLEAR_GENGARTION, SPPED_UP, SPEED_DOWN; //确定，当前代数，代数清零，加速，减速
  private JButton SELECT_SAMPLE, SAMPLE, CLEAR_CELL, NEXT_GENERATION, START, STOP, EXIT; //选择样例，示例，下一代，开始繁衍，暂停，退出
  private JComboBox lengthList, widthList;//长宽选择
  private boolean running;//线程运行状态
  private Thread thread;//新建线程
  private boolean deadCell;//是否为活细胞
  @Setter(AccessLevel.PUBLIC)
  @Getter(AccessLevel.PUBLIC)
  private int speed = 1000;//初始化运行速度


  private GameUi(String name) {
    super(name);
    initGUI();
  }

  public static void main(String[] arg) {
    frame = new GameUi("康威生命游戏 By XueYuKun&YinYu");
  }

  //初始化界面
  private void initGUI() {
    if (maxWidth == 0) {
      maxWidth = 20;
    }
    if (maxLength == 0) {
      maxLength = 35;
    }

    //初始化细胞单元对象
    cellUnit = new CellUnit(maxLength, maxWidth);

    JPanel backPanel, centerPanel, bottomPanel;
    JLabel jWidth, jLength, jNowGeneration;
    backPanel = new JPanel(new BorderLayout());
    centerPanel = new JPanel(new GridLayout(maxWidth, maxLength));
    bottomPanel = new JPanel();
    this.setContentPane(backPanel);
    backPanel.add(centerPanel, "Center");
    backPanel.add(bottomPanel, "South");

    new_array = new JButton[maxWidth][maxLength];
    isSelected = new boolean[maxWidth][maxLength];
    for (int i = 0; i < maxWidth; i++) {
      for (int j = 0; j < maxLength; j++) {
        new_array[i][j] = new JButton(""); //按钮内容置空表示细胞
        new_array[i][j].setBackground(Color.white); //初始时所有细胞均为死细胞
        centerPanel.add(new_array[i][j]);
      }
    }

    jLength = new JLabel("长度：");
    lengthList = new JComboBox();
    for (int i = 5; i <= 200; i++)
      this.lengthList.addItem(String.valueOf(i));
    lengthList.setSelectedIndex(maxLength - 5); //设置的初始最小值为5，故减去5，下同

    jWidth = new JLabel("宽度：");
    widthList = new JComboBox();
    for (int i = 5; i <= 100; i++)
      widthList.addItem(String.valueOf(i));
    widthList.setSelectedIndex(maxWidth - 5);

    OKEY = new JButton("确定");
    //SELECT_SAMPLE = new JButton("选择样例");
    SAMPLE = new JButton("示例");
    jNowGeneration = new JLabel("当前代数：");
    BUTTON_NOW_GENERATION = new JButton("" + cellUnit.getNowGeneration());//Buttom不能直接添加int，故采用此方式
    BUTTON_NOW_GENERATION.setEnabled(false);
    CLEAR_GENGARTION = new JButton("清零代数");
    SPPED_UP = new JButton("加速");
    SPEED_DOWN = new JButton("减速");
    RANDOM_INIT_CELL = new JButton("随机初始化");
    CLEAR_CELL = new JButton("细胞清零");
    START = new JButton("开始繁衍");
    NEXT_GENERATION = new JButton("下一代");
    STOP = new JButton("暂停");
    EXIT = new JButton("退出");

    bottomPanel.add(jLength);
    bottomPanel.add(lengthList);
    bottomPanel.add(jWidth);
    bottomPanel.add(widthList);
    bottomPanel.add(OKEY);
    //bottomPanel.add(SELECT_SAMPLE);
    bottomPanel.add(SAMPLE);
    bottomPanel.add(jNowGeneration);
    bottomPanel.add(BUTTON_NOW_GENERATION);
    bottomPanel.add(CLEAR_GENGARTION);
    bottomPanel.add(SPPED_UP);
    bottomPanel.add(SPEED_DOWN);
    bottomPanel.add(RANDOM_INIT_CELL);
    bottomPanel.add(CLEAR_CELL);
    bottomPanel.add(START);
    bottomPanel.add(NEXT_GENERATION);
    bottomPanel.add(STOP);
    bottomPanel.add(EXIT);


//    SELECT_SAMPLE.setBounds(400, 200, 100, 100);
//    SELECT_SAMPLE.setVisible(true);
//    SELECT_SAMPLE.addActionListener(this);

    // 设置窗口
    this.setSize(1280, 720);
    this.setResizable(true);
    this.setLocationRelativeTo(null); // 让窗口在屏幕居中
    this.setVisible(true);// 将窗口设置为可见的

    // 注册监听器
    this.addWindowListener(new WindowAdapter() {
      public void windowClosed(WindowEvent e) {
        System.exit(0);
      }
    });
    OKEY.addActionListener(this);
    CLEAR_GENGARTION.addActionListener(this);
    //SELECT_SAMPLE.addActionListener(this);
    SAMPLE.addActionListener(this);
    SPPED_UP.addActionListener(this);
    SPEED_DOWN.addActionListener(this);
    RANDOM_INIT_CELL.addActionListener(this);
    CLEAR_CELL.addActionListener(this);
    NEXT_GENERATION.addActionListener(this);
    START.addActionListener(this);
    STOP.addActionListener(this);
    EXIT.addActionListener(this);

    for (int i = 0; i < maxWidth; i++) {
      for (int j = 0; j < maxLength; j++) {
        new_array[i][j].addActionListener(this);
      }
    }
  }

  /*
    监视器监控按钮事件
   */
  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == OKEY) { //确定
      //设置的初始最小值为5，故加上5
      frame.setMaxLength(lengthList.getSelectedIndex() + 5);
      frame.setMaxWidth(widthList.getSelectedIndex() + 5);
      initGUI();

      cellUnit = new CellUnit(getMaxWidth(), getMaxLength());

    } else if (event.getSource() == CLEAR_GENGARTION) {
      //代数清零
      cellUnit.setNowGeneration(0);
      BUTTON_NOW_GENERATION.setText("" + cellUnit.getNowGeneration());
      //刷新当前代数
      running = false;
      thread = null;
    } else if (event.getSource() == RANDOM_INIT_CELL) {
      //随机初始化
      cellUnit.randomCell();
      viewCellUnit();
      running = false;
      thread = null;
    } else if (event.getSource() == CLEAR_CELL) {
      //细胞清零
      cellUnit.killAllCell();
      viewCellUnit();
      running = false;
      thread = null;
    } else if (event.getSource() == START) {
      //开始
      running = true;
      thread = new Thread(new Runnable() {
        public void run() {
          while (running) {
            makeNextGeneration();
            repaint();
            try {
              Thread.sleep(frame.getSpeed());
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            deadCell = true;
            for (int row = 1; row <= maxWidth; row++) {
              for (int col = 1; col <= maxLength; col++) {
                if (cellUnit.getMulti_array()[row][col] != 0) {
                  deadCell = false;
                  break;
                }
              }
              if (!deadCell) {
                break;
              }
            }
            if (deadCell) {
              JOptionPane.showMessageDialog(null, "细胞全部死亡，游戏结束");
              running = false;
              thread = null;
            }
          }
        }
      });
      thread.start();
    } else if (event.getSource() == SPPED_UP) {
      if (speed <= 100) {
        JOptionPane.showMessageDialog(null, "无法继续加速", "错误", JOptionPane.ERROR_MESSAGE);
      }
      frame.setSpeed(speed -= 100);
    } else if (event.getSource() == SPEED_DOWN) {
      frame.setSpeed(speed += 100);
    } else if (event.getSource() == SELECT_SAMPLE) {
      JFileChooser jFileChooser = new JFileChooser();
      JTextArea jta = new JTextArea(5, 3);
      jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      jFileChooser.showDialog(new JLabel(), "选择");
      File file = jFileChooser.getSelectedFile();
      if (file.isDirectory()) {
        JOptionPane.showMessageDialog(null, "不能选择文件夹！", "错误", JOptionPane.ERROR_MESSAGE);
      } else if (file.isFile()) {
        String fileName = file.getName();
        FileReader fr = null;
        BufferedReader br = null;
        try {
          fr = new FileReader(fileName);
          br = new BufferedReader(fr);
          String str;
          while ((str = br.readLine()) != null) {
            jta.append(str + "\n");
          }
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            br.close();
            fr.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    } else if (event.getSource() == NEXT_GENERATION) {
      //下一代
      makeNextGeneration();
      running = false;
      thread = null;
    } else if (event.getSource() == SAMPLE) {

      cellUnit.setMulti_array(Sample.init());
      getSample();
      running = false;
      thread = null;
    } else if (event.getSource() == STOP) {
      //暂停
      running = false;
      thread = null;
    } else if (event.getSource() == EXIT) {
      //退出
      frame.dispose();
      System.exit(0);
    } else {
      int[][] grid = cellUnit.getMulti_array();
      for (int i = 0; i < maxWidth; i++) {
        for (int j = 0; j < maxLength; j++) {
          if (event.getSource() == new_array[i][j]) {
            isSelected[i][j] = !isSelected[i][j];
            if (isSelected[i][j]) {
              new_array[i][j].setBackground(new Color(218,52,116));
              grid[i + 1][j + 1] = 1;
            } else {
              new_array[i][j].setBackground(Color.white);
              grid[i + 1][j + 1] = 0;
            }
            break;
          }
        }
      }
      cellUnit.setMulti_array(grid);
    }
  }

  //细胞开始分裂
  private void makeNextGeneration() {
    cellUnit.division();
    repaint();
    viewCellUnit();
    //刷新当前代数
    BUTTON_NOW_GENERATION.setText("" + cellUnit.getNowGeneration());
  }

  //画面展示
  private void viewCellUnit() {
    int[][] grid = cellUnit.getMulti_array();
    for (int i = 1; i < maxWidth - 1; i++) {
      for (int j = 1; j < maxLength - 1; j++) {
        if (grid[i][j] == 1) {
          //活细胞
          new_array[i - 1][j - 1].setBackground(new Color(218,52,116));
        } else {
          //死细胞
          new_array[i - 1][j - 1].setBackground(Color.white);
        }
      }
    }
  }

  //获取样例
  private void getSample() {
    int[][] grid = Sample.init();
    for (int i = 1; i < 18; i++) {
      for (int j = 1; j < 38; j++) {
        if (grid[i][j] == 1) {
          //活细胞
          new_array[i - 1][j - 1].setBackground(new Color(218,52,116));
        } else {
          //死细胞
          new_array[i - 1][j - 1].setBackground(Color.white);
        }
      }
    }
  }
}

