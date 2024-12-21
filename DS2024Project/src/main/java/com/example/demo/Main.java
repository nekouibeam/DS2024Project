package main.java.com.example.demo;

import java.io.IOException;

public class Main 
{
    public static void main(String[] args) 
    {
        GoogleQuery googleQuery= new GoogleQuery("Tomato");
        try 
        {
            /*
             * Using different keyword depends on the last number of your student ID
             * 0,1:Tomato
             * 2,3:Liver
             * 4,5:Pokemon
             * 6,7:Tissue
             * 8,9:Process
             */
            System.out.println(googleQuery.query());
//			System.out.println(new GoogleQuery("µf­X").query());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}     


