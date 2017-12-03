package familytaskmanager.microso.com.familytaskmanager;

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

    private User currentUser;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Family(int aId) {
        id = aId;
        tools = new ArrayList<Tool>();
        users = new ArrayList<User>();
        //this user will always be replaced
        currentUser = new User("0", "Default", "User", true, "menu_people", 0);

        shoppingItems = new ArrayList<ShoppingItem>();
        activeTasks = new ArrayList<Task>();
        inactiveTasks = new ArrayList<Task>();

        toolsReference = database.getReference("Tools");
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

    //------------------------
    // INTERFACE
    //------------------------
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

    public List<String> getToolsID() {
        List<String> ids = new ArrayList<String>();
        for (Tool t : tools) {
            ids.add(t.getId());
        }
        return ids;
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
        System.out.println("WASD - Update user called with " + aUser.getFname() + " - " + aUser.hasAssignedTo());
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

    public boolean addTaskAt(Task aTask, int index) {
        boolean wasAdded = false;
        if (addTask(aTask)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTasks()) {
                index = numberOfTasks() - 1;
            }
            activeTasks.remove(aTask);
            activeTasks.add(index, aTask);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveTaskAt(Task aTask, int index) {
        boolean wasAdded = false;
        if (activeTasks.contains(aTask)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfTasks()) {
                index = numberOfTasks() - 1;
            }
            activeTasks.remove(aTask);
            activeTasks.add(index, aTask);
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
        activeTasks.clear();
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "]";
    }

    /**
     * Testong Method.
     * @return
     */
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

        shoppingItemsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clearing the list
                shoppingItems.clear();

                // Iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting each item
                    ShoppingItem shoppingItem = postSnapshot.getValue(ShoppingItem.class);

                    // Add shopping getted in the list
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

    }

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
        System.out.println("WASD Active task is of size " + activeTasks.size());
        for (Task t :  activeTasks) {
            if (t.getAssignedUserID() != null) {
                System.out.println("WASD t.getAssignedUserID est pas null");
                User assignedUser = getUserWithID(t.getAssignedUserID());
                System.out.println("WASD on a retrieve le user " + assignedUser.getFname() + " - " + assignedUser.getId());
                t.setUser(assignedUser);
            }
            if (t.getCreatorID() != null) {
                User creator = getUserWithID(t.getCreatorID());
                t.setCreator(creator);
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

    public void initializeDummyDB() {
        String tool_id = toolsReference.push().getKey();
        toolsReference.child(tool_id).setValue(new Tool(tool_id, "Walid", 2));
    }

    /*public static Family createDummyFamily() {

        User mainUser = new User("1", "Walid", "B", true, R.drawable.menu_people, 0);

        Family family = new Family(1);

        //Creation of 4 more users for testing
        User thomas = new User("2", "Thomas", "C", true, R.drawable.menu_people, 0);
        User vincent = new User("3", "Vincent", "H", true, R.drawable.menu_people, 0);
        User oliver = new User("4", "Oliver", "B", false, R.drawable.menu_people, 0);
        User jeanGab = new User("5", "Jean-Gabriel", "G", true, R.drawable.menu_people, 0);
        family.addUser(mainUser);
        family.addUser(thomas);
        family.addUser(vincent);
        family.addUser(oliver);
        List<User> users = new ArrayList<>();
        users.add(mainUser);
        users.add(thomas);
        users.add(vincent);
        users.add(oliver);
        users.add(jeanGab);
        family.users = users;*/

        /*Creating some tools for the tasks
        Tool bucket = new Tool("1", "Bucket", 5);
        Tool mop = new Tool("2", "Mop", 4);
        Tool sponge = new Tool("3", "Sponge", 3);
        Tool wrench = new Tool("4", "Wrench", 2);
        Tool broom = new Tool("5", "Broom", 1);
        family.tools.add(bucket);
        family.tools.add(mop);
        family.tools.add(sponge);
        family.tools.add(wrench);
        family.tools.add(broom);*/

        //Creation of 5 tasks for testing
        /*Task dishes = new Task("1", "Dishes", "Dishes note", 1511880133, false, 0.25, 5, Task.TaskState.Created, mainUser);
        dishes.setUser(mainUser);
        dishes.addTool(sponge);
        Task sweep = new Task("2", "Sweep", "Sweep noooooooote", 1511880133, false, 1, 10, Task.TaskState.Created, mainUser);
        sweep.setUser(thomas);
        sweep.addTool(broom);
        Task washCar = new Task("1", "Wash Car", "Wash Car note", 1511880133, false, 1, 5, Task.TaskState.Created, thomas);
        washCar.addTool(bucket);
        washCar.addTool(sponge);
        Task shop = new Task("1", "Shop", "shop nooooote", 1511880133, false, 0.25, 5, Task.TaskState.Created, mainUser);
        shop.addTool(mop);
        Task otherTask = new Task("1", "Other", "Other task note", 1511880133, false, 0.25, 5, Task.TaskState.Created, mainUser);
        otherTask.addTool(wrench);
        Task outOfIdeas = new Task("1", "I'm out of ideas", "Other task note", 1511880133, false, 0.25, 5, Task.TaskState.Created, mainUser);
        List<Task> tasks = new ArrayList<>();
        tasks.add(dishes);
        tasks.add(sweep);
        tasks.add(washCar);
        tasks.add(shop);
        tasks.add(otherTask);
        tasks.add(outOfIdeas);
        family.activeTasks = tasks;

        return family;
    }*/
}
