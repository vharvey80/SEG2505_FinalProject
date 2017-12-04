package familytaskmanager.microso.com.familytaskmanager;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince-PC on 2017-11-22.
 */

public class UserChangeAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> users;
    private User selected_user;
    private Activity activity;
    private TextView nameView;
    private TextView ptsView;
    private ImageView pic;

    public UserChangeAdapter(Context context, List<User> users, Activity activity) {
        super(context, R.layout.user_change_list_item, users);
        this.context = context;
        this.users = users;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        selected_user = users.get(position);
        User current = ((MainActivity)activity).getCurrentUser();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_change_list_item, parent, false);

        nameView = (TextView) rowView.findViewById(R.id.userchangelistname);
        ptsView = (TextView) rowView.findViewById(R.id.userchangelistpoints);
        pic = (ImageView) rowView.findViewById(R.id.userchangelistpic);

        //set the views so they don't reset when the alert dialogue opens up
        nameView.setText(current.getFname());
        ptsView.setText(Integer.toString(current.getAccumulatedPts()));
        String resourceName = selected_user.getProfilePicResourceName();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
        pic.setImageResource(resourceId);

        //Open up dialog to enter password
        if(!selected_user.getId().equals(current.getId())){
            authentication(position);
        } else{
            //set the current user for the family on start
            changeUser(position);
        }
        return rowView;
    }

    //is called when the password is correct to set the views and change the current user in the family
    private void changeUser(int position) {
        nameView.setText(selected_user.getFname());
        ptsView.setText(Integer.toString(selected_user.getAccumulatedPts()));

        //TODO fix this in master
        String resourceName = selected_user.getProfilePicResourceName();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(resourceName, "drawable", "familytaskmanager.microso.com.familytaskmanager");
        pic.setImageResource(resourceId);

        //Set the selected user as the current user in the database and family
        ((MainActivity) activity).requestSetCurrentUser(position);
    }

    private void authentication(final int position) {
        //creates the dialogue box
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.password_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
            /* PERSONALIZATION OF THE DIALOG (REUSABILITY) */
        final TextView add_title = (TextView) mView.findViewById(R.id.add_title);
        add_title.setText("Please Enter Your Password"); // personalize the title of your dialog
            /* END OF PERSONALIZATION */

        //4 number pickers 0-9 to enter a PIN
        final NumberPicker pin1 = (NumberPicker) mView.findViewById(R.id.PINPicker1);
        pin1.setMaxValue(9);
        pin1.setMinValue(0);
        final NumberPicker pin2 = (NumberPicker) mView.findViewById(R.id.PINPicker2);
        pin2.setMaxValue(9);
        pin2.setMinValue(0);
        final NumberPicker pin3 = (NumberPicker) mView.findViewById(R.id.PINPicker3);
        pin3.setMaxValue(9);
        pin3.setMinValue(0);
        final NumberPicker pin4 = (NumberPicker) mView.findViewById(R.id.PINPicker4);
        pin4.setMaxValue(9);
        pin4.setMinValue(0);

        Button enter_btn = (Button) mView.findViewById(R.id.enter_btn);
        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if password is correct, call change user
                int num1 = pin1.getValue();
                int num2 = pin2.getValue();
                int num3 = pin3.getValue();
                int num4 = pin4.getValue();
                String password = ""+num1+num2+num3+num4;

                if(password.equals(selected_user.getPassword())) {
                    changeUser(position);
                    dialog.dismiss();
                } else {
                    Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button cancel_btn = (Button) mView.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
