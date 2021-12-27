package com.example.getstock;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link showPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 * Will be used to show profile of broker.
 */
public class showPostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView title;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment show_post_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static showPostFragment newInstance(String param1, String param2) {
        showPostFragment fragment = new showPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public showPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        title.setText("People with omicron are up to 70% less likely to require admission to hospital than those with the delta variant, according to a U.K. government study.\n" +
//                "Of those admitted to hospital with omicron, 17 people had received their boosters, 74 were double vaccinated, and 27 were unvaccinated.\n" +
//                "The analysis is “preliminary and highly uncertain” due to the small numbers of omicron cases currently in hospital, among other factors, Britain’s Health Security Agency said.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_post_, container, false);
        title = view.findViewById(R.id.actual_post);

        return view;
    }
}