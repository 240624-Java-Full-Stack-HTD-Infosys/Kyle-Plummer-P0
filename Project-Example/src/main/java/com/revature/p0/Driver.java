package com.revature.p0;

import com.revature.p0.menus.LoginMenu;
import com.revature.p0.menus.MainMenu;
import com.revature.p0.menus.MenuUtil;
import com.revature.p0.menus.RegisterMenu;

public class Driver {
    public static void main(String[] args) {
        MenuUtil menuUtil = MenuUtil.getMenuUtil();
        menuUtil.register(new MainMenu());
        menuUtil.register(new RegisterMenu());
        menuUtil.register(new LoginMenu());
        menuUtil.run();




    }

}
