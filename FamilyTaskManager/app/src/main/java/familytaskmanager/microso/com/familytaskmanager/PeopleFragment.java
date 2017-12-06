package familytaskmanager.microso.com.familytaskmanager;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleFragment extends Fragment {

    PeopleListAdapter peopleListAdapter;


    public PeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_people, container, false);

        //List view code
        ListView listView = (ListView) view.findViewById(R.id.peopleListView);

        // Get floating action button from view
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addUserFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskFabClicked();
            }
        });



        peopleListAdapter = new PeopleListAdapter(getActivity().getApplicationContext(), ((MainActivity)getActivity()).getFamilyPeopleList());
        for(User u : ((MainActivity)getActivity()).getFamilyPeopleList()) {
        }
        listView.setAdapter(peopleListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                final User clickedUser = (User) parent.getItemAtPosition(position);


                Intent intent = new Intent(getActivity().getApplicationContext(), UserActivity.class);
                intent.putExtra("user", (Serializable) clickedUser);
                intent.putExtra("currentUserIsParent", (Serializable) ((MainActivity)getActivity()).getCurrentUser().getIsParent());
                getActivity().startActivityForResult(intent, MainActivity.USER_REQUEST_CODE);
            }
        });
        //End of List view code

        return view;
    }

    /**
     * Method to open create new User activity on floating action button click
     */
    public void taskFabClicked() {
        int requestCode = 4;
        Intent intent = new Intent(getActivity().getApplication().getApplicationContext(), UserModifyActivity.class);
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("currentUserIsParent", (Serializable) ((MainActivity)getActivity()).getCurrentUser().getIsParent());
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            User newUser = (User) data.getSerializableExtra("user");
            ((MainActivity) getContext()).family.addUser(newUser);
        }
    }

}
