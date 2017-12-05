package familytaskmanager.microso.com.familytaskmanager;

import java.io.Serializable;
import java.util.*;
import java.sql.Date;

public class User implements Serializable {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //User Attributes
    private String id;
    private String fname;
    private String lname;
    private boolean isParent;
    private String profilePicResourceName;
    private int accumulatedPts;
    private String password;

    //User Associations
    private List<Task> assignedTo;
    private List<Task> tasks;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    public User() {
        // For Firebase
    }

    public User(String aId, String aFname, String aLname, boolean aIsParent, String aProfilePicResourceName, int aAccumulatedPts) {
        id = aId;
        fname = aFname;
        lname = aLname;
        isParent = aIsParent;
        profilePicResourceName = aProfilePicResourceName;
        accumulatedPts = aAccumulatedPts;
        assignedTo = new ArrayList<Task>();
        tasks = new ArrayList<Task>();
        password = "1234";
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setId(String aId) {
        boolean wasSet = false;
        id = aId;
        wasSet = true;
        return wasSet;
    }

    public boolean setFname(String aFname) {
        boolean wasSet = false;
        fname = aFname;
        wasSet = true;
        return wasSet;
    }

    public boolean setLname(String aLname) {
        boolean wasSet = false;
        lname = aLname;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsParent(boolean aIsParent) {
        boolean wasSet = false;
        isParent = aIsParent;
        wasSet = true;
        return wasSet;
    }

    public boolean setProfilePicResourceName(String aProfilePicResourceName) {
        boolean wasSet = false;
        profilePicResourceName = aProfilePicResourceName;
        wasSet = true;
        return wasSet;
    }

    public boolean setAccumulatedPts(int aAccumulatedPts) {
        boolean wasSet = false;
        accumulatedPts = aAccumulatedPts;
        wasSet = true;
        return wasSet;
    }

    public boolean removeAssignedTask(String taskID){
        Task toRemove = null; //Need this to avoid concurent exception
        boolean removed = false;
        for (Task a_t : assignedTo) {
            if (a_t.getId().equals(taskID)) {
                toRemove = a_t;
                removed = true;
                break;
            }
        }
        assignedTo.remove(toRemove);
        return removed;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public String getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public String getProfilePicResourceName() {
        return profilePicResourceName;
    }

    public int getAccumulatedPts() {
        return accumulatedPts;
    }

    public Task getAssignedTo(int index) {
        Task aAssignedTo = assignedTo.get(index);
        return aAssignedTo;
    }

    public List<Task> getAssignedTo() {
        //TODO REMOVE List<Task> newAssignedTo = Collections.unmodifiableList(assignedTo);
        return assignedTo;
    }

    public int numberOfAssignedTo() {
        int number = assignedTo.size();
        return number;
    }

    public boolean hasAssignedTo() {
        boolean has = assignedTo.size() > 0;
        return has;
    }

    public Task getTask(int index) {
        Task aTask = tasks.get(index);
        return aTask;
    }

    public List<Task> getTasks() {
        //TODO REMOVE List<Task> newTasks = Collections.unmodifiableList(tasks);
        return this.tasks;
    }

    public int numberOfTasks() {
        int number = tasks.size();
        return number;
    }

    public boolean hasTasks() {
        boolean has = tasks.size() > 0;
        return has;
    }

    public boolean addAssignedTo(Task aAssignedTo) {
        boolean wasAdded = false;
        if (alreadyAssignedTo(aAssignedTo)) {
            return false;
        }
        User existingUser = aAssignedTo.getUser();
        if (existingUser == null) {
            aAssignedTo.setUser(this);
        } else if (!this.equals(existingUser)) {
            existingUser.removeAssignedTo(aAssignedTo);
            addAssignedTo(aAssignedTo);
        } else {
            assignedTo.add(aAssignedTo);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeAssignedTo(Task task) {
        boolean wasRemoved = false;
        if (alreadyAssignedTo(task)) {
            removeAssignedTask(task.getId());
            task.removeAssignedUser();
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public static int minimumNumberOfTasks() {
        return 0;
    }

    public boolean addTask(Task aTask) {
        boolean wasAdded = false;
        if (tasks.contains(aTask)) {
            return false;
        }
        User existingCreator = aTask.getCreator();
        boolean isNewCreator = existingCreator != null && !this.equals(existingCreator);
        if (isNewCreator) {
            aTask.setCreator(this);
        } else {
            tasks.add(aTask);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeTask(Task aTask) {
        boolean wasRemoved = false;
        if (!this.equals(aTask.getCreator())) {
            tasks.remove(aTask);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public void delete() {
        while (!assignedTo.isEmpty()) {
            assignedTo.get(0).setUser(null);
        }
        for (int i = tasks.size(); i > 0; i--) {
            Task aTask = tasks.get(i - 1);
            aTask.delete();
        }
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setAssignedToList(List<Task> assignedToList) {
        this.assignedTo = assignedToList;
    }

    private boolean alreadyAssignedTo(Task aTask){
        boolean contains = false;

        for(Task t : assignedTo) {
            if (t.getId().equals(aTask.getId())){
                contains = true;
            }
        }
        return contains;
    }

    public String toString() {
        return (fname+" "+lname);
    }
}