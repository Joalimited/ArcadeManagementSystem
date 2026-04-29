Arcade Management System

Chosen Domain:
Arcade business management system.

Summary:
This JavaFX project allows users to register, log in, and manage arcade
records. The system supports customers, arcade machines, play sessions,
memberships, and payments and an employee management system.

Main Features:
- Register and login system
- Email validation with regex
- Simple password encryption
- Add, view, update, delete, and search records
- JavaFX GUI with tabs, buttons, labels, text fields, alerts, list views,
  check boxes, VBox, and GridPane
- File handling using text files
- ArrayLists for record storage
- Employee management system
- Try-catch and if-statement validation

Class Design:
- Person is an abstract class.
- Customer extends Person.
- Manageable is an interface.
- ArcadeManager implements Manageable.
- User contains a static user count and static method.
- FileManager handles file input and output.
- EncryptionUtil encrypts passwords.

Inheritance:
Customer inherits from Person.

Polymorphism:
Customer overrides getRole() from Person.
ArcadeManager also accepts Object records and processes different record
types using instanceof.

Abstract Class:
Person is abstract.

Interface:
Manageable is used for CRUD method requirements.

Static Members:
User has a static userCount attribute and getUserCount() method.

Authentication:
Users register with a unique email. The email is checked using a regex.
Passwords are encrypted by reversing the password and shifting each
character by 3 before saving.

File Handling:
Data is saved in the data folder:
- users.txt
- customers.txt
- machines.txt
- memberships.txt
- sessions.txt
- payments.txt

How to Run:
1. Open the folder in an IDE that supports JavaFX.
2. Make sure you have JavaFX installed and added to the project libraries.
3. Run arcade.ArcadeApp.
4. Register a user first, then log in.
