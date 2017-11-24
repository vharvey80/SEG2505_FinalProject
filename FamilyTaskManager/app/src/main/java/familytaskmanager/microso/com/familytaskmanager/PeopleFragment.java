package familytaskmanager.microso.com.familytaskmanager;


import android.os.Bundle;
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

        peopleListAdapter = new PeopleListAdapter(getActivity().getApplicationContext(), ((MainActivity)getActivity()).getFamilyPeopleList());
        listView.setAdapter(peopleListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Toast.makeText(getActivity(), "You clicked a view", Toast.LENGTH_SHORT).show();

                final User clickedUser = (User) parent.getItemAtPosition(position);


                Intent intent = new Intent(getActivity().getApplicationContext(), UserActivity.class);
                intent.putExtra("user", (Serializable) clickedUser);
                /*Bundle b = new Bundle();
                ArrayList<Task> taskList = (ArrayList<Task>) ((MainActivity) getActivity()).getFamilyTaskList();
                b.putSerializable("list",taskList);
                intent.putExtra("list", b);*/
                startActivity(intent);

            }
        });
        //End of List view code

        return view;
    }

}
