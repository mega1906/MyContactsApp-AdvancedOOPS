package com.mycontactapp.app;

import com.mycontactapp.controller.AppController;

/* Main class
 * Use Case 8 - Bulk Operations
 * Starts the console app and routes the user to the selected module.
 * @author Developer
 * @version 8.0
 */
public class MyContactsApp {

    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.startApp();
    }
}
