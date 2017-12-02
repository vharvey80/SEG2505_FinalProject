package familytaskmanager.microso.com.familytaskmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskDetailActivity extends AppCompatActivity {

    private Task presentTask;
    private List<Tool> familyToolList;
    private List<User> userList;

    //flag to be set when we change the user (assign or un-assign)
    private boolean userChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        presentTask = (Task) intent.getSerializableExtra("task");
        familyToolList = (List<Tool>) intent.getSerializableExtra("toolList");
        userList = (List<User>) intent.getSerializableExtra("userList");

        userChange = false; //default no change

        this.updateActivityView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_changeTaskName:
                Toast.makeText(this, "Can't chane yet, but should be easy", Toast.LENGTH_SHORT).show();
                showDialogPart1();
                return true;
            case R.id.action_deletaTask:
                Toast.makeText(this, "Can't delete yet, might get complicated", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                System.out.println("Clicked home, xyz");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item); //Simply copied this line from official Android Tutorials
        }
    }

    private void showDialogPart1() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_1, null);
        MainActivity.setNumberPickersDialog(dialogView);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Task Info (1/2)");

        //get buttons to set onClick
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.dialogConfirmAndNext);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.dialogCancel);

        final AlertDialog dialog = dialogBuilder.create();
        this.populateChangeInfoDialog(dialogView);
        dialog.show();

        //setting function of the confirm button
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting boolean to stop advancement if needed
                boolean allGood = true;

                //Here we get all the info entered on the first dialog
                EditText nameInput = (EditText) dialogView.findViewById(R.id.dialogTaskNameField);
                String taskName = nameInput.getText().toString();

                EditText timeInput = (EditText) dialogView.findViewById(R.id.dialogTimeField);
                String taskTime = timeInput.getText().toString();

                NumberPicker yearInput = (NumberPicker) dialogView.findViewById(R.id.dialogYearPicker);
                int year = yearInput.getValue();

                NumberPicker monthInput = (NumberPicker) dialogView.findViewById(R.id.dialogMonthPicker);
                int month = monthInput.getValue();

                NumberPicker dayInput = (NumberPicker) dialogView.findViewById(R.id.dialogDayPicker);
                int day = dayInput.getValue();

                EditText rewardInput = (EditText) dialogView.findViewById(R.id.dialogRewardField);
                String taskReward = rewardInput.getText().toString();

                EditText noteInput = (EditText) dialogView.findViewById(R.id.dialogNoteField);
                String taskNote = noteInput.getText().toString();
                //End of getting the info

                //Checking if any field is empty
                boolean allFilled = checkAllFilled(taskName, taskTime, taskReward, taskNote);
                if (!allFilled) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    allGood = false;
                }

                //validating the rest. Only show message if all is good up to validation.
                // Avoiding to many messages to user
                double validTime = validateTime(taskTime); //validating input
                if (validTime == -1 && allGood) {
                    Toast.makeText(getApplicationContext(), "Wrong Time input. Make sure its a number over 0.", Toast.LENGTH_LONG).show();
                    allGood = false;
                }

                boolean validDate = validateDate(year, month, day); //Validating date
                if (!validDate && allGood) {
                    Toast.makeText(getApplicationContext(), "Wrong Date input. Make sure to respect the number of days per month.", Toast.LENGTH_LONG).show();
                    allGood = false;
                }

                //TODO make sure if not parent, reward = 0 pts
                int validReward = validateReward(taskReward); //validating input
                if (validReward == -1 && allGood) {
                    Toast.makeText(getApplicationContext(), "Wrong Reward input. Make sure its an integer over 0.", Toast.LENGTH_LONG).show();
                    allGood = false;
                }
                //End of validating fields

                if (allGood) {
                    dialog.cancel();

                    //TODO finish implementation
                    //Since all good, we update all info
                    updateTaskTask(taskName, validTime, year, month, day, validReward, taskNote);
                    updateActivityView();
                    showDialogPart2();

                }
            }
        });

        //cancel button function
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void showDialogPart2() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_2, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Task Info (2/2)");

        //get buttons to set onClick
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.dialogConfirmAndNext);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.dialogCancel);

        //Getting LIstView and populating
        final ListView listView = (ListView) dialogView.findViewById(R.id.toolListView);
        String[] toolArray = this.getToolArray(familyToolList);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, toolArray);
        listView.setAdapter(adapter);
        //End of listView code

        //Setting all tools of the task as checked already
        for (Tool t : presentTask.getTools()) {
            int index = taskToolIsInFamilyList(t);
            if (index != -1) {
                listView.setItemChecked(index, true);
            }
        }

        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        //setting function of the confirm button
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //clearing list before populating
                presentTask.getTools().clear();

                SparseBooleanArray checked = listView.getCheckedItemPositions();

                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {

                        //TODO review strategie for adding tool
                        // To add tool, we will add them to the tool object given in argument to onclick
                        // and will update DB when going back to MainActivity (startActivityForResult).
                        boolean added = presentTask.addTool(familyToolList.get(i));
                    }
                }

                //once all added, we call for update
                updateActivityView();
                dialog.cancel(); //TODO finish logic
            }
        });

        //cancel button function
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

    private void populateChangeInfoDialog(View dialogView) {

        //Locating all the fields
        EditText taskName = (EditText)dialogView.findViewById(R.id.dialogTaskNameField);
        EditText taskTime = (EditText)dialogView.findViewById(R.id.dialogTimeField);
        NumberPicker yearPicker = (NumberPicker) dialogView.findViewById(R.id.dialogYearPicker);
        NumberPicker monthPicker = (NumberPicker) dialogView.findViewById(R.id.dialogMonthPicker);
        NumberPicker dayPicker = (NumberPicker) dialogView.findViewById(R.id.dialogDayPicker);
        EditText taskPoints = (EditText)dialogView.findViewById(R.id.dialogRewardField);
        EditText taskNote = (EditText)dialogView.findViewById(R.id.dialogNoteField);

        //Populating the fields with current info
        taskName.setText(presentTask.getTitle());
        taskTime.setText(Double.toString(presentTask.getEstimatedTime()));

        //Setting up Calendar to get date in a proper manner
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(presentTask.getDueDate()));

        yearPicker.setValue(calendar.get(Calendar.YEAR));
        monthPicker.setValue(calendar.get(Calendar.MONTH));
        dayPicker.setValue(calendar.get(Calendar.DAY_OF_MONTH));

        taskPoints.setText(Integer.toString(presentTask.getRewardPts()));
        taskNote.setText(presentTask.getNote());

    }

    /**
     * REMOVE METHOD, JUST FOR TEST.
     * @param list
     * @return
     */
    private String[] getToolArray(List<Tool> list) {
        String[] out = new String[list.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = list.get(i).getName();
        }
        return out;
    }

    /**
     * Helper method. Return double if time given is valid. Else, returns -1.
     * TODO: Add description for arguments
     * @param time
     * @return
     */
    private double validateTime(String time) {

        double output;

        try {
            output = Double.parseDouble(time);
        }catch (Exception ex) {
            output = -1;
        }

        if (output <= 0) {
            output = -1;
        }

        return output;

    }

    /**
     * Helper method. Return true if date given is valid. Else, returns false.
     * TODO: Add description for arguments
     * @param month
     * @param day
     * @return
     */
    private boolean validateDate(int year, int month, int day) {

        boolean valid = true;

        //Test February with 28 days
        if (month == 2) {
            boolean leapYear = ((year % 4) == 0);
            if ((day > 28 && !leapYear) || (day > 29 && leapYear)) {
                valid = false;
            }
        }

        //test 30 day month
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                valid = false;
            }
        }

        return valid;

    }

    /**
     * Helper method. Return int if points given is valid. Else, returns -1.
     * TODO: Add description for arguments
     * @param pts
     * @return
     */
    private int validateReward(String pts) {

        int output;

        try {
            output = Integer.parseInt(pts);
        }catch (Exception ex) {
            output = -1;
        }

        if (output <= 0) {
            output = -1;
        }

        return output;

    }

    /**
     * Helper method that updates all fields
     * @param taskName
     * @param taskTime
     * @param taskReward
     * @param taskNote
     * @return
     */
    private boolean checkAllFilled(String taskName, String taskTime, String taskReward, String taskNote) {

        boolean valid = true;

        if (taskName.isEmpty() || taskTime.isEmpty() || taskReward.isEmpty() || taskNote.isEmpty()) {
            valid = false;
        }

        return valid;

    }

    private void updateTaskTask(String taskName, double validTime, int year, int month, int day, int validReward, String taskNote) {
        presentTask.setTitle(taskName);
        presentTask.setEstimatedTime(validTime);

        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day); //months are 0-11
        presentTask.setDueDate(c.getTimeInMillis());

        presentTask.setRewardPts(validReward);
        presentTask.setNote(taskNote);
    }

    /**
     * Helper method, updates/initializes all fields in Activity
     */
    private void updateActivityView() {
        //Setting the title to the task from which the activity was called
        if (presentTask != null) {
            getSupportActionBar().setTitle(presentTask.getTitle());
            //setTitle(presentTask.getTitle());
        }else {
            getSupportActionBar().setTitle("Task");
            //setTitle("Task");
        }

        //Setting all the fiels in accordance to Task

        //The fields bellow depend on if the task is assigned or not
        ImageView assignedUserIcon = (ImageView) findViewById(R.id.userIcon);
        TextView assignedUserName = (TextView) findViewById(R.id.taskUserName);
        TextView taskAssignedText = (TextView) findViewById(R.id.taskAssignedOrNot);
        TextView assignOrReleaseText = (TextView) findViewById(R.id.assignOrReleaseText);
        ImageView assignOrReleaseIcon = (ImageView) findViewById(R.id.assignOrReleaseIcon);
        if (presentTask.hasUser()) {
            //Getting profile pic
            String resourceName = presentTask.getUser().getProfilePicResourceName();
            Resources resources = getResources();
            int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
            assignedUserIcon.setImageResource(resourceId);
            //end of getting picture
            assignedUserName.setText(presentTask.getUser().getFname());
            taskAssignedText.setText("Assigned");
            assignOrReleaseText.setText("Release");
            assignOrReleaseIcon.setImageResource(android.R.drawable.presence_busy);
        } else {
            assignedUserIcon.setImageResource(android.R.drawable.ic_menu_add);
            assignedUserName.setText("Not assigned yet");
            taskAssignedText.setText("Unassigned");
            assignOrReleaseText.setText("Assign");
            assignOrReleaseIcon.setImageResource(android.R.drawable.ic_input_add);
        }

        //Setting the constant fields (time, creator, etc)
        TextView estimatedTime = (TextView) findViewById(R.id.estimatedTimeText);
        estimatedTime.setText(Double.toString(presentTask.getEstimatedTime()) + "h"); //Make this fancier

        //Setting up Calendar to get date in a proper manner
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(presentTask.getDueDate()));

        TextView dueDateField = (TextView) findViewById(R.id.limitDateText);
        dueDateField.setText(dateFormat.format(calendar.getTime()));

        TextView creatorNameField = (TextView) findViewById(R.id.creatorName);
        creatorNameField.setText(presentTask.getCreator().getFname());
        TextView rewardPts = (TextView) findViewById(R.id.rewardValueField);
        rewardPts.setText(presentTask.getRewardPts() + " points");

        //Setting the Gridview adapter
        GridView toolGridView = (GridView) findViewById(R.id.taskToolsGrid);

        TaskActivityGridToolAdapter gridToolAdapter = new TaskActivityGridToolAdapter(getApplicationContext(), presentTask.getTools());
        toolGridView.setAdapter(gridToolAdapter);

        //Setting the note
        TextView noteText = (TextView) findViewById(R.id.noteText);
        noteText.setText(presentTask.getNote());

        //setting onClick for AssignOrReleaseIcon
        assignOrReleaseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presentTask.hasUser()) {
                    showReleaseDialog();
                } else {
                    assignTask();
                }
            }
        });
    }

    private int taskToolIsInFamilyList(Tool tool) {
        int index = -1;

        for (int i = 0; i < familyToolList.size() && index == -1; i++) {
            if (familyToolList.get(i).getId().equals(tool.getId())) {
                index = i;
            }
        }

        return index;
    }

    private void assignTask() {
        //Dialog code
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.dialog_choose_user, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Choose User To Assign");
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        //end dialog code

        //List view code
        ListView listView = (ListView) dialogView.findViewById(R.id.dialogUserList);

        PeopleListAdapter peopleListAdapter = new PeopleListAdapter(getApplicationContext(), userList);
        listView.setAdapter(peopleListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                alertDialog.dismiss();
                final User clickedUser = (User) parent.getItemAtPosition(position);

                //TODO Make diffrence between current parent user and current kid
                if(true) {
                    askAssignmentConfirmation(clickedUser);
                }

            }
        });
        //End of List view code
    }

    /**
     * Method called when user chose who to assign task to.
     */
    private void askAssignmentConfirmation(final User userToAssign) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Confirm allocation")
                .setMessage("Task will be assigned to " + userToAssign.getFname() + "\n"
                        + "Note: \n - " + presentTask.getNote())
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        presentTask.setUser(userToAssign); //Should also assign task to user
                        userChange = true;
                        updateActivityView();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void showReleaseDialog() {

        //TODO check that the present user is assigned

    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("updatedTask", (Serializable) presentTask);
        if(presentTask.hasUser() && userChange) {
            returnIntent.putExtra("updatedUser", (Serializable) presentTask.getUser());
        }
        setResult(MainActivity.TASK_ACTIVITY_REQ_CODE, returnIntent);;
        super.finish();
    }

}
