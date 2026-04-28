package arcade;

import java.util.ArrayList;

/**
 * ArcadeManager.java
 * Main collection manager for arcade records.
 */
public class ArcadeManager implements Manageable {
    private ArrayList<Customer> customers;
    private ArrayList<ArcadeMachine> machines;
    private ArrayList<Membership> memberships;
    private ArrayList<PlaySession> sessions;
    private ArrayList<Payment> payments;
    private ArrayList<User> users;
    private User currentUser;

    public ArcadeManager() {
        customers = new ArrayList<Customer>();
        machines = new ArrayList<ArcadeMachine>();
        memberships = new ArrayList<Membership>();
        sessions = new ArrayList<PlaySession>();
        payments = new ArrayList<Payment>();
        users = new ArrayList<User>();
        currentUser = null;
        loadAllData();
    }

    public void loadAllData() {
        loadUsers();
        loadCustomers();
        loadMachines();
        loadMemberships();
        loadSessions();
        loadPayments();
    }

    public void saveAllData() {
        saveUsers();
        saveCustomers();
        saveMachines();
        saveMemberships();
        saveSessions();
        savePayments();
    }

    private void loadUsers() {
        for (String line : FileManager.readLines("users.txt")) {
            users.add(User.fromFileString(line));
        }

        createDefaultAccountsIfNeeded();
    }

    private void createDefaultAccountsIfNeeded() {
        boolean hasOwner = false;
        boolean hasManager = false;
        boolean hasSupervisor = false;

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase("owner")) {
                hasOwner = true;
            }

            if (user.getEmail().equalsIgnoreCase("manager")) {
                hasManager = true;
            }

