/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goose;

/**
 *
 * @author avall
 */

import java.util.*;
import java.io.*;

class Player {
    String playerName;
    int playerPosition=0;
    
    Player (String name) {
        this.playerName=name;
    }
}

public class Goose {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new
        InputStreamReader(System.in));
        ArrayList<Player> players = new ArrayList<>();
        String str;
        
        System.out.println("Enter commands.");
        System.out.println("Enter 'stop' to quit.");

        for (str = br.readLine();!str.equals("stop");) {           
            
            switch(Goose.action(str)) {
                
                case 0:
                    String tempPlayerName=str.substring(11);
                    if (playerDoNotExist(tempPlayerName,players)) {
                        players.add(new Player(tempPlayerName));
                        System.out.println("players:"+printPlayers(players));
                    } else {
                        System.out.println(tempPlayerName+": already existing player");
                    }
                    break;
                
                case 1:
                case 2:
                    
                    String tempPlayerName1;
                    int dice1;
                    int dice2;
                            
                    if (Goose.action(str)==1) {
                    tempPlayerName1=str.substring(5,str.length() - 5);
                    dice1=Integer.parseInt(str.substring(str.length() - 4,str.length() - 3));
                    dice2=Integer.parseInt(str.substring(str.length() - 1,str.length()));
                    } else {
                    tempPlayerName1=str.substring(5);
                    Random rand = new Random();
                    dice1=rand.nextInt(6) + 1;
                    dice2=rand.nextInt(6) + 1;
                    }
                    if (playerDoNotExist(tempPlayerName1,players)) {
                        System.out.println("Player does not exist.");
                    } else {
                        move(tempPlayerName1,dice1,dice2,players);
                    }
                    break;
                
                case 3:
                    System.out.println("undefined command");
                    break;
            }
            
            str = br.readLine();
            
        }
    }    

    private static int action (String a) {
        int b=3;
        if (a.matches("add player \\w+")) b=0;
        if (a.matches("move \\w+ [1-6], [1-6]")) b=1;
        if (a.matches("move \\w+")) b=2;
        return b;
        }

    private static boolean playerDoNotExist(String tempPlayerName,ArrayList<Player> players) {
        boolean b=true;
        for (Player p : players) {
            if (tempPlayerName.equals(p.playerName)) {
                b=false;
                break;
            }
        }
        return b;
    }

    private static String printPlayers(ArrayList<Player> players) {
        String str="";
        for (Player p : players) {
            str=str+" "+p.playerName+",";
        }
        str=str.substring(0, str.length() - 1);
        return str;
    }

    private static void move(String tempPlayerName1, int dice1, int dice2, ArrayList<Player> players) {
        
        for (Player p : players) {
            if (tempPlayerName1.equals(p.playerName)) {
                int initial=p.playerPosition;
                String str=p.playerName+" rolls "+dice1+", "+dice2+". "+p.playerName+" moves from "+(initial==0?"Start":initial)+" to ";
                
                p.playerPosition=p.playerPosition+dice1+dice2;
                if (p.playerPosition==6) {
                    p.playerPosition=12;
                    str=str+"The Bridge. "+p.playerName+" jumps to ";
                }
                while (p.playerPosition==5 || p.playerPosition==9 ||p.playerPosition==14 ||p.playerPosition==18 ||p.playerPosition==23 ||p.playerPosition==27) {
                    str=str+p.playerPosition+", The Goose. "+p.playerName+" moves again and goes to ";
                    p.playerPosition=p.playerPosition+dice1+dice2;    
                }
                str=str+p.playerPosition+". ";
                for (Player q : players) {
                    if (!tempPlayerName1.equals(q.playerName) && p.playerPosition==q.playerPosition) {
                        q.playerPosition=initial;
                        str=str+"On "+p.playerPosition+" there is "+q.playerName+", who returns to "+initial+". ";
                    }    
                }
                if (p.playerPosition>63) {
                    p.playerPosition=126-p.playerPosition;
                    str=str+p.playerName+" bounces! "+p.playerName+" returns to "+p.playerPosition;
                }
                if (p.playerPosition==63) {
                    str=str+p.playerName+" Wins!";
                }
                System.out.println(str);
                break;
            }
        }
    }
}
