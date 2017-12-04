package familytaskmanager.microso.com.familytaskmanager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by walid on 2017-11-18.
 */

public class TaskListAdapter extends ArrayAdapter {

    private final Context context;
    private List<Task> values;
    private final List<User> userList;
    private final FragmentActivity activity;

    public TaskListAdapter(Context context, List<Task> values, List<User> userList,
                           FragmentActivity activity) {
        super(context, R.layout.task_list_item, values);
        this.context = context;
        this.values = values;
        this.userList = userList;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_list_item, parent, false);

        TextView taskName = (TextView) rowView.findViewById(R.id.taskListItemName);
        TextView taskNote = (TextView) rowView.findViewById(R.id.tasksListItemNote);
        ImageView taskIcon = (ImageView) rowView.findViewById(R.id.taskListItemIcon);

        Task task = values.get(position);

        taskName.setText(task.getTitle());

        //This is only place holder logic. Once I get UI done, make this fancier
        if (task.hasNote()) {
            taskNote.setText(task.getNote());
        } else {
            taskNote.setText("There is no note");
        }

        if (task.hasUser()) {
            //Getting profile pic
            String resourceName = task.getUser().getProfilePicResourceName();
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
            taskIcon.setImageResource(resourceId);
            //end of getting picture
        } else {
            taskIcon.setImageResource(android.R.drawable.ic_menu_add);
        }

        taskIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Had to make postion final to be able to use it here
                imageIconClicked(position);

            }
        });

        return rowView;
    }

    private void imageIconClicked(int position) {

        final Task clickedTask = values.get(position);

        if (clickedTask.hasUser()) {
            Toast.makeText(context, "Already Assigned", Toast.LENGTH_SHORT).show();
            return;
        }

        //Dialog code
        LayoutInflater inflater = LayoutInflater.from(activity);
        final View dialogView = inflater.inflate(R.layout.dialog_choose_user, null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        if (((MainActivity)activity).requestCurrentUser().getIsParent()) {
            alertDialogBuilder.setTitle("Choose User To Assign");
        } else {
            alertDialogBuilder.setTitle("You can only assign to yourself since you're not a parent");
        }
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        //end dialog code

        //List view code
        ListView userListView = (ListView) dialogView.findViewById(R.id.dialogUserList);

        PeopleListAdapter peopleListAdapter = new PeopleListAdapter(context, userList);
        userListView.setAdapter(peopleListAdapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                alertDialog.dismiss();
                final User clickedUser = (User) parent.getItemAtPosition(position);

                //if parent or the user chose himself
                User currentUser = ((MainActivity)activity).requestCurrentUser();
                if(currentUser.getIsParent()
                        || currentUser.getId().equals(clickedUser.getId())) {
                    askAssignmentConfirmation(clickedUser, clickedTask);
                } else {
                    Toast.makeText(activity, "Please either assign to yourself of " +
                            "ask your Parent to assign the Task", Toast.LENGTH_SHORT).show();

                    alertDialog.cancel();
                }

            }
        });
        //End of List view code

    }

    /**
     * Method called when user chose who to assign task to.
     */
    private void askAssignmentConfirmation(final User userToAssign, final Task clickedTask) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("Confirm allocation")
                .setMessage("Task will be assigned to " + userToAssign.getFname() + "\n"
                        + "Note: \n - " + clickedTask.getNote())
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        clickedTask.setUser(userToAssign); //Should also assign task to user
                        ((MainActivity)activity).requestTaskUpdate(clickedTask);
                        ((MainActivity)activity).requestUserUpdate(userToAssign);
                        notifyDataSetChanged();
                        //TODO this is a quick fix. Finding more elegant solution would be nice
                        ((MainActivity)activity).onStart();
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

}
