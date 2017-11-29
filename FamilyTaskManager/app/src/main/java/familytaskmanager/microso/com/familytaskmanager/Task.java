package familytaskmanager.microso.com.familytaskmanager;

import android.widget.Toast;

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
    //key "user" is assigned user. Key "creator" is creator
    //Need to use map to make firebase work
    private Map<String, User> users;
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

    public User getUser() {
        return users.get("user");
    }

    public boolean hasUser() {
        boolean has = users.get("user") != null;
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

    public User getCreator() {
        return users.get("creator");
    }

    public Tool getTool(int index) {
        Tool aTool = tools.get(index);
        return aTool;
    }

    public List<Tool> getTools() {
        //TODO analyze code
        //List<Tool> newTools = Collections.unmodifiableList(tools);
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


    public boolean setUser(User aUser) {
        boolean wasSet = false;
        User existingUser = users.get("user");
        users.put("user", aUser);
        //user = aUser;
        if (existingUser != null && !existingUser.equals(aUser)) {
            existingUser.removeAssignedTo(this);
        }
        if (aUser != null) {
            aUser.addAssignedTo(this);
        }
        wasSet = true;
        return wasSet;
    }

    public boolean setCreator(User aCreator) {
        boolean wasSet = false;
        if (aCreator == null) {
            return wasSet;
        }

        User existingCreator = users.get("creator");
        users.put("creator", aCreator);
        //creator = aCreator;
        if (existingCreator != null && !existingCreator.equals(aCreator)) {
            existingCreator.removeTask(this);
        }
        users.get("creator").addTask(this);
        wasSet = true;
        return wasSet;
    }

    public static int minimumNumberOfTools() {
        return 0;
    }

    public boolean addTool(Tool aTool) {
        boolean wasAdded = true;
        if (tools.contains(aTool)) {
            return false;
        }
        tools.add(aTool);
        //TODO clean
        /*if (aTool.indexOfTask(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aTool.addTask(this);
            if (!wasAdded) {
                tools.remove(aTool);
            }
        }*/
        return wasAdded;
    }

    public boolean removeTool(Tool aTool) {
        boolean wasRemoved = false;
        if (!tools.contains(aTool)) {
            return wasRemoved;
        }

        int oldIndex = tools.indexOf(aTool);
        tools.remove(oldIndex);
        if (aTool.indexOfTask(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aTool.removeTask(this);
            if (!wasRemoved) {
                tools.add(oldIndex, aTool);
            }
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

    public void delete() {
        if (users.get("user") != null) {
            User placeholderUser = users.get("user");
            this.users.put("user", null);
            //this.user = null;
            placeholderUser.removeAssignedTo(this);
        }
        User placeholderCreator = users.get("cretor");
        this.users.put("creator", null);
        //this.creator = null;
        placeholderCreator.removeTask(this);
        ArrayList<Tool> copyOfTools = new ArrayList<Tool>(tools);
        tools.clear();
        for (Tool aTool : copyOfTools) {
            aTool.removeTask(this);
        }
    }


    public String toString() {
        return super.toString() + "[" +
                "id" + ":" + getId() + "," +
                "title" + ":" + getTitle() + "," +
                "note" + ":" + getNote() + "," +
                "recurrent" + ":" + getRecurrent() + "," +
                "estimatedTime" + ":" + getEstimatedTime() + "," +
                "rewardPts" + ":" + getRewardPts() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "dueDate" + "=" + getDueDate() + " " +
                "  " + "state" + "=" + (getState() != null ? !getState().equals(this) ? getState().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "user = " + (getUser() != null ? Integer.toHexString(System.identityHashCode(getUser())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "creator = " + (getCreator() != null ? Integer.toHexString(System.identityHashCode(getCreator())) : "null");
    }

    //TODO maybe remove these methods if map test not work.
    public Map<String, User> getUsers() { return users; }

    public void setUsers(Map<String, User> users) { this. users = users; }

    public void setTools(List<Tool> tools) { this.tools = tools; }
}