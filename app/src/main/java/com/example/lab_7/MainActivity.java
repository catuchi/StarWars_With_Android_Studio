package com.example.lab_7;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> characterList = new ArrayList<>();
    private ListView listView;
    private CharacterAdapter characterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        characterAdapter = new CharacterAdapter(this, characterList);
        listView.setAdapter(characterAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, String> selectedCharacter = characterList.get(position);
            String name = selectedCharacter.get("name");
            String height = selectedCharacter.get("height");
            String mass = selectedCharacter.get("mass");

            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("height", height);
            bundle.putString("mass", mass);

            if (findViewById(R.id.frame_layout) != null) {
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, detailsFragment)
                        .commit();
            } else {
                Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        new FetchCharactersTask().execute("https://swapi.dev/api/people/?format=json");
    }

    private class FetchCharactersTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray resultsArray = jsonObject.getJSONArray("results");

                // Parse each character's data
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject character = resultsArray.getJSONObject(i);
                    String name = character.getString("name");
                    String height = character.getString("height");
                    String mass = character.getString("mass");

                    HashMap<String, String> characterInfo = new HashMap<>();
                    characterInfo.put("name", name);
                    characterInfo.put("height", height);
                    characterInfo.put("mass", mass);

                    characterList.add(characterInfo);
                }

                characterAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void passDataToFragment(ArrayList<HashMap<String, String>> characterList) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("characterList", characterList);
        detailsFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, detailsFragment);
        transaction.commit();
    }
}
