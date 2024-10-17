package com.example.kmtapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.SearchView; // Make sure you use this import instead of android.widget.SearchView

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {

    private SearchView searchBar; // This now refers to the correct SearchView class
    private CardView cardNearestStops, cardMap;
    private TextView stop1, stop2, stop3;
    private ImageView mapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize UI components
        searchBar = findViewById(R.id.search_bar);
        cardNearestStops = findViewById(R.id.card_nearest_stops);
        cardMap = findViewById(R.id.card_map);
        stop1 = findViewById(R.id.stop_1);
        stop2 = findViewById(R.id.stop_2);
        stop3 = findViewById(R.id.stop_3);
        mapImage = findViewById(R.id.mapImage);  // Initialize mapImage

        // Set sample map image - Replace this with Google Maps API or a real map later
        mapImage.setImageResource(R.drawable.sample_map);

        // Add search bar functionality
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                Toast.makeText(HomePageActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text change in the search bar
                return false;
            }
        });

        // Set sample bus stop data - Replace this with dynamic data in the future
        stop1.setText("Bharati Vidyapeeth");
        stop2.setText("Kandalgaon");
        stop3.setText("R.K Nagar");
    }
}
