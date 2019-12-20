package com.example.lib;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class MyClass extends JFrame {
    private JPanel centralPanel;//拼圖按鈕面板
    private JButton emptyButton;//空白按鈕
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        MyClass frame = new MyClass();
        frame.setVisible(true);
    }
    public MyClass(){
        super();
        setResizable(false);//窗體大小不可改變
        setTitle("拼圖遊戲");
        setBounds(125,250,600,580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//關閉窗體時退出程式
        final JPanel topPanel = new JPanel();//建立面板物件
        topPanel.setBorder(new TitledBorder(null,"",TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.DEFAULT_POSITION,null,null));
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        final JLabel modelLable =new JLabel();//顯示參考圖片的標籤
        modelLable.setIcon(new ImageIcon(""));
        topPanel.add(modelLable, BorderLayout.WEST);
        final JButton startButton = new JButton("下一局");
        startButton.addActionListener(new StartButtonAction());
        topPanel.add(startButton, BorderLayout.CENTER);
        //建立拼圖的面板
        centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(0,4));
        getContentPane().add(centralPanel, BorderLayout.CENTER);
        String[][] randomOrder = reorder();//獲得圖片的隨機擺放順序
        for(int i=0;i<4;++i)
            for(int j =0;j<4;++j){
                final JButton button = new JButton();
                button.setName(i+""+j);
                button.setIcon(new ImageIcon(randomOrder[i][j]));
                if(randomOrder[i][j].equals(" model00.jpg"))
                    emptyButton = button;
                button.addActionListener(new ImgButtonAction());
                centralPanel.add(button);
            }
    }
    private String[][] reorder()//生成網格圖片的隨機擺放順序
    {
        String[][] exactOrder = new String [4][4];//獲得圖片正確的擺放順序
        for(int i=0;i<4;++i)
            for(int j =0;j<4;++j)
                exactOrder[i][j]="model"+i+j+".jpg";
        String[][] randomOrder = new String[4][4];
        for(int i=0;i<4;++i)
            for(int j =0;j<4;++j)
                while(randomOrder[i][j]==null)//隨機的指定網格為空
                {
                    int r = (int)(Math.random()*4);//隨機行
                    int c = (int)(Math.random()*4);//取隨機列
                    if(exactOrder[r][c]!=null){
                        randomOrder[i][j] = exactOrder[r][c];
                        exactOrder[r][c]=null;
                    }
                }
        return randomOrder;
    }
    class ImgButtonAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String emptyName = emptyButton.getName();
            char emptyRow = emptyName.charAt(0);//空白按鈕行
            char emptyCol = emptyName.charAt(1);//空白按鈕列
            JButton clickButton =(JButton)e.getSource();
            String clickName = clickButton.getName();
            char clickRow = clickName.charAt(0);
            char clickCol = clickName.charAt(1);
            //判斷是否相鄰
            if(Math.abs(clickRow-emptyRow)+Math.abs(clickCol-emptyCol)==1){
                //將被單擊的圖片移動到空白按鈕上
                emptyButton.setIcon(clickButton.getIcon());
                clickButton.setIcon(new ImageIcon("model00.jpg"));
                emptyButton = clickButton;
            }
        }
    }
    class StartButtonAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String[][] randomOrder =reorder();
            int i = 0;
            for(int row=0;row<4;++row)
                for(int col=0;col<4;++col)
                {
                    JButton button=(JButton)centralPanel.getComponent(i++);
                    button.setIcon(new ImageIcon(randomOrder[row][col]));
                    if(randomOrder[row][col].equals("model00.jpg"))
                        emptyButton = button;
                }
        }
    }
}
