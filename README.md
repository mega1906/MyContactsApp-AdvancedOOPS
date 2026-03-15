# MyContacts App - Advanced OOPS

### Use Case 8 - Bulk Operations

This project is a Java console application built step by step using object-oriented programming concepts, design patterns and core Java features.

This use case allows a logged-in user to perform delete, tag and export operations on multiple contacts at once.

#### Design Patterns

- Composite Pattern for treating one or many selected contacts the same way

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
10. Delete Contact
11. Bulk Operations
12. Logout
13. Exit
Enter choice: 11

Available Contact Ids
MEG1 - Teena
MEG2 - Tulsee
Enter reference ids separated by comma or ALL: MEG1, MEG2

Select bulk operation:
1. Bulk Delete
2. Bulk Tag
3. Bulk Export
Enter choice: 2
Enter tag: TeamA
```

Output

```text
Tag added to 2 contact(s).
```
