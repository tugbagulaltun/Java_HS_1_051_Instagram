package com.tugba.controller;

import com.tugba.entity.User;
import com.tugba.service.UserService;
import com.tugba.utility.Response;
import com.tugba.utility.StaticValues;

import java.util.Scanner;

public class UserController {
    private final UserService userService;

    public UserController(){
        this.userService= new UserService();
    }

    public void register(){
        boolean isRegister = false;
        do {
            System.out.println("""
                ************************************
                ************** ÜYE OL **************
                ************************************
                """);
            System.out.print("Kullanıcı Adı........: ");
            String userName = new Scanner(System.in).nextLine();
            System.out.print( "Kullanıcı email.....: ");
            String email = new Scanner(System.in).nextLine();
            System.out.print("Sifre ...............: ");
            String password = new Scanner(System.in).nextLine();
            System.out.print("Sifre Doğrulama......: ");
            String rePassword = new Scanner(System.in).nextLine();
            /**
             * 1- Eğer şifreler uyuşmuyor ise, tekrar giriş yapması sağlanır. (CONTROLLER)
             * 2- Bilgiler zorunludur boş bırakılamaz ve eksik girilemez. (CONTROLLER)
             */

            if (!password.equals(password)) {
                System.out.println("Sifreler uyuşmuyor.");
            } else if (userName.length() < 3 || userName.length() > 64) {
                System.out.println("Tekrar kullanıcı adını girin. 3-64 Arasında olmalıdır.");
            } else if (!(email.contains("@"))) {
                System.out.println("Lütfen email formatunda giriş yapınız.");
            } else {
                Response<Boolean> response = userService.register(userName,email,password);
                isRegister = response.getData();
                System.out.println(response.getMessage());
            }

        }while (!isRegister);
    }

    public void login(){
        System.out.println("""
                ************************************
                ************** GIRIS YAP ***********
                ************************************
                """);
        System.out.print("Kullanıcı Adı........: ");
        String userName = new Scanner(System.in).nextLine();
        System.out.print("Sıfre ...............: ");
        String password = new Scanner(System.in).nextLine();
        Response<User>  response = userService.login(userName,password);
        if (response.getStatusCode()==400 || response.getStatusCode()==500){
            System.out.println(response.getMessage());
        }else {
            /**
             * Giriş başarılı oldu işlemleri yapılacak.
             * Geri dönen kullanıcı bilgisi uygulama içinde static olarak saklanacak (Mobil uygulamalarda lokal de saklanır.)
             */
            System.out.println("Başarılı bir şekilde giriş yapıldı");
            StaticValues.user= response.getData();
        }

    }
}
