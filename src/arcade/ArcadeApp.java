package arcade;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ArcadeApp.java
 * Main JavaFX application for the Arcade Management System.
 */
public class ArcadeApp extends Application
{
    private ArcadeManager manager;
    private Stage mainStage;

    private ListView<Customer> customerList;
    private ListView<ArcadeMachine> machineList;
    private ListView<PlaySession> sessionList;
    private ListView<Payment> paymentList;
    private ListView<Membership> membershipList;
    private ListView<User> employeeList;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        manager = new ArcadeManager();
        mainStage = stage;
        showLoginScreen();
    }

    private void showLoginScreen()
    {
        Label titleLabel = new Label("Arcade Management System Login");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        loginButton.setOnAction(event ->
        {
            if(manager.loginUser(usernameField.getText(), passwordField.getText()))
            {
                showMainScreen();
            }
            else
            {
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        registerButton.setOnAction(event ->
        {
            boolean success = manager.registerUser(usernameField.getText(),
                    passwordField.getText());

            if(success)
            {
                showAlert("Registration Complete",
                        "Account created. You may now log in.");
            }
            else
            {
                showAlert("Registration Failed",
                        "Username may be blank or already used.");
            }
        });

        VBox layout = new VBox(10, titleLabel, usernameField, passwordField,
                 loginButton, registerButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-font-size: 14px; -fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 400, 300);
        mainStage.setTitle("Arcade Login");
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void showMainScreen()
    {
        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(event ->
        {
            manager.logoutUser();
            showLoginScreen();
        });

        TabPane tabPane = new TabPane();

        Tab customerTab = new Tab("Customers", createCustomerPane());
        Tab machineTab = new Tab("Machines", createMachinePane());
        Tab sessionTab = new Tab("Play Sessions", createSessionPane());
        Tab paymentTab = new Tab("Payments", createPaymentPane());
        Tab membershipTab = new Tab("Memberships", createMembershipPane());

        customerTab.setClosable(false);
        machineTab.setClosable(false);
        sessionTab.setClosable(false);
        paymentTab.setClosable(false);
        membershipTab.setClosable(false);

        tabPane.getTabs().addAll(customerTab, machineTab, sessionTab,
                paymentTab, membershipTab);

        if(manager.canCurrentUserManageEmployees())
        {
            Tab employeeTab = new Tab("Employees", createEmployeePane());
            employeeTab.setClosable(false);
            tabPane.getTabs().add(employeeTab);
        }

        VBox layout = new VBox(10, logoutButton, tabPane);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 800, 600);
        mainStage.setTitle("Arcade Management System");
        mainStage.setScene(scene);
    }
    private VBox createCustomerPane()
    {
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        TextField membershipField = new TextField();
        TextField searchField = new TextField();

        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        phoneField.setPromptText("Phone");
        emailField.setPromptText("Email");
        membershipField.setPromptText("Membership ID");
        searchField.setPromptText("Search by name");

        Button addButton = new Button("Add Customer");
        Button updateButton = new Button("Update Selected");
        Button deleteButton = new Button("Delete Selected");
        Button searchButton = new Button("Search");

        customerList = new ListView<Customer>();
        refreshCustomerList();

        addButton.setOnAction(event ->
        {
            try
            {
                int id = manager.getNextCustomerId();
                int membershipId = Integer.parseInt(membershipField.getText());

                Customer customer = new Customer(id, firstNameField.getText(),
                        lastNameField.getText(), phoneField.getText(),
                        emailField.getText(), membershipId);

                manager.addRecord(customer);
                refreshCustomerList();
                clear(firstNameField, lastNameField, phoneField, emailField,
                        membershipField);
            }
            catch(Exception e)
            {
                showAlert("Input Error", "Check the customer fields.");
            }
        });

        updateButton.setOnAction(event ->
        {
            Customer selected = customerList.getSelectionModel()
                    .getSelectedItem();

            if(selected != null)
            {
                try
                {
                    Customer updated = new Customer(selected.getId(),
                            firstNameField.getText(), lastNameField.getText(),
                            phoneField.getText(), emailField.getText(),
                            Integer.parseInt(membershipField.getText()));

                    manager.updateRecord(selected.getId(), updated);
                    refreshCustomerList();
                }
                catch(Exception e)
                {
                    showAlert("Input Error", "Check the customer fields.");
                }
            }
        });

        deleteButton.setOnAction(event ->
        {
            Customer selected = customerList.getSelectionModel()
                    .getSelectedItem();

            if(selected != null)
            {
                manager.deleteRecord(selected.getId());
                refreshCustomerList();
            }
        });

        searchButton.setOnAction(event ->
        {
            Object result = manager.searchRecord(searchField.getText());

            if(result instanceof Customer)
            {
                customerList.getSelectionModel().select((Customer)result);
            }
            else
            {
                showAlert("Search", "No customer found.");
            }
        });

        customerList.setOnMouseClicked(event ->
        {
            Customer selected = customerList.getSelectionModel()
                    .getSelectedItem();

            if(selected != null)
            {
                firstNameField.setText(selected.getFirstName());
                lastNameField.setText(selected.getLastName());
                phoneField.setText(selected.getPhone());
                emailField.setText(selected.getEmail());
                membershipField.setText("" + selected.getMembershipId());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("First:"), 0, 0);
        form.add(firstNameField, 1, 0);
        form.add(new Label("Last:"), 0, 1);
        form.add(lastNameField, 1, 1);
        form.add(new Label("Phone:"), 0, 2);
        form.add(phoneField, 1, 2);
        form.add(new Label("Email:"), 0, 3);
        form.add(emailField, 1, 3);
        form.add(new Label("Membership:"), 0, 4);
        form.add(membershipField, 1, 4);

        VBox layout = new VBox(10, form, addButton, updateButton,
                deleteButton, searchField, searchButton, customerList);
        layout.setPadding(new Insets(15));
        return layout;
    }

    private VBox createMachinePane()
    {
        TextField nameField = new TextField();
        TextField typeField = new TextField();
        TextField statusField = new TextField();
        TextField costField = new TextField();
        TextField searchField = new TextField();

        nameField.setPromptText("Game Name");
        typeField.setPromptText("Machine Type");
        statusField.setPromptText("Status");
        costField.setPromptText("Cost Per Play");
        searchField.setPromptText("Search by game name");

        Button addButton = new Button("Add Machine");
        Button updateButton = new Button("Update Selected");
        Button deleteButton = new Button("Delete Selected");
        Button searchButton = new Button("Search");

        machineList = new ListView<ArcadeMachine>();
        refreshMachineList();

        addButton.setOnAction(event ->
        {
            try
            {
                ArcadeMachine machine = new ArcadeMachine(
                        manager.getNextMachineId(), nameField.getText(),
                        typeField.getText(), statusField.getText(),
                        Double.parseDouble(costField.getText()));

                manager.addRecord(machine);
                refreshMachineList();
                clear(nameField, typeField, statusField, costField);
            }
            catch(Exception e)
            {
                showAlert("Input Error", "Check the machine fields.");
            }
        });

        updateButton.setOnAction(event ->
        {
            ArcadeMachine selected = machineList.getSelectionModel()
                    .getSelectedItem();

            if(selected != null)
            {
                try
                {
                    ArcadeMachine updated = new ArcadeMachine(
                            selected.getMachineId(), nameField.getText(),
                            typeField.getText(), statusField.getText(),
                            Double.parseDouble(costField.getText()));

                    manager.updateRecord(selected.getMachineId(), updated);
                    refreshMachineList();
                }
                catch(Exception e)
                {
                    showAlert("Input Error", "Check the machine fields.");
                }
            }
        });

        deleteButton.setOnAction(event ->
        {
            ArcadeMachine selected = machineList.getSelectionModel()
                    .getSelectedItem();

            if(selected != null)
            {
                manager.deleteRecord(selected.getMachineId());
                refreshMachineList();
            }
        });

        searchButton.setOnAction(event ->
        {
            Object result = manager.searchRecord(searchField.getText());

            if(result instanceof ArcadeMachine)
            {
                machineList.getSelectionModel().select((ArcadeMachine)result);
            }
            else
            {
                showAlert("Search", "No machine found.");
            }
        });

        machineList.setOnMouseClicked(event ->
        {
            ArcadeMachine selected = machineList.getSelectionModel()
                    .getSelectedItem();

            if(selected != null)
            {
                nameField.setText(selected.getGameName());
                typeField.setText(selected.getMachineType());
                statusField.setText(selected.getStatus());
                costField.setText("" + selected.getCostPerPlay());
            }
        });

        VBox layout = new VBox(10, nameField, typeField, statusField,
                costField, addButton, updateButton, deleteButton,
                searchField, searchButton, machineList);
        layout.setPadding(new Insets(15));
        return layout;
    }

    private VBox createSessionPane()
    {
        TextField customerIdField = new TextField();
        TextField machineIdField = new TextField();
        TextField playsField = new TextField();
        TextField costField = new TextField();

        customerIdField.setPromptText("Customer ID");
        machineIdField.setPromptText("Machine ID");
        playsField.setPromptText("Number of Plays");
        costField.setPromptText("Total Cost");

        Button addButton = new Button("Add Play Session");
        sessionList = new ListView<PlaySession>();
        refreshSessionList();

        addButton.setOnAction(event ->
        {
            try
            {
                int customerId = Integer.parseInt(customerIdField.getText());
                int machineId = Integer.parseInt(machineIdField.getText());

                if(!manager.isValidCustomerId(customerId))
                {
                    showAlert("Invalid Customer ID",
                            "Customer ID does not exist.");
                    return;
                }

                if(!manager.isValidMachineId(machineId))
                {
                    showAlert("Invalid Machine ID",
                            "Machine ID does not exist.");
                    return;
                }

                PlaySession session = new PlaySession(
                        manager.getNextSessionId(),
                        customerId,
                        machineId,
                        Integer.parseInt(playsField.getText()),
                        Double.parseDouble(costField.getText()));

                manager.addRecord(session);
                refreshSessionList();
                clear(customerIdField, machineIdField, playsField, costField);
            }
            catch(Exception e)
            {
                showAlert("Input Error", "Check the play session fields.");
            }
        });

        VBox layout = new VBox(10, customerIdField, machineIdField,
                playsField, costField, addButton, sessionList);
        layout.setPadding(new Insets(15));
        return layout;
    }

    private VBox createPaymentPane()
    {
        TextField customerIdField = new TextField();
        TextField amountField = new TextField();
        TextField methodField = new TextField();
        TextField statusField = new TextField();

        customerIdField.setPromptText("Customer ID");
        amountField.setPromptText("Amount");
        methodField.setPromptText("Cash/Card");
        statusField.setPromptText("Paid/Pending");

        Button addButton = new Button("Add Payment");
        paymentList = new ListView<Payment>();
        refreshPaymentList();

        addButton.setOnAction(event ->
        {
            try
            {
                Payment payment = new Payment(manager.getNextPaymentId(),
                        Integer.parseInt(customerIdField.getText()),
                        Double.parseDouble(amountField.getText()),
                        methodField.getText(), statusField.getText());

                manager.addRecord(payment);
                refreshPaymentList();
                clear(customerIdField, amountField, methodField, statusField);
            }
            catch(Exception e)
            {
                showAlert("Input Error", "Check the payment fields.");
            }
        });

        VBox layout = new VBox(10, customerIdField, amountField, methodField,
                statusField, addButton, paymentList);
        layout.setPadding(new Insets(15));
        return layout;
    }

    private VBox createMembershipPane()
    {
        TextField customerIdField = new TextField();
        TextField typeField = new TextField();
        TextField pointsField = new TextField();
        TextField discountField = new TextField();

        customerIdField.setPromptText("Customer ID");
        typeField.setPromptText("Basic/Gold/VIP");
        pointsField.setPromptText("Points");
        discountField.setPromptText("Discount Rate");

        Button addButton = new Button("Add Membership");
        membershipList = new ListView<Membership>();
        refreshMembershipList();

        addButton.setOnAction(event ->
        {
            try
            {
                Membership membership = new Membership(
                        manager.getNextMembershipId(),
                        Integer.parseInt(customerIdField.getText()),
                        typeField.getText(),
                        Integer.parseInt(pointsField.getText()),
                        Double.parseDouble(discountField.getText()));

                manager.addRecord(membership);
                refreshMembershipList();
                clear(customerIdField, typeField, pointsField, discountField);
            }
            catch(Exception e)
            {
                showAlert("Input Error", "Check the membership fields.");
            }
        });

        VBox layout = new VBox(10, customerIdField, typeField, pointsField,
                discountField, addButton, membershipList);
        layout.setPadding(new Insets(15));
        return layout;
    }

    private VBox createEmployeePane()
    {
        Label infoLabel = new Label("Role rules: Owners manage everyone. Managers manage Supervisors and Employees. Supervisors manage Employees only.");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField roleField = new TextField();

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        roleField.setPromptText("Role: Owner, Manager, Supervisor, or Employee");

        Button addButton = new Button("Add Employee");
        Button updateRoleButton = new Button("Update Selected Position");
        Button deleteButton = new Button("Delete Selected Employee");

        employeeList = new ListView<User>();
        refreshEmployeeList();

        addButton.setOnAction(event ->
        {
            boolean added = manager.addEmployee(usernameField.getText(),
                    passwordField.getText(), roleField.getText());

            if(added)
            {
                refreshEmployeeList();
                clear(usernameField, passwordField, roleField);
                showAlert("Employee Added", "The employee account was added.");
            }
            else
            {
                showAlert("Input Error",
                        "Use a unique username and a role you are allowed to manage.");
            }
        });

        updateRoleButton.setOnAction(event ->
        {
            User selected = employeeList.getSelectionModel().getSelectedItem();

            if(selected != null)
            {
                boolean updated = manager.updateEmployeeRole(
                        selected.getUserId(), roleField.getText());

                if(updated)
                {
                    refreshEmployeeList();
                    clear(roleField);
                    showAlert("Position Updated",
                            "The employee position was updated.");
                }
                else
                {
                    showAlert("Permission Error",
                            "You are not allowed to update that position.");
                }
            }
            else
            {
                showAlert("Update Error", "Select an employee first.");
            }
        });

        deleteButton.setOnAction(event ->
        {
            User selected = employeeList.getSelectionModel().getSelectedItem();

            if(selected != null)
            {
                boolean deleted = manager.deleteEmployee(selected.getUserId());

                if(deleted)
                {
                    refreshEmployeeList();
                }
                else
                {
                    showAlert("Permission Error",
                            "You are not allowed to remove that role.");
                }
            }
            else
            {
                showAlert("Delete Error", "Select an employee first.");
            }
        });

        employeeList.setOnMouseClicked(event ->
        {
            User selected = employeeList.getSelectionModel().getSelectedItem();

            if(selected != null)
            {
                usernameField.setText(selected.getEmail());
                roleField.setText(selected.getRole());
            }
        });

        VBox layout = new VBox(10, infoLabel, usernameField, passwordField,
                roleField, addButton, updateRoleButton, deleteButton,
                employeeList);
        layout.setPadding(new Insets(15));
        return layout;
    }

    private void refreshCustomerList()
    {
        customerList.setItems(FXCollections.observableArrayList(
                manager.getCustomers()));
    }

    private void refreshMachineList()
    {
        machineList.setItems(FXCollections.observableArrayList(
                manager.getMachines()));
    }

    private void refreshSessionList()
    {
        sessionList.setItems(FXCollections.observableArrayList(
                manager.getSessions()));
    }

    private void refreshPaymentList()
    {
        paymentList.setItems(FXCollections.observableArrayList(
                manager.getPayments()));
    }

    private void refreshMembershipList()
    {
        membershipList.setItems(FXCollections.observableArrayList(
                manager.getMemberships()));
    }

    private void refreshEmployeeList()
    {
        employeeList.setItems(FXCollections.observableArrayList(
                manager.getUsers()));
    }

    private void clear(TextField... fields)
    {
        for(TextField field : fields)
        {
            field.clear();
        }
    }

    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
