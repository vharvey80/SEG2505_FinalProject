package familytaskmanager.microso.com.familytaskmanager;

import java.util.*;
import java.sql.Date;

public class Family {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Family Attributes
    private int id;

    //Family Associations
    private List<Tool> tools;
    private List<User> users;
    private List<ShoppingItem> shoppingItems;
    private List<Task> tasks;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Family(int aId, User... allUsers) {
        id = aId;
        tools = new ArrayList<Tool>();
        users = new ArrayList<User>();
        boolean didAddUsers = setUsers(allUsers);
        if (!didAddUsers) {
            throw new RuntimeException("Unable to create Family, must have at least 1 users");
        }
        shoppingItems = new ArrayList<ShoppingItem>();
        tasks = new ArrayList<Task>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setId(int aId) {
        boolean wasSet = false;
        id = aId;
        wasSet = true;
        return wasSet;
    }

    public int getId() {
        return id;
    }

    public Tool getTool(int index) {
        Tool aTool = tools.get(index);
        return aTool;
    }

    public List<Tool> getTools() {
        List<Tool> newTools = Collections.unmodifiableList(tools);
        return newTools;
    }

    public int numberOfTools() {
        int number = tools.size();
        return number;
    }

    public boolean hasTools() {
        boolean has = tools.size() > 0;
        return has;
    }

    public int indexOfTool(Tool aTool) {
        int index = tools.indexOf(aTool);
        return index;
    }

    public User getUser(int index) {
        User aUser = users.get(index);
        return aUser;
    }

    public List<User> getUsers() {
        List<User> newUsers = Collections.unmodifiableList(users);
        return newUsers;
    }

    public int numberOfUsers() {
        int number = users.size();
        return number;
    }

    public boolean hasUsers() {
        boolean has = users.size() > 0;
        return has;
    }

    public int indexOfUser(User aUser) {
        int index = users.indexOf(aUser);
        return index;
    }

    public ShoppingItem getShoppingItem(int index) {
        ShoppingItem aShoppingItem = shoppingItems.get(index);
        return aShoppingItem;
    }

    public List<ShoppingItem> getShoppingItems() {
        List<ShoppingItem> newShoppingItems = Collections.unmodifiableList(shoppingItems);
        return newShoppingItems;
    }

    public int numberOfShoppingItems() {
        int number = shoppingItems.size();
        return number;
    }

    public boolean hasShoppingItems() {
        boolean has = shoppingItems.size() > 0;
        return has;
    }

    public int indexOfShoppingItem(ShoppingItem aShoppingItem) {
        int index = shoppingItems.indexOf(aShoppingItem);
        return index;
    }

    public Task getTask(int index) {
        Task aTask = tasks.get(index);
        return aTask;
    }

    public List<Task> getTasks() {
        List<Task> newTasks = Collections.unmodifiableList(tasks);
        return newTasks;
    }

    public int numberOfTasks() {
        int number = tasks.size();
        return number;
    }

    public boolean hasTasks() {
        boolean has = tasks.size() > 0;
        return has;
    }

    public int indexOfTask(Task aTask) {
        int index = tasks.indexOf(aTask);
        return index;
    }

    public static int minimumNumberOfTools() {
        return 0;
    }

    public boolean addTool(Tool aTool) {
        boolean wasAdded = false;
        if (tools.contains(aTool)) {
            return false;
        }
        tools.add(aTool);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeTool(Tool aTool) {
        boolean wasRemoved = false;
        if (tools.contains(aTool)) {
            tools.remove(aTool);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public boolean addToolAt(Tool aTool, int index) {
        boolean wasAdded = false;
        if (addTool(aTool)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTools()) {
                index = numberOfTools() - 1;
            }
            tools.remove(aTool);
            tools.add(index, aTool);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveToolAt(Tool aTool, int index) {
        boolean wasAdded = false;
        if (tools.contains(aTool)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTools()) {
                index = numberOfTools() - 1;
            }
            tools.remove(aTool);
            tools.add(index, aTool);
            wasAdded = true;
        } else {
            wasAdded = addToolAt(aTool, index);
        }
        return wasAdded;
    }

    public static int minimumNumberOfUsers() {
        return 1;
    }

    public boolean addUser(User aUser) {
        boolean wasAdded = false;
        if (users.contains(aUser)) {
            return false;
        }
        users.add(aUser);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeUser(User aUser) {
        boolean wasRemoved = false;
        if (!users.contains(aUser)) {
            return wasRemoved;
        }

        if (numberOfUsers() <= minimumNumberOfUsers()) {
            return wasRemoved;
        }

        users.remove(aUser);
        wasRemoved = true;
        return wasRemoved;
    }

    public boolean setUsers(User... newUsers) {
        boolean wasSet = false;
        ArrayList<User> verifiedUsers = new ArrayList<User>();
        for (User aUser : newUsers) {
            if (verifiedUsers.contains(aUser)) {
                continue;
            }
            verifiedUsers.add(aUser);
        }

        if (verifiedUsers.size() != newUsers.length || verifiedUsers.size() < minimumNumberOfUsers()) {
            return wasSet;
        }

        users.clear();
        users.addAll(verifiedUsers);
        wasSet = true;
        return wasSet;
    }

    public boolean addUserAt(User aUser, int index) {
        boolean wasAdded = false;
        if (addUser(aUser)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfUsers()) {
                index = numberOfUsers() - 1;
            }
            users.remove(aUser);
            users.add(index, aUser);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveUserAt(User aUser, int index) {
        boolean wasAdded = false;
        if (users.contains(aUser)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfUsers()) {
                index = numberOfUsers() - 1;
            }
            users.remove(aUser);
            users.add(index, aUser);
            wasAdded = true;
        } else {
            wasAdded = addUserAt(aUser, index);
        }
        return wasAdded;
    }

    public static int minimumNumberOfShoppingItems() {
        return 0;
    }

    public boolean addShoppingItem(ShoppingItem aShoppingItem) {
        boolean wasAdded = false;
        if (shoppingItems.contains(aShoppingItem)) {
            return false;
        }
        shoppingItems.add(aShoppingItem);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeShoppingItem(ShoppingItem aShoppingItem) {
        boolean wasRemoved = false;
        if (shoppingItems.contains(aShoppingItem)) {
            shoppingItems.remove(aShoppingItem);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public boolean addShoppingItemAt(ShoppingItem aShoppingItem, int index) {
        boolean wasAdded = false;
        if (addShoppingItem(aShoppingItem)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfShoppingItems()) {
                index = numberOfShoppingItems() - 1;
            }
            shoppingItems.remove(aShoppingItem);
            shoppingItems.add(index, aShoppingItem);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveShoppingItemAt(ShoppingItem aShoppingItem, int index) {
        boolean wasAdded = false;
        if (shoppingItems.contains(aShoppingItem)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfShoppingItems()) {
                index = numberOfShoppingItems() - 1;
            }
            shoppingItems.remove(aShoppingItem);
            shoppingItems.add(index, aShoppingItem);
            wasAdded = true;
        } else {
            wasAdded = addShoppingItemAt(aShoppingItem, index);
        }
        return wasAdded;
    }

    public static int minimumNumberOfTasks() {
        return 0;
    }

    public boolean addTask(Task aTask) {
        boolean wasAdded = false;
        if (tasks.contains(aTask)) {
            return false;
        }
        tasks.add(aTask);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeTask(Task aTask) {
        boolean wasRemoved = false;
        if (tasks.contains(aTask)) {
            tasks.remove(aTask);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public boolean addTaskAt(Task aTask, int index) {
        boolean wasAdded = false;
        if (addTask(aTask)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTasks()) {
                index = numberOfTasks() - 1;
            }
            tasks.remove(aTask);
            tasks.add(index, aTask);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveTaskAt(Task aTask, int index) {
        boolean wasAdded = false;
        if (tasks.contains(aTask)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTasks()) {
                index = numberOfTasks() - 1;
            }
            tasks.remove(aTask);
            tasks.add(index, aTask);
            wasAdded = true;
        } else {
            wasAdded = addTaskAt(aTask, index);
        }
        return wasAdded;
    }

    public void delete() {
        tools.clear();
        users.clear();
        shoppingItems.clear();
        tasks.clear();
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "]";
    }
}
