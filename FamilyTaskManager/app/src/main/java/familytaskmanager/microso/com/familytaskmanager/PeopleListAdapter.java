package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 2017-11-22.
 */

public class PeopleListAdapter extends ArrayAdapter{

    private final Context context;
    private final List<User> values;

    public PeopleListAdapter(Context context, List<User> values) {
        super(context, R.layout.people_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.people_list_item, parent, false);

        TextView peopleName = (TextView) rowView.findViewById(R.id.peopleListItemName);
        TextView peopleNumTasks = (TextView) rowView.findViewById(R.id.peopleListItemNumTasks);
        ImageView peopleIcon = (ImageView) rowView.findViewById(R.id.peopleListItemIcon);

        User people = values.get(position);

        peopleName.setText(people.getFname()+" "+people.getLname());
        peopleNumTasks.setText("Allocated tasks: "+people.getTasks().size());
        peopleIcon.setImageResource(people.getProfilePicId());
    }
}
