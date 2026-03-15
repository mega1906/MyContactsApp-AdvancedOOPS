# MyContacts App - Advanced OOPS

MyContacts App is a Java console-based contact management application developed use case by use case to demonstrate advanced object-oriented programming, design patterns, and core Java concepts through a single contact management system.

The application allows a user to register, log in, manage profile information, create and organize contacts, apply tags, search and filter contacts, and perform bulk contact operations through a simple console interface.

## Use Cases

### UC-01: User Registration
A new user creates an account by entering user type, full name, email, phone number, password, and city.  
The system validates each field before moving to the next input and stores the user after successful registration.

### UC-02: User Authentication
A registered user logs in using email and password.  
The system allows only one active session at a time and shows the logged-in user menu after successful authentication.

### UC-03: User Profile Management
A logged-in user can view profile details, update selected fields, change password, and manage preferences.  
The profile module supports field-wise editing and preference toggles such as email notifications and contact view.

### UC-04: Create Contact
A logged-in user creates a new contact by selecting the contact type and entering contact details.  
The system supports person and organization contacts, multiple phone numbers, multiple email addresses, optional address, optional notes, unique contact IDs, and timestamp tracking.

### UC-05: View Contact Details
A logged-in user selects a contact from the available contact list and views complete contact information.  
The system shows reference ID, contact type, phones, emails, tags, address, notes, timestamps, and contact frequency.

### UC-06: Edit Contact
A logged-in user edits an existing contact by selecting the contact and choosing which field to update.  
The system supports editing name, phone numbers, email addresses, address, and notes.  
Undo and redo operations are also supported for contact edits.

### UC-07: Delete Contact
A logged-in user deletes a selected contact after confirmation.  
The system supports both soft delete and hard delete.  
Soft-deleted contacts are hidden from normal operations, while hard delete removes them permanently.

### UC-08: Bulk Operations
A logged-in user performs operations on multiple contacts at once.  
The system supports bulk delete, bulk tag assignment, and bulk export using multiple selected contact IDs or all contacts.

### UC-09: Search Contacts
A logged-in user searches contacts by name, phone number, email, or tags.  
The system allows one or multiple search fields together and returns matching contacts in a readable format.

### UC-10: Advanced Filtering
A logged-in user applies advanced filters such as tag, date added, and frequently contacted.  
The filtered results can be sorted by name, date added, or contact frequency.

### UC-11: Create and Manage Tags
A logged-in user views predefined tags, creates custom tags, and manages available tags for organizing contacts.  
The system keeps tags unique and reusable across contacts.

### UC-12: Apply Tags to Contacts
A logged-in user assigns one or more tags to contacts and removes tags when needed.  
The system maintains the relationship between contacts and tags and updates contact details whenever tags change.

## Final Workflow

The application follows one continuous user flow:

1. A new user starts the app and registers an account.
2. The registered user logs in using email and password.
3. After login, the user can manage profile details and preferences.
4. The user creates contacts by entering contact details and selecting contact type.
5. The user can view full contact details at any time by selecting a contact reference ID.
6. The user can edit contact information and use undo/redo for recent changes.
7. The user can delete contacts using soft delete or hard delete.
8. The user can create tags and assign tags to contacts for better organization.
9. The user can search contacts using name, email, phone number, or tags.
10. The user can apply advanced filters and sorting to narrow contact results.
11. The user can perform bulk actions on multiple contacts, such as delete, tag, and export.
12. The user logs out or exits the application.

## Sample Workflow Output

```text
====================================
 Welcome to MyContacts App
====================================
1. User Registration
2. User Login
3. Exit
Enter choice: 1

Select user type:
1. Free User
2. Premium User
Enter choice: 1
Enter full name: Mega
Enter email: mega@example.com
Enter phone number: 9876543210
Enter password: Mega123
Enter city: Chennai

User registered successfully.
User Type  : Free User
Full Name  : Mega
Email      : mega@example.com
Phone      : 9876543210
City       : Chennai
User Id    : USR-AB12CD34

Do you want to register another user? (yes/no): no
```

```text
====================================
 Welcome to MyContacts App
====================================
1. User Registration
2. User Login
3. Exit
Enter choice: 2

Select authentication method:
1. Basic Authentication
2. OAuth Authentication
Enter choice: 1
Enter email: mega@example.com
Enter password: Mega123

Welcome, Mega

1. View Profile
2. Update Profile
3. Change Password
4. Manage Preferences
5. Create Contact
6. View Contact Details
7. Edit Contact
8. Undo Last Contact Edit
9. Redo Last Contact Edit
10. Delete Contact
11. Bulk Operations
12. Search Contacts
13. Advanced Filtering
14. Apply Tags To Contacts
15. Logout
16. Exit
Enter choice: 5

Select contact type:
1. Person Contact
2. Organization Contact
Enter choice: 1
Enter contact name: Teena
Enter phone number: 9876543210
Do you want to add another phone number? (yes/no): no
Enter email address: teena@gmail.com
Do you want to add another email address? (yes/no): no
Enter address (optional): Chennai
Enter notes (optional): Friend

Contact created successfully.
Contact Type : Person Contact
Reference Id : MEG1
Contact Id   : 123e4567-e89b-12d3-a456-426614174000
Name         : Teena
Phones       : 9876543210
Emails       : teena@gmail.com
Address      : Chennai
Notes        : Friend
Created At   : 2025-01-01 10:00:00
```
