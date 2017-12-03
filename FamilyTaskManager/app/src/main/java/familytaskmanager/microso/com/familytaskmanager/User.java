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

    public boolean removeAssignedTask(String taskID){  // TODO WALID, je sais que tu travaillais avec les assign, mais regarde ça et fais ce que tu veux avec, mais
                                                       // c'est la méthode que j'utilise dans la famille pour remove les tasks d'un user.
        System.out.println("WASDF - removeAssignedTask called for " + fname + " on task " + taskID);
        printAssggnedTasks();
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
        printAssggnedTasks();
        return removed;
    }

    //TODO return acutal password
    public String getPassword() {
        return "allo";
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

    /*public boolean isIsParent() {
        return isParent;
    }*/

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

    /*public int indexOfAssignedTo(Task aAssignedTo) {
        int index = assignedTo.indexOf(aAssignedTo);
        return index;
    }*/

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

    /*public int indexOfTask(Task aTask) {
        int index = tasks.indexOf(aTask);
        return index;
    }*/

    public static int minimumNumberOfAssignedTo() {
        return 0;
    }

    public boolean addAssignedTo(Task aAssignedTo) {
        System.out.println("WASDF - addAssignedTo called for " + fname);
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
        System.out.println("WASDF - removeAssignedTo called for " + fname);
        boolean wasRemoved = false;
        if (alreadyAssignedTo(task)) {
            System.out.println("WASD - entered contains");
            removeAssignedTask(task.getId());
            //assignedTo.remove(task);

            //There is security in task class in case the User was already removed from Task
            task.removeAssignedUser();

            wasRemoved = true;
        }
        return wasRemoved;
    }

    /*public boolean addAssignedToAt(Task aAssignedTo, int index) {
        boolean wasAdded = false;
        if (addAssignedTo(aAssignedTo)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfAssignedTo()) {
                index = numberOfAssignedTo() - 1;
            }
            assignedTo.remove(aAssignedTo);
            assignedTo.add(index, aAssignedTo);
            wasAdded = true;
        }
        return wasAdded;
    }*/

    /*public boolean addOrMoveAssignedToAt(Task aAssignedTo, int index) {
        boolean wasAdded = false;
        if (assignedTo.contains(aAssignedTo)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfAssignedTo()) {
                index = numberOfAssignedTo() - 1;
            }
            assignedTo.remove(aAssignedTo);
            assignedTo.add(index, aAssignedTo);
            wasAdded = true;
        } else {
            wasAdded = addAssignedToAt(aAssignedTo, index);
        }
        return wasAdded;
    }*/

    public static int minimumNumberOfTasks() {
        return 0;
    }

    public Task addTask(String aId, String aTitle, String aNote, long aDueDate, boolean aRecurrent, double aEstimatedTime, int aRewardPts, Task.TaskState aState) {
        return new Task(aId, aTitle, aNote, aDueDate, aRecurrent, aEstimatedTime, aRewardPts, aState, this);
    }

    public boolean addTask(Task aTask) {
        System.out.println("WASDF - addTask called for " + fname);
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
        System.out.println("WASDF - removeTask called for " + fname);
        boolean wasRemoved = false;
        //Unable to remove aTask, as it must always have a creator
        if (!this.equals(aTask.getCreator())) {
            tasks.remove(aTask);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /*public boolean addTaskAt(Task aTask, int index) {
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
    }*/

    /*public boolean addOrMoveTaskAt(Task aTask, int index) {
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
    }*/

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

    //TODO remove, testing method
    public void printAssggnedTasks() {
        for (Task t : assignedTo) {
            System.out.print("WASDF - " + t.getTitle());
        }
    }
}