package familytaskmanager.microso.com.familytaskmanager;

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
    private Date dueDate;
    private boolean recurrent;
    private double estimatedTime;
    private int rewardPts;
    private TaskState state;

    //Task Associations
    private User user;
    private User creator;
    private List<Tool> tools;

    //------------------------
    // CONSTRUCTOR
    //------------------------
    public Task() {
        // For Firebase
    }

    public Task(String aId, String aTitle, String aNote, Date aDueDate, boolean aRecurrent, double aEstimatedTime, int aRewardPts, TaskState aState, User aCreator) {
        id = aId;
        title = aTitle;
        note = aNote;
        dueDate = aDueDate;
        recurrent = aRecurrent;
        estimatedTime = aEstimatedTime;
        rewardPts = aRewardPts;
        state = aState;
        boolean didAddCreator = setCreator(aCreator);
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

    public boolean setDueDate(Date aDueDate) {
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

    public Date getDueDate() {
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
        return user;
    }

    public boolean hasUser() {
        boolean has = user != null;
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
        return creator;
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


    public boolean setUser(User aUser) {
        boolean wasSet = false;
        User existingUser = user;
        user = aUser;
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

        User existingCreator = creator;
        creator = aCreator;
        if (existingCreator != null && !existingCreator.equals(aCreator)) {
            existingCreator.removeTask(this);
        }
        creator.addTask(this);
        wasSet = true;
        return wasSet;
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
        if (aTool.indexOfTask(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aTool.addTask(this);
            if (!wasAdded) {
                tools.remove(aTool);
            }
        }
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
        if (user != null) {
            User placeholderUser = user;
            this.user = null;
            placeholderUser.removeAssignedTo(this);
        }
        User placeholderCreator = creator;
        this.creator = null;
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
                "  " + "dueDate" + "=" + (getDueDate() != null ? !getDueDate().equals(this) ? getDueDate().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "state" + "=" + (getState() != null ? !getState().equals(this) ? getState().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "user = " + (getUser() != null ? Integer.toHexString(System.identityHashCode(getUser())) : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "creator = " + (getCreator() != null ? Integer.toHexString(System.identityHashCode(getCreator())) : "null");
    }
}