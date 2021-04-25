/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.big.hw;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author Administrator
 */
public class BigHW extends JFrame implements ActionListener
{
    MenuBar mb;   //MenuBar类简述平台的一个绑定到特定框架的（下拉）菜单栏
    Menu menu;   //Menu对象是一个从menu bar部署的下拉菜单组件
    MenuItem[] items;   //一个menu里面的所有元素都属于该MenuItem类
    LinkedList data;   //{@code List}和{@code Deque}的双向链表实现接口。
                           //实现所有可选的列表操作，并允许所有元素（包括{@code null}）。 
    TextArea ta;   //TextArea对象是多行区域显示文本。
                        //可以设置为允许编辑或为只读。
    final int NUM=7;
    final String Message="This is an employee management system";
    final String[] colNames={"NUMBER","NAME","GENDER","LENGTH","SALARY","LEVEL"};
    final String fileName="C:\\sysdata\\javadata.txt";

    //构造方法
    public BigHW()  {
        this.data=new LinkedList();
        this.setGUI();
    }

protected void setGUI()
{
    this.setBounds(150, 150, 500, 300);
    mb=new MenuBar();
    menu=new Menu("OPTIONS");
    menu.setFont(new Font("Arial Black",Font.BOLD,22));
    int i;
    items=new MenuItem[NUM];
    for(i=0;i<this.NUM;i++)
    {
        items[i]=new MenuItem();
        items[i].addActionListener(this);
        menu.add(items[i]);
        items[i].setFont(new Font("Arial Black",Font.BOLD,22));
    }
    items[0].setLabel("Add Employee"); items[1].setLabel("Print Listings");
     items[2].setLabel("Save Data"); items[3].setLabel("Load Data");
      items[4].setLabel("Search Employee"); items[5].setLabel("Delete Employee");
       items[6].setLabel("Exit The System");
   this.setMenuBar(mb);
   mb.add(menu);
   ta=new TextArea(this.Message,20,25);
   ta.setFont(new Font("Arial Black",Font.BOLD,22));
   ta.setEnabled(false);   //失能
   this.setLayout(new BorderLayout());   //设置布局管理器
   this.add(ta,BorderLayout.CENTER);   //添加特定组件到容器终端
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   this.setVisible(true);   //显示
}

protected void addEmployee()
{
    final JFrame f1=new JFrame("Add A New Employee");   //新建一个“Add A New Employee”窗口框架
    f1.setBounds(250, 250, 800, 300);
    String[][] inputData = {{"Input number here","Input name here","Input gender here",
                                "Input length here","Input salary here","Input level here"}};
    final JTable inputTab=new JTable(inputData,this.colNames);   //新建一个二维表格
    inputTab.setFont(new Font("Arial",Font.PLAIN,16));
    inputTab.getTableHeader().setFont(new Font("Arial",Font.PLAIN,16));
    JButton b1=new JButton("RESET");   //设置一个RESET按键
    JButton b2=new JButton("SUBMIT");   //设置一个SUBMIT按键
    //RESET按键具体功能实现方法
    b1.addActionListener(
    new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            TableModel jm1=inputTab.getModel();
            for(int i=0;i<inputTab.getRowCount();i++)
            {
                for(int j=0;j<inputTab.getColumnCount();j++)
                {
                    jm1.setValueAt("", i, j);
                }
            }
        }
    }
            );
   //SUBMIT按键具体功能实现方法
   b2.addActionListener(
   new ActionListener()
   {
       public void actionPerformed(ActionEvent e)
       {
           TableModel jm1=inputTab.getModel();
           for(int i=0;i<inputTab.getRowCount();i++)
           {
             //增补指定的元素到列表最后
             data.add(new EMPLOYEE((String)jm1.getValueAt(i, 0),(String)jm1.getValueAt(i, 1),
                                    (String)jm1.getValueAt(i, 2),(String)jm1.getValueAt(i, 3),
                                    (String)jm1.getValueAt(i, 4),(String)jm1.getValueAt(i, 5)));
           }
       }
   }
           );
    JScrollPane jsp=new JScrollPane(inputTab);   //创建一个能显示特定组件内容的滚动视野
    JPanel jp=new JPanel();   //使用双缓冲区和流布局创建一个新的通用轻量级容器
    jp.add(b1); jp.add(b2);
    f1.setLayout(new BorderLayout());
    f1.add(jsp,BorderLayout.CENTER);
    f1.add(jp,BorderLayout.SOUTH);
    f1.addWindowListener(
    new WindowAdapter()
    {
        public void windowClosing(WindowEvent we)
        {
            f1.dispose();
        }
    }
            );
    f1.setVisible(true);
}

