package familytaskmanager.microso.com.familytaskmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {


    TaskListAdapter taskListAdapter;

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


        //List view code
        ListView listView = (ListView) view.findViewById(R.id.tasksListView);

        taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), ((MainActivity)getActivity()).getFamilyTaskList());

        listView.setAdapter(taskListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Toast.makeText(getActivity(), "You clicked a view", Toast.LENGTH_SHORT).show();

                final Task clickedTask = (Task) parent.getItemAtPosition(position);


                Intent intent = new Intent(getActivity().getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("task", (Serializable) clickedTask);
                startActivity(intent);

            }
        });
        //End of List view code


        return view;
    }

    public void taskFabClicked() {
        Toast.makeText(getActivity(), "Task FAB clicked", Toast.LENGTH_SHORT).show();
    }

}
