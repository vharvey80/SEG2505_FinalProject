package familytaskmanager.microso.com.familytaskmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskDetailActivity extends AppCompatActivity {

    private Task presentTask;
    private List<Tool> familyToolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //Provding up navigation


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        presentTask = (Task) intent.getSerializableExtra("task");
        familyToolList = (List<Tool>) intent.getSerializableExtra("toolList");

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
            assignedUserIcon.setImageResource(presentTask.getUser().getProfilePicId());
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
        calendar.setTime(presentTask.getDueDate());

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
            default:
                return super.onOptionsItemSelected(item); //Simply copied this line from official Android Tutorials
        }
    }

    private void showDialogPart1() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_1, null);
        MainActivity.setNumberPickersDialog(dialogView);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Task Info (1/2)")
                .setView(dialogView)
                .setPositiveButton("Confirm and Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Here would be logic after entering info
                        showDialogPart2();
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();

        //Poplating the dialog to make it easier for user
        this.populateChangeInfoDialog(dialogView);
    }

    private void showDialogPart2() {

        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.dialog_change_or_create_task_2, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Task Info (2/2)")
                .setView(dialogView)
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Here would be logic after choosing tools
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();

        ListView list = (ListView) dialogView.findViewById(R.id.toolListView);
        String[] toolArray = this.getToolArray(familyToolList);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, toolArray);
        list.setAdapter(adapter);
    }

    private void populateChangeInfoDialog(View dialogView) {

        //Locating all the fields
        EditText taskName = (EditText)dialogView.findViewById(R.id.dialogTaskNameField);
        EditText taskTime = (EditText)dialogView.findViewById(R.id.dialogTimeNameField);
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
        calendar.setTime(presentTask.getDueDate());

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

}
