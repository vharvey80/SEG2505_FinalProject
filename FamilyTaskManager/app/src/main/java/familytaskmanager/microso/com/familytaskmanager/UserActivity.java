package familytaskmanager.microso.com.familytaskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class UserActivity extends AppCompatActivity {

    private User selectedUser;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        selectedUser = (User) intent.getSerializableExtra("user");

        //Get the user list of tasks
         taskList = selectedUser.getTasks();

        //Setting the title to the task from which the activity was called
        if (selectedUser != null) {
            setTitle(selectedUser.getFname());
        }else {
            setTitle("User");
        }

        //Get the views from the layout
        TextView userName = (TextView) findViewById(R.id.userName);
        ImageView userIcon = (ImageView) findViewById(R.id.userIcon);


        //Set user info into the views
        String s = selectedUser.getFname()+" "+selectedUser.getLname();
        userName.setText(s);
        userIcon.setImageResource(selectedUser.getProfilePicId());

        //Set tasks list view
        ListView listView = (ListView) findViewById(R.id.currentTasksListView);
        //IMPORTANT! I passed null as an argument for the moment, since we focus on UI for now
        //This will need to be fixed (need to find best solution).
        TaskListAdapter taskListAdapter = new TaskListAdapter(this.getApplicationContext(), taskList, null, this);
        listView.setAdapter(taskListAdapter);
    }
}
