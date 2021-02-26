package com.cardealership.application;

import com.cardealership.model.User;
import com.cardealership.service.MenuService;

public class Application {

    public static void main(String[] args){
        MenuService menuService = new MenuService();
        menuService.MainMenu();
    }
}