protected void deleteEmployee()
{
    final JFrame f1=new JFrame("Delete An Employee");
    f1.setBounds(250, 250, 800, 300);
    String[][] inputData = {{"Input the employee number to be deleted","","","","",""}};
    final JTable inputTab=new JTable(inputData,this.colNames);
    inputTab.setFont(new Font("Arial",Font.PLAIN,16));
    inputTab.getTableHeader().setFont(new Font("Arial",Font.PLAIN,16));
    JButton b1=new JButton("RESET");
    JButton b2=new JButton("SUBMIT");
    b1.addActionListener(
    new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            TableModel jm1=inputTab.getModel();
            for(int i=0;i<inputTab.getRowCount();i++)
            {
                for(int j=0;j<inputTab.getColumnCount();j++)
                {
                    jm1.setValueAt("", i, j);
                }
            }
        }
    }
            );
    b2.addActionListener(
    new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            TableModel jm1=inputTab.getModel();
            String number=(String)jm1.getValueAt(0,0);
            Iterator ptr=data.iterator();
            boolean isDelete=false;
            while(ptr.hasNext())
            {
                 EMPLOYEE u=(EMPLOYEE)ptr.next();
                 if(number.equals(u.getNumber()))
                 {
                     isDelete=true;
                     ptr.remove();   //删除该员工信息
                     break;
                 }
            }
        }
    }
            );
    JScrollPane jsp=new JScrollPane(inputTab);
    JPanel jp=new JPanel();
    jp.add(b1); jp.add(b2);
    f1.setLayout(new BorderLayout());
    f1.add(jsp,BorderLayout.CENTER);
    f1.add(jp,BorderLayout.SOUTH);
    f1.addWindowListener(
    new WindowAdapter()
    {
        public void windowClosing(WindowEvent we)
        {
            f1.dispose();
        }
    }
            );
    f1.setVisible(true);
}

protected void printListings()
{
    final JFrame f1=new JFrame("Print The Employees");
    f1.setBounds(250, 250, 800, 300);
    String[][] inputData = new String[data.size()][6];
    Iterator ptr=data.iterator();  //返回data双链表中元素的迭代器
    int i=-1;
    while(ptr.hasNext())  //还有元素返回真
    {
        i++;
        EMPLOYEE u=(EMPLOYEE)ptr.next();
        inputData[i][0]=u.getNumber();
        inputData[i][1]=u.getName();
        inputData[i][2]=u.getGender();
        inputData[i][3]=u.getLength();
        inputData[i][4]=u.getSalary();
        inputData[i][5]=u.getLevel();
    }
    final JTable inputTab=new JTable(inputData,this.colNames);
    inputTab.setFont(new Font("Arial",Font.PLAIN,16));
    inputTab.getTableHeader().setFont(new Font("Arial",Font.PLAIN,16));
    JScrollPane jsp=new JScrollPane(inputTab);
     f1.setLayout(new BorderLayout());
    f1.add(jsp,BorderLayout.CENTER);
    f1.addWindowListener(
    new WindowAdapter()
    {
        public void windowClosing(WindowEvent we)
        {
            f1.dispose();
        }
    }
            );
    f1.setVisible(true);
}

