# MyContacts App - Advanced OOPS

### Use Case 10 - Advanced Filtering

This project is a Java console application built step by step using object-oriented programming concepts, design patterns and core Java features.

This use case allows a logged-in user to apply multiple filters like tag, date added and frequently contacted, then sort the result.

#### Design Patterns

- Composite Pattern for combining filters
- Strategy Pattern for different sorting algorithms

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
12. Search Contacts
13. Advanced Filtering
14. Logout
15. Exit
Enter choice: 13

Advanced Filtering
Enter tag to filter (leave blank to skip): TeamA
Enter date added in yyyy-MM-dd (leave blank to skip):
Enter minimum frequency count (leave blank to skip): 1

Select sort option:
1. Name
2. Date Added
3. Frequently Contacted
Enter choice: 3
```

Output

```text
Filtered Contacts
Reference Id : MEG1
Contact Type : Person Contact
Name         : Teena
Phones       : 9876543210
Emails       : teena@gmail.com
Tags         : TeamA
Address      : Chennai
Notes        : Friend
Created At   : 2025-01-01 10:00:00
Updated At   : 2025-01-01 10:00:00
Frequency    : 2
```
