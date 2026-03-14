# MyContacts App - Advanced OOPS

### Use Case 2 - User Authentication

This project is a Java console application built step by step using object-oriented programming concepts, design patterns and core Java features.

This use case allows a registered user to log in and access the contact list module.

#### Design Patterns

- Strategy Pattern to support different authentication methods
- Singleton Pattern for session handling

#### WorkFlow Example

```text
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
```

Output

```
User registered successfully.
User Type  : Free User
Full Name  : Mega
Email      : mega@example.com
Phone      : 9876543210
City       : Chennai
User Id    : USR-E1A2F0D8
```

```text
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
```

Output

```text
Welcome, Mega
1. View Profile
2. Logout
3. Exit
```
