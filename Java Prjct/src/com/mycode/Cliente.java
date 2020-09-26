package com.mycode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Cliente extends JFrame implements ActionListener{
    JPanel p1,p2;
    JTextField t1;
    JButton b1;
    static JTextArea a1;

//    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    Cliente(){

//      Panel Superior-------------------------------------
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,94));
        p1.setBounds(300,0,600,70);
        add(p1);


        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/mycode/Iconos/bck1.png"));
        Image i2 = i1.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i1);
        l1.setBounds(15,22,27,27);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/mycode/Iconos/11.png"));
        Image i5 = i4.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i4);
        l2.setBounds(60,9,50,50);
        p1.add(l2);

        JLabel l3 = new JLabel("bruh2");
        l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(120,10,100,20);
        p1.add(l3);

        JLabel l4 = new JLabel("Chat activo");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 13));
        l4.setForeground(Color.WHITE);
        l4.setBounds(120,35,100,20);
        p1.add(l4);

        t1 = new JTextField();
        t1.setBounds(320,650,425,30);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(t1);

        b1 = new JButton("Send");
        b1.setBounds(770, 650, 100,30);
        b1.addActionListener(this);
        add(b1);

        a1 = new JTextArea();
        a1.setBounds(305,75,590,570);
        a1.setBackground(Color.CYAN);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        add(a1);

//        ----------------------------
        p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(Color.BLACK);
        p2.setBounds(0,0,300,700);
        add(p2);

        getContentPane().setBackground(Color.CYAN);
        setLayout(null);
        setSize(900, 700);
        setLocation(500, 50);
//        setVisible(true);
        setUndecorated(true);
        setVisible(true);



    }

    public void actionPerformed(ActionEvent ae){

        try{
            String out = t1.getText();

            a1.setText(a1.getText() + "\n\t\t\t\t"+out);

            dout.writeUTF(out);

            t1.setText("");

        }catch(Exception e) { }
    }

    public static void main(String[] args){
        new Cliente().setVisible(true);

        try{

            s = new Socket("127.0.0.1", 6001);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            String msginput = " ";

            msginput = din.readUTF();
            a1.setText(a1.getText()+"\n"+msginput);

        }catch(Exception e){}
    }
}