            if (user.getEmail().equalsIgnoreCase("supervisor")) {
                hasSupervisor = true;
            }
        }

        if (!hasOwner) {
            users.add(new User(getNextUserId(), "owner",
                    EncryptionUtil.encrypt("owner123"), "Owner"));
        }

        if (!hasManager) {
            users.add(new User(getNextUserId(), "manager",
                    EncryptionUtil.encrypt("manager123"), "Manager"));
        }

        if (!hasSupervisor) {
            users.add(new User(getNextUserId(), "supervisor",
                    EncryptionUtil.encrypt("supervisor123"), "Supervisor"));
        }

        if (!hasOwner || !hasManager || !hasSupervisor) {
            saveUsers();
        }
    }

    private String formatRole(String role) {
        if (role == null) {
            return "";
        }

        if (role.equalsIgnoreCase("Owner")) {
            return "Owner";
        }
        else if (role.equalsIgnoreCase("Manager")) {
            return "Manager";
        }
        else if (role.equalsIgnoreCase("Supervisor")) {
            return "Supervisor";
        }
        else if (role.equalsIgnoreCase("Employee")) {
            return "Employee";
        }

        return "";
    }

    public boolean canCurrentUserManageRole(String targetRole) {
        if (currentUser == null) {
            return false;
        }

        String currentRole = currentUser.getRole();
        String formattedTargetRole = formatRole(targetRole);

        if (formattedTargetRole.isEmpty()) {
            return false;
        }

        if (currentRole.equalsIgnoreCase("Owner")) {
            return true;
        }
        else if (currentRole.equalsIgnoreCase("Manager")) {
            return formattedTargetRole.equalsIgnoreCase("Supervisor")
                    || formattedTargetRole.equalsIgnoreCase("Employee");
        }
        else if (currentRole.equalsIgnoreCase("Supervisor")) {
            return formattedTargetRole.equalsIgnoreCase("Employee");
        }

        return false;
    }

    public boolean addEmployee(String username, String password, String role) {
        if (username.isBlank() || password.isBlank() || role.isBlank()) {
            return false;
        }

        String formattedRole = formatRole(role);

        if (formattedRole.isEmpty()) {
            return false;
        }

        if (!canCurrentUserManageRole(formattedRole)) {
            return false;
        }

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(username)) {
                return false;
            }
        }

        users.add(new User(getNextUserId(), username,
                EncryptionUtil.encrypt(password), formattedRole));
        saveUsers();
        return true;
    }


    public boolean updateEmployeeRole(int userId, String newRole) {
        String formattedRole = formatRole(newRole);

        if (formattedRole.isEmpty()) {
            return false;
        }

        if (!canCurrentUserManageRole(formattedRole)) {
            return false;
        }

        for (User user : users) {
            if (user.getUserId() == userId) {
                if (!canCurrentUserManageRole(user.getRole())) {
                    return false;
                }

                user.setRole(formattedRole);
                saveUsers();
                return true;
            }
        }

        return false;
    }

    public boolean deleteEmployee(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                if (!canCurrentUserManageRole(user.getRole())) {
                    return false;
                }

                users.remove(user);
                saveUsers();
                return true;
            }
        }

        return false;
    }

    public boolean canCurrentUserManageEmployees() {
        return currentUser != null
                && (currentUser.getRole().equalsIgnoreCase("Owner")
                || currentUser.getRole().equalsIgnoreCase("Manager")
                || currentUser.getRole().equalsIgnoreCase("Supervisor"));
    }

    private void saveUsers() {
        ArrayList<String> lines = new ArrayList<String>();

        for (User user : users) {
            lines.add(user.toFileString());
        }

        FileManager.writeLines("users.txt", lines);
    }

    private void loadCustomers() {
        for (String line : FileManager.readLines("customers.txt")) {
            customers.add(Customer.fromFileString(line));
        }
    }

    private void saveCustomers() {
        ArrayList<String> lines = new ArrayList<String>();

        for (Customer customer : customers) {
            lines.add(customer.toFileString());
        }

        FileManager.writeLines("customers.txt", lines);
    }

    private void loadMachines() {
        for (String line : FileManager.readLines("machines.txt")) {
            machines.add(ArcadeMachine.fromFileString(line));
        }
    }

    private void saveMachines() {
        ArrayList<String> lines = new ArrayList<String>();

        for (ArcadeMachine machine : machines) {
            lines.add(machine.toFileString());
        }

        FileManager.writeLines("machines.txt", lines);
    }

    private void loadMemberships() {
        for (String line : FileManager.readLines("memberships.txt")) {
            memberships.add(Membership.fromFileString(line));
        }
    }

    private void saveMemberships() {
        ArrayList<String> lines = new ArrayList<String>();

        for (Membership membership : memberships) {
            lines.add(membership.toFileString());
        }

        FileManager.writeLines("memberships.txt", lines);
    }

    private void loadSessions() {
        for (String line : FileManager.readLines("sessions.txt")) {
            sessions.add(PlaySession.fromFileString(line));
        }
    }

    private void saveSessions() {
        ArrayList<String> lines = new ArrayList<String>();

        for (PlaySession session : sessions) {
            lines.add(session.toFileString());
        }

        FileManager.writeLines("sessions.txt", lines);
    }

    private void loadPayments() {
        for (String line : FileManager.readLines("payments.txt")) {
            payments.add(Payment.fromFileString(line));
        }
    }

    private void savePayments() {
        ArrayList<String> lines = new ArrayList<String>();

        for (Payment payment : payments) {
            lines.add(payment.toFileString());
        }

        FileManager.writeLines("payments.txt", lines);
    }

    public boolean registerUser(String username, String password) {
        if (username.isBlank() || password.isBlank()) {
            return false;
        }

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(username)) {
                return false;
            }
        }

        User user = new User(getNextUserId(), username,
                EncryptionUtil.encrypt(password), "Employee");
        users.add(user);
        saveUsers();
        return true;
    }

    public boolean loginUser(String email, String password) {
        String encrypted = EncryptionUtil.encrypt(password);

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)
                    && user.getEncryptedPassword().equals(encrypted)) {
                currentUser = user;
                return true;
            }
        }

        currentUser = null;
        return false;
    }

    public void logoutUser() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private int getNextUserId() {
        int max = 0;

        for (User user : users) {
            if (user.getUserId() > max) {
                max = user.getUserId();
            }
        }

        return max + 1;
    }

    public int getNextCustomerId() {
        int max = 1000;

        for (Customer customer : customers) {
            if (customer.getId() > max) {
                max = customer.getId();
            }
        }

        return max + 1;
    }

    public int getNextMachineId() {
        int max = 2000;

        for (ArcadeMachine machine : machines) {
            if (machine.getMachineId() > max) {
                max = machine.getMachineId();
            }
        }

        return max + 1;
    }

    public int getNextSessionId() {
        int max = 3000;

        for (PlaySession session : sessions) {
            if (session.getSessionId() > max) {
                max = session.getSessionId();
            }
        }

        return max + 1;
    }

    public int getNextPaymentId() {
        int max = 4000;

        for (Payment payment : payments) {
            if (payment.getPaymentId() > max) {
                max = payment.getPaymentId();
            }
        }

        return max + 1;
    }

    public int getNextMembershipId() {
        int max = 5000;

        for (Membership membership : memberships) {
            if (membership.getMembershipId() > max) {
                max = membership.getMembershipId();
            }
        }

        return max + 1;
    }

    @Override
    public void addRecord(Object record) {
        if (record instanceof Customer) {
            customers.add((Customer) record);
            saveCustomers();
        } else if (record instanceof ArcadeMachine) {
            machines.add((ArcadeMachine) record);
            saveMachines();
        } else if (record instanceof PlaySession) {
            sessions.add((PlaySession) record);
            saveSessions();
        } else if (record instanceof Payment) {
            payments.add((Payment) record);
            savePayments();
        } else if (record instanceof Membership) {
            memberships.add((Membership) record);
            saveMemberships();
        }
    }

    @Override
    public boolean updateRecord(int id, Object record) {
        if (record instanceof Customer) {
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId() == id) {
                    customers.set(i, (Customer) record);
                    saveCustomers();
                    return true;
                }
            }
        } else if (record instanceof ArcadeMachine) {
            for (int i = 0; i < machines.size(); i++) {
                if (machines.get(i).getMachineId() == id) {
                    machines.set(i, (ArcadeMachine) record);
                    saveMachines();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean deleteRecord(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                customers.remove(customer);
                saveCustomers();
                return true;
            }
        }

        for (ArcadeMachine machine : machines) {
            if (machine.getMachineId() == id) {
                machines.remove(machine);
                saveMachines();
                return true;
            }
        }

        return false;
    }

    @Override
    public Object searchRecord(String keyword) {
        for (Customer customer : customers) {
            if (customer.getFullName().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                return customer;
            }
        }

        for (ArcadeMachine machine : machines) {
            if (machine.getGameName().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                return machine;
            }
        }

        return null;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<ArcadeMachine> getMachines() {
        return machines;
    }

    public ArrayList<Membership> getMemberships() {
        return memberships;
    }

    public ArrayList<PlaySession> getSessions() {
        return sessions;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean isValidCustomerId(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public boolean isValidMachineId(int id) {
        for (ArcadeMachine machine : machines) {
            if (machine.getMachineId() == id) {
                return true;
            }
        }

        return false;
    }
}
