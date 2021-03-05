package com.amit.opinverse;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentAssignment extends Fragment {
    AssingmentAdapter assingmentAdapter;
    LinearLayoutManager linearLayoutManager;
    List<AssignmentModel> assignmentModels;
    RecyclerView assignmentRecycler;

    public FragmentAssignment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assignment, container, false);

        assignmentModels = new ArrayList<>();
        assignmentModels.add(new AssignmentModel("Assignment 1", "Assignment Task 1", "Lorem Ipsum dolor sit amet, consectetur, adipiscing elit, Nunc maximus, nulla ut, commodo sagitis, sapien dui, mattis dui, non",
                4, R.drawable.certificate));
        assignmentModels.add(new AssignmentModel("Assignment 2", "Assignment Task 2", "Lorem Ipsum dolor sit amet, consectetur, adipiscing elit, Nunc maximus, nulla ut, commodo sagitis, sapien dui, mattis dui, non",
                2, R.drawable.certificate));

        assignmentRecycler = v.findViewById(R.id.assgnRecycler);
        linearLayoutManager = new LinearLayoutManager(v.getContext());
        assingmentAdapter = new AssingmentAdapter(v.getContext(), assignmentModels);
        assignmentRecycler.setLayoutManager(linearLayoutManager);
        assignmentRecycler.setAdapter(assingmentAdapter);

        return v;
    }
}