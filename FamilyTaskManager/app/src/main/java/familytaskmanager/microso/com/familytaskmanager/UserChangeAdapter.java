package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince-PC on 2017-11-22.
 */

public class UserChangeAdapter extends ArrayAdapter<User> {
    private final Context context;
    //private final ArrayList<User> users;
    private final List<User> users;
    private User selected_user;

    public UserChangeAdapter(Context context, List<User> users) {
        super(context, R.layout.user_change_list_item, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        selected_user = users.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_change_list_item, parent, false);

        TextView nameView = (TextView) rowView.findViewById(R.id.userchangelistname);
        TextView ptsView = (TextView) rowView.findViewById(R.id.userchangelistpoints);
        ImageView pic = (ImageView) rowView.findViewById(R.id.userchangelistpic);
        nameView.setText(selected_user.getFname());
        ptsView.setText(Integer.toString(selected_user.getAccumulatedPts()));

        String resourceName = selected_user.getProfilePicResourceName();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
        pic.setImageResource(resourceId);
        return rowView;
    }

}
