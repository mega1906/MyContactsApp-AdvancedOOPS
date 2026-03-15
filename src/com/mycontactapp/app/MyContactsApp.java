package com.mycontactapp.app;

import com.mycontactapp.controller.AppController;

/* Main class
 * Use Case 6 - Edit Contact
 * Starts the console app and routes the user to the selected module.
 * @author Developer
 * @version 6.0
 */
public class MyContactsApp {

    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.startApp();
    }
}