public void searchEmployee()
{
    final JFrame f1=new JFrame("Search An Employee");
    f1.setBounds(250, 250, 800, 300);
    String[][] inputData = {{"Input the employee number","","","","",""}};
    final JTable inputTab=new JTable(inputData,this.colNames);
    inputTab.setFont(new Font("Arial",Font.PLAIN,16));
    inputTab.getTableHeader().setFont(new Font("Arial",Font.PLAIN,16));
    JButton b1=new JButton("RESET");
    JButton b2=new JButton("SUBMIT");
    b1.addActionListener(
    new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            TableModel jm1=inputTab.getModel();
            for(int i=0;i<inputTab.getRowCount();i++)
            {
                for(int j=0;j<inputTab.getColumnCount();j++)
                {
                    jm1.setValueAt("", i, j);
                }
            }
        }
    }
            );
    b2.addActionListener(
    new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            TableModel jm1=inputTab.getModel();
            String number=(String)jm1.getValueAt(0,0);
            Iterator ptr=data.iterator();
            boolean found=false;
            String name=null;String gender=null;String length=null;String salary=null;String level=null;
            while(ptr.hasNext())
            {
                 EMPLOYEE u=(EMPLOYEE)ptr.next();
                 if(number.equals(u.getNumber()))
                 {
                     found=true;
                     name=u.getName();gender=u.getGender();length=u.getLength();
                     salary=u.getSalary();level=u.getLevel();
                     break;
                 }
            }
            if(found)
            {
                jm1.setValueAt(name, 0, 1);jm1.setValueAt(gender, 0, 2);jm1.setValueAt(length, 0, 3);
                jm1.setValueAt(salary, 0, 4);jm1.setValueAt(level, 0, 5);
            }
            else 
            {
                jm1.setValueAt("not found", 0, 1);jm1.setValueAt("not found", 0, 2);
                jm1.setValueAt("not found", 0, 3);jm1.setValueAt("not found", 0, 4);
                jm1.setValueAt("not found", 0, 5);
            }
        }
    }
            );
    JScrollPane jsp=new JScrollPane(inputTab);
    JPanel jp=new JPanel();
    jp.add(b1); jp.add(b2);
    f1.setLayout(new BorderLayout());
    f1.add(jsp,BorderLayout.CENTER);
    f1.add(jp,BorderLayout.SOUTH);
    f1.addWindowListener(
    new WindowAdapter()
    {
        public void windowClosing(WindowEvent we)
        {
            f1.dispose();
        }
    }
            );
    f1.setVisible(true);
}

public void saveData()
{
    try
    {
        FileOutputStream fos=new FileOutputStream(this.fileName);   //创建文件输出流以写入具有指定名称的文件 
        ObjectOutputStream oos=new ObjectOutputStream(fos);//创建一个ObjectOutputStream写入指定的OutputStream 
        oos.writeObject(this.data);   //将指定的对象写入ObjectOutputStream
        oos.flush();   //强制把数据输出，清空缓冲区
        oos.close();
        fos.close();
    }catch(Exception e)
    {
        e.printStackTrace();   //将这个throwable及其回溯打印到标准错误流。
    }
}

public void loadData()
{
    try
    {
        FileInputStream fis=new FileInputStream(this.fileName);
        ObjectInputStream ois=new ObjectInputStream(fis);
        LinkedList oldData = (LinkedList)ois.readObject();
        this.data.addAll(oldData);//按照指定集合的迭代器返回的顺序，将指定集合中的所有元素追加到此列表的末尾
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}

public void exitSystem()
{
    this.setVisible(false);   //不显示，退出系统
}

public static void main(String[] args)
{
    new BigHW();
}
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if(e.getSource()==items[0])this.addEmployee();
        else if(e.getSource()==items[1])this.printListings();
        else if(e.getSource()==items[2])this.saveData();
        else if(e.getSource()==items[3])this.loadData();
        else if(e.getSource()==items[4])this.searchEmployee();
        else if(e.getSource()==items[5])this.deleteEmployee();
        else if(e.getSource()==items[6])this.exitSystem();
    }
}



class EMPLOYEE implements Serializable
{
    private String number,name,gender,length,salary,level;

    public EMPLOYEE(String number, String name, String gender, String length, String salary, String level) {
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.length = length;
        this.salary = salary;
        this.level = level;
    }

    public EMPLOYEE() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
