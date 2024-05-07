package com.tugba.controller;

import com.tugba.entity.Follow;
import com.tugba.service.FollowService;
import com.tugba.service.UserService;
import com.tugba.utility.Response;

import java.util.Scanner;

public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    public FollowController() {
        this.followService=new FollowService();
        this.userService=new UserService();
    }

    public void followingUser(){
        int secim;
        do {
            System.out.println("""
                ******************************
                ********** FOLLOWING *********
                ******************************
                
                1- KULLANICILARI LISTELE
                2- TAKİP ET
                3 TAKIPCILERI LISTELE
                4- CIKIS
                """);
            secim= new Scanner(System.in).nextInt();
            switch (secim){
                case 1: userService.findAll().forEach(user -> {
                    System.out.println(" ID: " + user.getId() +" --> "+ " Username: "+ user.getUsername());});
                break;
                case 4:
                    System.out.println("CIKIS YAPILDI");break;
                case 2:
                    follower();break;
                case 3:
                    System.out.println(" --- TAKIPCI LISTESI ---");
                    followService.findAll().forEach(System.out::println);
                default:
                    System.out.println("HATALI BIR GIRIS YAPILDI LUTFEN TERKRAR SECIM YAPINIZ"); break;
            }
        }while (secim!=4);

    }

    public void follower(){
        System.out.print("Takip Etmek Istediğiniz Kişinin Kullanıcı Adını Giriniz: ");
        String username = new Scanner(System.in).nextLine();
        Response<Boolean> followResponse = followService.follower(username);
        System.out.println(followResponse.getMessage());
    }


}
