package familytaskmanager.microso.com.familytaskmanager;

import android.provider.ContactsContract;
import android.view.SoundEffectConstants;
import android.widget.Toast;

import java.util.*;
import java.sql.Date;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Family {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Family Attributes
    private int id;

    //Family Associations
    private List<Tool> tools;
    private List<User> users;
    private List<ShoppingItem> fridge;
    private List<ShoppingItem> shoppingItems;
    private List<Task> activeTasks;
    private List<Task> inactiveTasks;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference toolsReference;
    private DatabaseReference usersReference;
    private DatabaseReference shoppingItemsReference;
    private DatabaseReference activeTasksReference;
    private DatabaseReference inactiveTasksReference;
    private DatabaseReference currentUserReference;
    private DatabaseReference fridgeReference;

    private User currentUser;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Family(int aId) {
        id = aId;
        tools = new ArrayList<Tool>();
        users = new ArrayList<User>();
        fridge = new ArrayList<ShoppingItem>();
        //this user will always be replaced
        currentUser = new User("0", "Default", "User", true, "menu_people", 0);

        shoppingItems = new ArrayList<ShoppingItem>();
        activeTasks = new ArrayList<Task>();
        inactiveTasks = new ArrayList<Task>();

        toolsReference = database.getReference("Tools");
        fridgeReference = database.getReference("Fridge");
        usersReference = database.getReference("Users");
        shoppingItemsReference = database.getReference("ShoppingItems");
        activeTasksReference = database.getReference("ActiveTasks");
        inactiveTasksReference = database.getReference("InactiveTasks");
        currentUserReference = database.getReference("currentUser");

        //we need to get the current user from DB only once
        currentUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);

                //if there was no last user, i.e. on first install
                if (currentUser == null) {
                    User defaultCurrent = new User(null, "Current", "User", true, "menu_people", 10);
                    addCurrentUser(defaultCurrent);
                    currentUser = defaultCurrent;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                return;
            }
        });

    }

    private boolean addCurrentUser(User user) {
        boolean wasAdded = true;
        if (currentUser != null && user.getId() != null) {
            if(currentUser.getId().equals(user.getId())) {
                return  false;
            }
        }
        /** DATABASE CODE **/
            String user_id = currentUserReference.push().getKey();
            user.setId(user_id);
            currentUserReference.child(user_id).setValue(user);
            usersReference.child(user_id).setValue(user);
        /** END **/
        return wasAdded;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public boolean setCurrentUser(int userIndex) {
        User user = users.get(userIndex);
        boolean wasUpdated = false;
        DatabaseReference currentUser_del_ref;
        currentUser_del_ref = currentUserReference.child(currentUser.getId()); // get reference
        currentUser_del_ref.removeValue(); // delete the user
        currentUserReference.child(user.getId()).setValue(user); //write the new current user in database

        currentUser = user; //modify the attribute in family
        wasUpdated = true;
        return wasUpdated;
    }

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
        // TODO REMOVE List<Tool> newTools = Collections.unmodifiableList(tools);
        return tools;
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
        //TODO remove List<User> newUsers = Collections.unmodifiableList(users);
        return users;
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
        //TODO remove List<ShoppingItem> newShoppingItems = Collections.unmodifiableList(shoppingItems);
        return shoppingItems;
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

    public Task getActiveTask(int index) {
        Task aTask = activeTasks.get(index);
        return aTask;
    }

    public Task getInactiveTask(int index) {
        Task aTask = inactiveTasks.get(index);
        return aTask;
    }

    public List<Task> getActiveTasks() {
        //TODO REMOVE List<Task> newTasks = Collections.unmodifiableList(activeTasks);
        return activeTasks;
    }

    public List<Task> getInactiveTasks() {
        //TODO REMOVE List<Task> newTasks = Collections.unmodifiableList(inactiveTasks);
        return inactiveTasks;
    }

    public int numberOfTasks() {
        int number = activeTasks.size() + inactiveTasks.size();
        return number;
    } // Might change

    public boolean hasActiveTasks() {
        boolean has = activeTasks.size() > 0;
        return has;
    }

    public int indexOfTask(Task aTask) {
        int index = activeTasks.indexOf(aTask);
        return index;
    }

    public List<ShoppingItem> getFridge() {
        return fridge;
    }

    public static int minimumNumberOfTools() {
        return 0;
    }

    // Modified for the database.
    public boolean addTool(Tool aTool) {
        boolean wasAdded = false;
        for (Tool t : tools) {
            if (t.getId().equals(aTool.getId())) { return false; }
        }
        /** DATABASE CODE **/
            String tool_id = toolsReference.push().getKey();
            aTool.setId(tool_id);
            toolsReference.child(tool_id).setValue(aTool);
        /** END **/
        tools.add(aTool);
        wasAdded = true;
        return wasAdded;
    }

    // Modified for the database.
    public boolean removeTool(String aTool) {
        boolean wasRemoved = false;
        Tool toolToRemove = null;
        DatabaseReference tool_del_ref; // temporary reference
        for (Tool t : tools) {
            if (t.getId().equals(aTool)) {
                /** DATABASE CODE **/
                tool_del_ref = toolsReference.child(aTool); // get reference
                tool_del_ref.removeValue(); // delete the tool
                /** END **/
                wasRemoved = true;
                toolToRemove = t;
            }
        }
        if (toolToRemove != null) { tools.remove(toolToRemove); } else { throw new IllegalArgumentException("Impossible de deleter un tool inexistant."); }
        return wasRemoved;
    }

    public static int minimumNumberOfUsers() {
        return 1;
    }

    // Modified for databse.
    public boolean addUser(User aUser) {
        boolean wasAdded = false;
        if (users.contains(aUser)) {
            return false;
        }
        /** DATABASE CODE **/
            String user_id = usersReference.push().getKey();
            aUser.setId(user_id);
            usersReference.child(user_id).setValue(aUser);
        /** END **/
        users.add(aUser);
        wasAdded = true;
        return wasAdded;
    }

    // Modified for databse.
    public boolean removeUser(User aUser) {
        boolean wasRemoved = false;
        DatabaseReference user_del_ref; // temporary reference
        if (!users.contains(aUser)) {
            return wasRemoved;
        }

        if (numberOfUsers() <= minimumNumberOfUsers()) {
            return wasRemoved;
        }
        /** DATABASE CODE **/
            user_del_ref = usersReference.child(aUser.getId()); // get reference
            user_del_ref.removeValue(); // delete the tool
        /** END **/
        users.remove(aUser);
        wasRemoved = true;
        return wasRemoved;
    }

    public boolean updateUser(User aUser) {
        boolean wasUpdated = false;
        DatabaseReference user_update_ref;
        /*if (users.contains(aUser)) {
            return false;
        }*/
        /** DATABASE CODE **/
            user_update_ref = usersReference.child(aUser.getId());
            user_update_ref.setValue(aUser);
        /** END **/
        wasUpdated = true;
        return wasUpdated;
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

    public boolean addFridgeItem(ShoppingItem aFridgeItem) {
        boolean wasAdded = false;
        for (ShoppingItem s : fridge) {
            if (s.getId().equals(aFridgeItem.getId())) { return false; }
        }
        /** DATABASE CODE **/
        String fridge_id = fridgeReference.push().getKey();
        aFridgeItem.setId(fridge_id);
        fridgeReference.child(fridge_id).setValue(aFridgeItem);
        /** END **/
        fridge.add(aFridgeItem);
        wasAdded = true;
        return wasAdded;
    }

    // Modified for the database.
    public boolean removeFridgeItem(String aFridgeItem) {
        boolean wasRemoved = false;
        ShoppingItem itemToRemove = null;
        DatabaseReference fridge_del_ref; // temporary reference
        for (ShoppingItem s : fridge) {
            if (s.getId().equals(aFridgeItem)) {
                /** DATABASE CODE **/
                fridge_del_ref = fridgeReference.child(aFridgeItem); // get reference
                fridge_del_ref.removeValue(); // delete the tool
                /** END **/
                wasRemoved = true;
                itemToRemove = s;
            }
        }
        if (itemToRemove != null) { fridge.remove(itemToRemove); } else { throw new IllegalArgumentException("Impossible de supprimer un item inexistant."); }
        return wasRemoved;
    }

    public static int minimumNumberOfShoppingItems() {
        return 0;
    }

    public boolean addShoppingItem(ShoppingItem aShoppingItem) {
        boolean wasAdded = false;
        if (shoppingItems.contains(aShoppingItem)) {
            return false;
        }
        String shoppingItemID = shoppingItemsReference.push().getKey();
        aShoppingItem.setId(shoppingItemID);
        shoppingItemsReference.child(shoppingItemID).setValue(aShoppingItem);
        shoppingItems.add(aShoppingItem);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeShoppingItem(ShoppingItem aShoppingItem) {
        boolean found = false;
        DatabaseReference shoppingItem_del_ref;
        for(ShoppingItem s : shoppingItems) {
            if(s.getId() == aShoppingItem.getId()) {
                aShoppingItem = s;
                found = true;
                break;
            }
        }
        if (found) {
            shoppingItems.remove(aShoppingItem);
            shoppingItem_del_ref = shoppingItemsReference.child(aShoppingItem.getId()); // get reference
            shoppingItem_del_ref.removeValue(); // delete the item
        }
        return found;
    }

    public static int minimumNumberOfTasks() {
        return 0;
    }

    public boolean addTask(Task aTask) {
        boolean wasAdded = false;
        if (activeTasks.contains(aTask)) {
            return false;
        }
        //TODO Either we set state as "assigned" or we change State machine
        /** DATABASE CODE **/
            String task_id = activeTasksReference.push().getKey();
            aTask.setId(task_id);
            activeTasksReference.child(task_id).setValue(aTask);
        /** END **/
        activeTasks.add(aTask);
        wasAdded = true;
        return wasAdded;
    }

    public boolean addInactiveTask(Task aTask, boolean completeTask) {
        boolean wasAdded = false;

        for (Task t : inactiveTasks) {
            if (t.getId().equals(aTask.getId())) {
                return false;
            }
        }
        /** DATABASE CODE **/
        String task_id = inactiveTasksReference.push().getKey();
        aTask.setId(task_id);
        if (!completeTask)
            aTask.setState(Task.TaskState.Cancelled);
        else
            aTask.setState(Task.TaskState.Completed);
        inactiveTasksReference.child(task_id).setValue(aTask);
        /** END **/
        inactiveTasks.add(aTask);
        wasAdded = true;
        return wasAdded;
    }

    public boolean updateTask(Task aTask) {
        //TODO fix this method. Either have some kind of check to return true or false, or remove return of boolean
        boolean wasUpdated = false;
        DatabaseReference task_update_ref;

        /** DATABASE CODE **/
            task_update_ref = activeTasksReference.child(aTask.getId());
            task_update_ref.setValue(aTask);
        /** END **/
        wasUpdated = true;
        return wasUpdated;
    }

    public boolean removeTask(String aTask, boolean completeTask) {
        boolean wasRemoved = false;
        Task taskToRemove = null;
        DatabaseReference task_del_ref; // temporary reference
        for (Task t : activeTasks) {
            if (t.getId().equals(aTask)) {
                /** DATABASE CODE **/
                task_del_ref = activeTasksReference.child(aTask); // get reference
                task_del_ref.removeValue(); // delete the tool
                /** END **/
                wasRemoved = true;
                taskToRemove = t;
            }
        }
        if (taskToRemove != null) {
            if (activeTasks.remove(taskToRemove)) {
                this.addInactiveTask(taskToRemove, completeTask);
            }
        } else {
            throw new IllegalArgumentException("Impossible de deleter une task inexistante.");
        }
        return wasRemoved;
    }

    public void delete() {
        tools.clear();
        users.clear();
        fridge.clear();
        shoppingItems.clear();
        activeTasks.clear();
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "]";
    }

    public void onStartFamily() {

        currentUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Clearing the list
                currentUser = null;
                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each user
                    currentUser = postSnapshot.getValue(User.class);
                }
                //We always want at least one user in App
                if (currentUser == null) {
                    currentUser = new User("0", "Default", "User", true, "menu_people", 0);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Creating all the reference we need for the database.
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Clearing the list
                users.clear();

                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each user
                    User user = postSnapshot.getValue(User.class);

                    if(user.getTasks() == null) {
                        List<Task> tasks = new ArrayList<Task>();
                        user.setTasks(tasks);
                    }
                    if(user.getAssignedTo() == null) {
                        List<Task> assignedToTasks = new ArrayList<Task>();
                        user.setAssignedToList(assignedToTasks);
                    }

                    // Add user getted in the list
                    users.add(user);
                }

                //We always want at least one user in App
                if (users.isEmpty()) {
                    System.out.println("users empty xyz"); //TODO remove
                    String user_id = usersReference.push().getKey();
                    User defaultUser = new User(user_id, "Default", "User", true, "menu_people", 0);
                    usersReference.child(user_id).setValue(defaultUser);
                    users.add(defaultUser);
                }

                // Might be problems with the adapters... ?
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        toolsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clearing the list
                tools.clear();


                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each tool
                    Tool tool = postSnapshot.getValue(Tool.class);

                    // Add tool getted in the list
                    tools.add(tool);
                }

                // Might be problems with the adapters... ?
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fridgeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clearing the list
                fridge.clear();


                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each tool
                    ShoppingItem item = postSnapshot.getValue(ShoppingItem.class);

                    // Add item gotten in the list
                    fridge.add(item);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        shoppingItemsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clearing the list
                shoppingItems.clear();

                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each item
                    ShoppingItem shoppingItem = postSnapshot.getValue(ShoppingItem.class);

                    // Add shopping gotten in the list
                    shoppingItems.add(shoppingItem);
                }

                // Might be problems with the adapters... ?
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        activeTasksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clearing the list
                System.out.println("starting call to get data.......123456");
                activeTasks.clear();

                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each item
                    Task activeTask = postSnapshot.getValue(Task.class);

                    //We have a map for the users in Task class.
                    //So we create the map here and will fill it at end of DB loading

                    //Collections sometimes come back null from DB, which should not be case
                    //So here we check and correct
                    if (activeTask.getTools() == null) {
                        List<Tool> tools = new ArrayList<Tool>();
                        activeTask.setTools(tools);
                    }
                    //Also we need to make sur the map containing users is not null
                    activeTask.setUsers(new HashMap<String, User>());

                    // Add item getted in the list
                    activeTasks.add(activeTask);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        inactiveTasksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clearing the list
                inactiveTasks.clear();

                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each item
                    Task inactiveTask = postSnapshot.getValue(Task.class);

                    //TODO add security for null values coming from DB

                    // Add item getted in the list
                    inactiveTasks.add(inactiveTask);
                }

                // Might be problems with the adapters... ?
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }  // Initialize and load from DB.

    public boolean requestShoppingItemCreation(ShoppingItem newShoppingItem) {
        try {
            this.addShoppingItem(newShoppingItem);
            return true;
        } catch (Error e) {
            return false;
        }
    }

    public boolean requestShoppingItemDelete(ShoppingItem deletedItem) {
        try {
            this.removeShoppingItem(deletedItem);
            return true;
        } catch (Error e) { return false; }
    }

    public boolean requestToolCreation(Tool newTool) { // Method that allows us to add a new tool to the DB.
        try {
            this.addTool(newTool);
            return true;
        } catch (Error e) { return false; }
    }

    public boolean requestToolDelete(String deletedTool) {
        try {
            this.removeTool(deletedTool);
            return true;
        } catch (Error e) { return false; }
    }

    public boolean requestFridgeItemCreation(ShoppingItem newShoppingItem) { // Method that allows us to add a new tool to the DB.
        try {
            this.addFridgeItem(newShoppingItem);
            return true;
        } catch (Error e) { return false; }
    }

    public boolean requestFridgeItemDelete(String deletedShoppingItem) {
        try {
            this.removeFridgeItem(deletedShoppingItem);
            return true;
        } catch (Error e) { return false; }
    }

    public boolean requestTaskDelete(String deletedTask, boolean completeTask) {
        boolean removedDependency = true;
        try {
            // TODO TASK SUPRESSION : Be sure that the removeAssignedTask I've created is working when a user has some tasks to do.
            for (User u : users) {
                if (u.getAssignedTo() != null) {
                    for (Task t : u.getAssignedTo()) {
                        if (t.getId().equals(deletedTask)) {
                            removedDependency = u.removeAssignedTask(deletedTask);
                            break;
                        }
                    }
                }
            }
            if(removedDependency) {
                this.removeTask(deletedTask, completeTask);
            }
            return true;
        } catch (Error e) { throw new DatabaseException("Impossible to delete this item."); }
    }

    public Task requestTaskCreation(User creator, String name, double time, int year, int month,
                                    int day, int reward, String note) {

        boolean createTask = this.lookForTask(name);

        System.out.println("Before creation Task in Family xyz, arg creator is " + creator.getFname() + " Boolean is " + createTask);

        Task newTask = null;

        //if task does not exist, we create it.
        if(!createTask) {
            //Start by creating date
            Calendar c = Calendar.getInstance();
            c.set(year, month-1, day); //months are 0-11

            //TODO get if it's recurrent
            //id will be set when adding
            //TODO review the TaskState, we might have changed plans
            //At this point, the Task is in created State
            newTask = new Task(null, name, note, c.getTimeInMillis(), false, time, reward,
                    Task.TaskState.Created, creator);

            System.out.println("Before added Task in Family xyz, task creator -->  " + newTask.getCreator().getFname());
            this.addTask(newTask);
            System.out.println("After added Task in Family xyz, task creator -->  " + newTask.getCreator().getFname());

        }

        System.out.println("After creation Task in Family xyz, task creator -->  " + newTask.getCreator().getFname());

        return newTask;

    }

    /**
     * Helper method to see if a Task with the name given in argument exists in active Tasks
     * @param name
     * @return
     */
    private boolean lookForTask(String name) {
        boolean exists = false;

        for (Task t : this.activeTasks) {
            System.out.println(t);
            if (t.getTitle().equals(name)) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    /**
     * Method that populates the users of all tasks. Will be called by MainActivy at right moment.
     */
    public void populateTaskUsers() {
        for (User u : users) {
            u.setAssignedToList(new ArrayList<Task>());
            u.setTasks(new ArrayList<Task>());
        }
        System.out.println("WASD Active task is of size " + activeTasks.size());
        for (Task t :  activeTasks) {
            if (t.getAssignedUserID() != null) {
                System.out.println("WASD t.getAssignedUserID est pas null");
                User assignedUser = getUserWithID(t.getAssignedUserID());
                System.out.println("WASD on a retrieve le user " + assignedUser.getFname() + " - " + assignedUser.getId());

                t.setUser(assignedUser);
                assignedUser.addAssignedTo(t);
            }
            if (t.getCreatorID() != null) {
                User creator = getUserWithID(t.getCreatorID());
                t.setCreator(creator);
                creator.addTask(t);
            }
        }
    }

    /**
     * Returns the user who's id is given. Returns null if such User does not exist
     * TODO make private, public for cheap break fix
     * @param id
     * @return
     */
    public User getUserWithID(String id) {
        User user = null;
        for (User u : users) {
            if(u.getId().equals(id)) {
                return u;
            }
        }
        return user;
    }
}
