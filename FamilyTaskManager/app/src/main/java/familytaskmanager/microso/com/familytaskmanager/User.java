package familytaskmanager.microso.com.familytaskmanager;

import java.util.*;
import java.sql.Date;

public class User {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //User Attributes
    private int id;
    private String fname;
    private String lname;
    private boolean isParent;
    private int profilePicId;
    private int accumulatedPts;

    //User Associations
    private List<Task> assignedTo;
    private List<Task> tasks;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public User(int aId, String aFname, String aLname, boolean aIsParent, int aProfilePicId, int aAccumulatedPts) {
        id = aId;
        fname = aFname;
        lname = aLname;
        isParent = aIsParent;
        profilePicId = aProfilePicId;
        accumulatedPts = aAccumulatedPts;
        assignedTo = new ArrayList<Task>();
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

    public boolean setProfilePicId(int aProfilePicId) {
        boolean wasSet = false;
        profilePicId = aProfilePicId;
        wasSet = true;
        return wasSet;
    }

    public boolean setAccumulatedPts(int aAccumulatedPts) {
        boolean wasSet = false;
        accumulatedPts = aAccumulatedPts;
        wasSet = true;
        return wasSet;
    }

    public int getId() {
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

    public int getProfilePicId() {
        return profilePicId;
    }

    public int getAccumulatedPts() {
        return accumulatedPts;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public Task getAssignedTo(int index) {
        Task aAssignedTo = assignedTo.get(index);
        return aAssignedTo;
    }

    public List<Task> getAssignedTo() {
        List<Task> newAssignedTo = Collections.unmodifiableList(assignedTo);
        return newAssignedTo;
    }

    public int numberOfAssignedTo() {
        int number = assignedTo.size();
        return number;
    }

    public boolean hasAssignedTo() {
        boolean has = assignedTo.size() > 0;
        return has;
    }

    public int indexOfAssignedTo(Task aAssignedTo) {
        int index = assignedTo.indexOf(aAssignedTo);
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

    public static int minimumNumberOfAssignedTo() {
        return 0;
    }

    public boolean addAssignedTo(Task aAssignedTo) {
        boolean wasAdded = false;
        if (assignedTo.contains(aAssignedTo)) {
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

    public boolean removeAssignedTo(Task aAssignedTo) {
        boolean wasRemoved = false;
        if (assignedTo.contains(aAssignedTo)) {
            assignedTo.remove(aAssignedTo);
            aAssignedTo.setUser(null);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    public boolean addAssignedToAt(Task aAssignedTo, int index) {
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
    }

    public boolean addOrMoveAssignedToAt(Task aAssignedTo, int index) {
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
    }

    public static int minimumNumberOfTasks() {
        return 0;
    }

    public Task addTask(int aId, String aTitle, String aNote, Date aDueDate, boolean aRecurrent, double aEstimatedTime, int aRewardPts, Task.TaskState aState) {
        return new Task(aId, aTitle, aNote, aDueDate, aRecurrent, aEstimatedTime, aRewardPts, aState, this);
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
        //Unable to remove aTask, as it must always have a creator
        if (!this.equals(aTask.getCreator())) {
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
        while (!assignedTo.isEmpty()) {
            assignedTo.get(0).setUser(null);
        }
        for (int i = tasks.size(); i > 0; i--) {
            Task aTask = tasks.get(i - 1);
            aTask.delete();
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "," +
                "fname" + ":" + getFname() + "," +
                "lname" + ":" + getLname() + "," +
                "isParent" + ":" + getIsParent() + "," +
                "profilePicId" + ":" + getProfilePicId() + "," +
                "accumulatedPts" + ":" + getAccumulatedPts() + "]";
    }
}