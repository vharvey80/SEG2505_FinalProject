package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.SQLOutput;

public class TaskDetailActivity extends AppCompatActivity {

    private Task presentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        presentTask = (Task) intent.getSerializableExtra("task");

        //Setting the title to the task from which the activity was called
        if (presentTask != null) {
            setTitle(presentTask.getTitle());
        }else {
            setTitle("Task");
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
        TextView dueDateField = (TextView) findViewById(R.id.limitDateText);
        dueDateField.setText(presentTask.getDueDate().toString());
        TextView creatorNameField = (TextView) findViewById(R.id.creatorName);
        creatorNameField.setText(presentTask.getCreator().getFname());

        //Setting the note
        TextView noteText = (TextView) findViewById(R.id.noteText);
        noteText.setText(presentTask.getNote());

    }
}
