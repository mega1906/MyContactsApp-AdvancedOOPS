package com.mycontactapp.app;

import com.mycontactapp.controller.AppController;

/* Main class
 * Use Case 5 - View Contact Details
 * Starts the console app and routes the user to the selected module.
 * @author Developer
 * @version 5.0
 */
public class MyContactsApp {

    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.startApp();
    }
}
