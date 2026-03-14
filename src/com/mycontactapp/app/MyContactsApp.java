package com.mycontactapp.app;

import com.mycontactapp.controller.RegistrationController;

/* Main class
 * Use Case 1 - User Registration
 * Handles console input for creating a new user account.
 * @author Developer
 * @version 1.0
 */
public class MyContactsApp {

    public static void main(String[] args) {
        RegistrationController registrationController = new RegistrationController();
        registrationController.startRegistration();
    }
}
