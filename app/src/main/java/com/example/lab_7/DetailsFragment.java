package com.example.lab_7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String NAME_PARAM = "name";
    private static final String HEIGHT_PARAM = "height";
    private static final String MASS_PARAM = "mass";

    // TODO: Rename and change types of parameters
    private String getNameParam;
    private String getHeightParam;
    private String getMassParam;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2, String param3) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(NAME_PARAM, param1);
        args.putString(HEIGHT_PARAM, param2);
        args.putString(MASS_PARAM, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getNameParam = getArguments().getString(NAME_PARAM);
            getHeightParam = getArguments().getString(HEIGHT_PARAM);
            getMassParam = getArguments().getString(MASS_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView nameTextView = view.findViewById(R.id.name_text);
        TextView heightTextView = view.findViewById(R.id.height_text);
        TextView massTextView = view.findViewById(R.id.mass_text);

        nameTextView.setText(getNameParam != null ? getNameParam : "Unknown");
        heightTextView.setText(getHeightParam != null ? getHeightParam : "Unknown");
        massTextView.setText(getMassParam != null ? getMassParam : "Unknown");

        return view;
    }
}