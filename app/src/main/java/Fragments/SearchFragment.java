package Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flixster.R;

import java.net.InetAddress;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SearchFragment extends Fragment {

    private EditText etSearchBox;
    private Button btnSearchMovie;
    private TextView movieErrorMsg;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etSearchBox = getActivity().findViewById(R.id.et_search_box);
        btnSearchMovie = getActivity().findViewById(R.id.btn_search_movie);
        movieErrorMsg = view.findViewById(R.id.searchError);
        Log.i("etSearchBox","this is: "+etSearchBox);
        Log.i("btnSearchMovie","this is: "+btnSearchMovie);
        // Set on click listener on button
        btnSearchMovie.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                submitSearch();
            }
        });
    }

    /**
     * Submits a search and starts a search result activity.
     */
    private void submitSearch() {
        //Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        String TMDBQuery = etSearchBox.getText().toString();

        // Passing data to the Search Results activity
        Fragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString("SearchQuery", TMDBQuery);
        fragment.setArguments(args);
        replaceFragment(fragment);

    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}