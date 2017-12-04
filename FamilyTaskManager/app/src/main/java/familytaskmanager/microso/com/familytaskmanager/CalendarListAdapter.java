package familytaskmanager.microso.com.familytaskmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jean-Gabriel on 12/4/2017.
 */

public class CalendarListAdapter extends ArrayAdapter<Task> {
    private final Context context;
    private List<Task> values;

    public CalendarListAdapter(Context context, List<Task> values) {
        super(context, R.layout.task_list_item, values);
        this.context = context;
        this.values = values;
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

        return rowView;
    }
}
