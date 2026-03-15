# MyContacts App - Advanced OOPS

### Use Case 4 - Create Contact

This project is a Java console application built step by step using object-oriented programming concepts, design patterns and core Java features.

This use case allows a logged-in user to create a new contact with multiple phone numbers, multiple email addresses and optional fields.

#### Design Patterns

- Builder Pattern for contact construction
- Factory Pattern for creating contact types

#### WorkFlow Example

```text
1. View Profile
2. Update Profile
3. Change Password
4. Manage Preferences
5. Create Contact
6. Logout
7. Exit
Enter choice: 5

Select contact type:
1. Person Contact
2. Organization Contact
Enter choice: 1
Enter contact name: Teena
Enter phone number: 9876543210
Do you want to add another phone number? (yes/no): yes
Enter phone number: 9123456780
Do you want to add another phone number? (yes/no): no
Enter email address: teena@gmail.com
Do you want to add another email address? (yes/no): no
Enter address (optional): 
Enter notes (optional): College friend
```

Output

```text
Contact created successfully.
Contact Type : Person Contact
Reference Id : MEG1
Contact Id   : 63a966d0-3b62-42af-881b-6bec4d39677a
Name         : Teena
Phones       : 9876543210, 9123456780
Emails       : teena@gmail.com
Address      : Chennai
Notes        : College friend
Created At   : 2025-01-01 10:00:00
```