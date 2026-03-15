# MyContacts App - Advanced OOPS

### Use Case 6 - Edit Contact

This project is a Java console application built step by step using object-oriented programming concepts, design patterns and core Java features.

This use case allows a logged-in user to modify an existing contact and use undo or redo on the latest edits.

#### Design Patterns

- Command Pattern for undo and redo operations
- Memento Pattern for preserving contact state

#### WorkFlow Example

```text
1. View Profile
2. Update Profile
3. Change Password
4. Manage Preferences
5. Create Contact
6. View Contact Details
7. Edit Contact
8. Undo Last Contact Edit
9. Redo Last Contact Edit
10. Logout
11. Exit
Enter choice: 7

Available Contact Ids
MEG1 - Teena

Enter reference id: MEG1

What do you want to edit?
1. Name
2. Phone Numbers
3. Email Addresses
4. Address
5. Notes
6. Back
Enter choice: 5
Enter notes (optional): School Friend
```

Output

```text
Contact notes updated successfully.

Enter choice: 8
Last contact edit undone successfully.

Enter choice: 9
Last contact edit redone successfully.
```
