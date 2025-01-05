package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import com.example.pregaboo.R;

public class ShowMoms extends AppCompatActivity {

    private TableLayout momTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_moms);

        momTableLayout = findViewById(R.id.momTableLayout);

        // Retrieve the midwife ID from the intent
        String midwifeId = getIntent().getStringExtra("MIDWIFE_ID");
        if (midwifeId != null) {
            // Use the midwifeId as needed, e.g., to fetch data related to this midwife
        }

        if (momTableLayout != null) {
            // Your code to set tag or apply window insets
            ViewCompat.setOnApplyWindowInsetsListener(momTableLayout, (v, insets) -> {
                // Handle window insets
                return insets;
            });
        } else {
            // Handle the case where momTableLayout is null
            throw new NullPointerException("momTableLayout is null");
        }
    }
}