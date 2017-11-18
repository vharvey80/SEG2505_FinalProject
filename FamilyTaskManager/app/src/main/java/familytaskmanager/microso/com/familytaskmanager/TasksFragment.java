package familytaskmanager.microso.com.familytaskmanager;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {


    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        //Setting onClick for the FAB
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addTaskFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskFabClicked();
            }
        });

        return view;
    }

    public void taskFabClicked() {
        Toast.makeText(getActivity(), "Task FAB clicked", Toast.LENGTH_SHORT).show();
    }

}
