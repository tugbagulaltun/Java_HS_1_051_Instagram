package com.tugba.controller;

import com.tugba.service.PostService;

import java.util.Scanner;

public class PostController {
    private final PostService postService;

    private PostController(){
        this.postService = new PostService();
    }

    /**
     * Instagram Gerçek çalışma Süreci
     * Login -> (username , password)
     * Login işlemi yapıldıktan sonra giriş yapan kullanıcıya benzersiz bir TOKEN üretilir ve iletilir.
     * bu token genellikle (JWT) şeklinde kullanılır.
     * -> JWT ile ilgili cihazda saklanır ve işlem yapılırken bu kullanılır.
     * -> Post atarken, yorum yazarken like atarken kimlik doğrulama işlemi için JWT kullanılır.
     */

    public void newPost(){
        System.out.println("""
                *******************************
                ********* POST OLUSTUR ********
                *******************************""");
        System.out.print("Kullanıcı Adı........: ");
        String userName = new Scanner(System.in).nextLine();
        System.out.print("Yorum Yazınız........: ");
        String comment = new Scanner(System.in).nextLine();
        System.out.print("Resim................: ");
        String imageUrl = new Scanner(System.in).nextLine();
        System.out.print("Adres................: ");
        String address = new Scanner(System.in).nextLine();

    }
}
