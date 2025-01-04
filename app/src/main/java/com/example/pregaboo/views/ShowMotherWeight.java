package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;

public class ShowMotherWeight extends AppCompatActivity {
    private Spinner weekSpinner;
    private EditText weightInput;
    private Button addWeightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mother_weight);

        initializeViews();
        setupWeekSpinner();
    }

    private void initializeViews() {
        weekSpinner = findViewById(R.id.weekSpinner);
        weightInput = findViewById(R.id.weightInput);
        addWeightButton = findViewById(R.id.addWeightButton);
    }

    private void setupWeekSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this,
            R.array.weeks_array,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(adapter);
    }
}