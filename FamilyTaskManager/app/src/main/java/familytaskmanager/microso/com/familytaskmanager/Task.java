package familytaskmanager.microso.com.familytaskmanager;

import android.widget.Toast;

import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.sql.Date;
import java.util.*;

public class Task implements Serializable {

    //------------------------
    // ENUMERATIONS
    //------------------------

    public enum TaskState {Created, Completed, Cancelled}

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Task Attributes
    private String id;
    private String title;
    private String note;
    //I store Date as long (epoch Date), since Date is not supported in Firebase
    private long dueDate;
    private boolean recurrent;
    private double estimatedTime;
    private int rewardPts;
    private TaskState state;

    //Task Associations
    //To avoid too many relations, which cauuses problems with Firebase,
    //we have two String for creator and assigned user ID. The map is kept to now change
    //functionality of class, but weill not be saved in DB
    private String creatorID;
    private String assignedUserID;
    private Map<String, User> users; //will contain two elements. Keys are creator and user
    private List<Tool> tools;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    public Task() {
        // For Firebase
    }

    public Task(String aId, String aTitle, String aNote, long aDueDate, boolean aRecurrent, double aEstimatedTime, int aRewardPts, TaskState aState, User aCreator) {
        id = aId;
        title = aTitle;
        note = aNote;
        dueDate = aDueDate;
        recurrent = aRecurrent;
        estimatedTime = aEstimatedTime;
        rewardPts = aRewardPts;
        state = aState;

        users = new HashMap<>();
        users.put("creator", aCreator);
        creatorID = aCreator.getId();

        //boolean didAddCreator = setCreator(aCreator);
        //TODO uncomment the bellow check
        /*if (!didAddCreator) {
            throw new RuntimeException("Unable to create task due to creator");
        }*/
        tools = new ArrayList<Tool>();
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

    public boolean setTitle(String aTitle) {
        boolean wasSet = false;
        title = aTitle;
        wasSet = true;
        return wasSet;
    }

    public boolean setNote(String aNote) {
        boolean wasSet = false;
        note = aNote;
        wasSet = true;
        return wasSet;
    }

    public boolean setDueDate(long aDueDate) {
        boolean wasSet = false;
        dueDate = aDueDate;
        wasSet = true;
        return wasSet;
    }

    public boolean setRecurrent(boolean aRecurrent) {
        boolean wasSet = false;
        recurrent = aRecurrent;
        wasSet = true;
        return wasSet;
    }

    public boolean setEstimatedTime(double aEstimatedTime) {
        boolean wasSet = false;
        estimatedTime = aEstimatedTime;
        wasSet = true;
        return wasSet;
    }

    public boolean setRewardPts(int aRewardPts) {
        boolean wasSet = false;
        rewardPts = aRewardPts;
        wasSet = true;
        return wasSet;
    }

    public boolean setState(TaskState aState) {
        boolean wasSet = false;
        state = aState;
        wasSet = true;
        return wasSet;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Could be a List or array if we want many
     */
    public String getNote() {
        return note;
    }

    public long getDueDate() {
        return dueDate;
    }

    public boolean getRecurrent() {
        return recurrent;
    }

    /**
     * e.g. 1 hour
     */
    public double getEstimatedTime() {
        return estimatedTime;
    }

    public int getRewardPts() {
        return rewardPts;
    }

    public TaskState getState() {
        return state;
    }

    @Exclude
    public User getUser() {
        return users.get("user");
    }

    public String getAssignedUserID() {return assignedUserID; }

    public boolean hasUser() {
        boolean has = users.get("user") != null;
        return has;
    }

    //TODO test method, remove
    public boolean hasCreator() {
        boolean has = users.get("creator") != null;
        return has;
    }

    /**
     * Checks if the task includes a note.
     * @return true if the task includes a note.
     */
    public boolean hasNote() {
        boolean has = note != null;
        return has;
    }

    @Exclude
    public User getCreator() {
        return users.get("creator");
    }

    public String getCreatorID() { return creatorID; }

    public Tool getTool(int index) {
        Tool aTool = tools.get(index);
        return aTool;
    }

    public List<Tool> getTools() {
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


    @Exclude
    public boolean setUser(User aUser) {
        boolean wasSet = false;
        User existingUser = users.get("user");
        users.put("user", aUser);
        assignedUserID = aUser.getId();
        if (existingUser != null && !existingUser.equals(aUser)) {
            existingUser.removeAssignedTo(this);
        }
        if (aUser != null) {
            aUser.addAssignedTo(this);
        }
        wasSet = true;
        return wasSet;
    }

    public boolean removeAssignedUser() {
        boolean wasRemoved = false;
        if(hasUser()) {
            User currentlyAssigned = users.get("user");

            users.put("user", null);
            assignedUserID = null;

            //Removing from the user. If already done, there is security in User method
            currentlyAssigned.removeAssignedTo(this);

            wasRemoved = true;

        }
        return wasRemoved;
    }

    public void setAssignedUserID(String anAssignedUserID) { this.assignedUserID = anAssignedUserID; }

    @Exclude
    public boolean setCreator(User aCreator) {
        boolean wasSet = false;
        if (aCreator == null) {
            return wasSet;
        }

        User existingCreator = users.get("creator");
        users.put("creator", aCreator);
        creatorID = aCreator.getId();
        //creator = aCreator;
        if (existingCreator != null && !existingCreator.equals(aCreator)) {
            existingCreator.removeTask(this);
        }
        //TODO we might want to remove association between creator and his created tasks
        users.get("creator").addTask(this);
        wasSet = true;
        return wasSet;
    }

    public void setCreatorID(String aCreatorID) { creatorID = aCreatorID; }

    public static int minimumNumberOfTools() {
        return 0;
    }

    public boolean addTool(Tool aTool) {
        boolean wasAdded = true;
        if (tools.contains(aTool)) {
            return false;
        }
        tools.add(aTool);
        return wasAdded;
    }

    public boolean removeTool(Tool aTool) {
        boolean wasRemoved = false;
        if (!tools.contains(aTool)) {
            return wasRemoved;
        }

        int oldIndex = tools.indexOf(aTool);
        tools.remove(oldIndex);
        wasRemoved = true; // TODO Deleted all link with tasks in tools.
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

    public void delete() {
        if (users.get("user") != null) {
            User placeholderUser = users.get("user");
            this.users.put("user", null);
            //this.user = null;
            placeholderUser.removeAssignedTo(this);
        }
        User placeholderCreator = users.get("creator");
        this.users.put("creator", null);
        //this.creator = null;
        placeholderCreator.removeTask(this);
        ArrayList<Tool> copyOfTools = new ArrayList<Tool>(tools);
        tools.clear();
    }


    public String toString() {
        return getTitle() + " : " + getNote();
    }

    @Exclude
    public Map<String, User> getUsers() { return users; }

    @Exclude
    public void setUsers(Map<String, User> users) { this.users = users; }

    public void setTools(List<Tool> tools) { this.tools = tools; }

}