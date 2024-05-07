package com.tugba;

import com.tugba.controller.FollowController;
import com.tugba.controller.UserController;

import java.util.Scanner;

public class RunnerInstagram {
    public static void main(String[] args) {
        UserController userController= new UserController();
        int secim;
        do {
            System.out.println("""
                ************************************
                *********** JAVA HS1 INSTA *********
                ************************************
                1- LOGIN
                2- REGISTER
                3- TAKIP ET
                0- EXIT
                """);
            System.out.print("Seçin ...:");
            secim = new Scanner(System.in).nextInt();
            switch (secim){
                case 1: userController.login();break;
                case 2: userController.register(); break;
                case 3: new FollowController().followingUser(); break;
                case 0:
                    System.out.println("CIKIS YAPILDI"); break;
                default:
                    System.out.println("Hatalı bir seçim yaptınız.");
        }
        }while (secim!=0);


    }
}
