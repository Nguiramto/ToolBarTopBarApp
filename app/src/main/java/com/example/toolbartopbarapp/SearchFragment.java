package com.example.toolbartopbarapp;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShortsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

// Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);

// Set LayoutManager
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        );

// Initialize adapter
        categoryAdapter = new CategoryAdapter(getContext());

// Set data
        categoryAdapter.setData(getListCategory());

// Attach adapter
        recyclerView.setAdapter(categoryAdapter);

        return view;
    }

    private List<Category> getListCategory() {

        List<Category> listCategory = new ArrayList<>();

        List<CardItem> listItems = new ArrayList<>();

        listItems.add(new CardItem(R.drawable.heart_health, "Heart Health", "Monitor your heart rate"));
        listItems.add(new CardItem(R.drawable.doctor_consultation, "Consultation", "Talk to certified doctors"));
        listItems.add(new CardItem(R.drawable.medicatiom_reminder, "Medication", "Track your daily meds"));
        listItems.add(new CardItem(R.drawable.lab_results, "Lab Results", "View your test reports"));
        listItems.add(new CardItem(R.drawable.emergency_help, "Emergency", "Get help instantly"));
        listItems.add(new CardItem(R.drawable.healthy_food, "Nutrition", "Eat healthy daily"));
        listItems.add(new CardItem(R.drawable.mental_wellness, "Mental Health", "Care for your mind"));
        listItems.add(new CardItem(R.drawable.stay_active, "Stay Active", "Keep your body fit"));

        listCategory.add(new Category("Health Services", listItems));
        listCategory.add(new Category("Wellness", listItems));
        listCategory.add(new Category("Quick Access", listItems));

        return listCategory;
    }
}