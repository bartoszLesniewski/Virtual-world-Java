package pl.bartosz.po.projekt2;

import pl.bartosz.po.projekt2.wirtualnyswiat.GUI;

import java.awt.*;

public class JavaProjekt2
{
    public static void main(String[] args)
    {
       EventQueue.invokeLater(new Runnable()
       {
           @Override
           public void run()
           {
               GUI gui = new GUI();
               gui.menu();
           }
       });
    }
}
