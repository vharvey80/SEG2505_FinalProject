package ca.uottawa.vince_pc.finalproject_seg2505;

/**
 * Created by Vince-PC on 2017-11-06.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] names;
    private final Integer[] imageIds;

    public UserList (Activity context, String[] names, Integer[] imageIds) {
        super(context, R.layout.single_list, names);
        this.context = context;
        this.names = names;
        this.imageIds = imageIds;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(names[position]);

        imageView.setImageResource(imageIds[position]);
        return rowView;
    }
}
