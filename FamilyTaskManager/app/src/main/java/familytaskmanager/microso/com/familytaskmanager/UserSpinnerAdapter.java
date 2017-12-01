package familytaskmanager.microso.com.familytaskmanager;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by walid on 2017-11-22.
 */

public class UserSpinnerAdapter extends BaseAdapter {

    private final Context context;
    private final List<User> values;

    public UserSpinnerAdapter(Context context, List<User> values) {
        super();
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_spinner_item_dropped, parent, false);

        TextView userName = (TextView) rowView.findViewById(R.id.userSpinnerItemName);
        ImageView userIcon = (ImageView) rowView.findViewById(R.id.userSpinnerItemIcon);

        User user = values.get(position);

        userName.setText(user.getFname());
        //Getting profile pic
        String resourceName = user.getProfilePicResourceName();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
        userIcon.setImageResource(resourceId);
        //end of getting picture

        return rowView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_spinner_undroped, parent, false);

        return rowView;
    }
}
