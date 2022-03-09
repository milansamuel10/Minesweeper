//Name: MILAN SAJU SAMUEL
//ID: 100757350
//Professor: Akramul Azim
//Course: SOFE 3980U: Software Quality

import java.util.Scanner;

public class MainFile
{
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("---Mine Sweeper---");

        System.out.println();

        System.out.println("Pick your grid size (from 10x10 up to 20x20).");

        System.out.println();

        System.out.print("Enter the number of columns: ");
        int columns = keyboard.nextInt();
        keyboard.nextLine();

        System.out.print("Enter the number of rows: ");
        int rows = keyboard.nextInt();
        keyboard.nextLine();

        System.out.print("Enter the number of mines: ");
        int mines = keyboard.nextInt();
        keyboard.nextLine();

        while((columns < 10 || columns > 20) || (rows < 10 || rows > 20) || (mines >= columns * rows || mines < 0))
        {
            System.out.println();

            System.out.println("\"INVALID SETTINGS!\"");

            System.out.println();

            System.out.println("Pick your grid size (from 10x10 up to 20x20).");

            System.out.println();

            System.out.print("Enter the number of columns: ");
            columns = keyboard.nextInt();
            keyboard.nextLine();

            System.out.print("Enter the number of rows: ");
            rows = keyboard.nextInt();
            keyboard.nextLine();

            System.out.print("Enter the number of mines: ");
            mines = keyboard.nextInt();
            keyboard.nextLine();
        }

        new MS_Frame(columns,rows,mines);
    }
}