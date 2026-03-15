package com.mycontactapp.app;

import com.mycontactapp.controller.AppController;

/* Main class
 * Use Case 12 - Apply Tags to Contacts
 * Starts the console app and routes the user to the selected module.
 * @author Developer
 * @version 12.0
 */
public class MyContactsApp {

    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.startApp();
    }
}
