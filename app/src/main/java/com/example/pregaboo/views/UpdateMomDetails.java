package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.pregaboo.R;

public class UpdateMomDetails extends AppCompatActivity {
    private String momId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_mom_details);
        
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e("UpdateMomDetails", "View with ID 'main' not found.");
        }

        momId = getIntent().getStringExtra("MOM_ID");
        if (momId != null) {
            Log.d("UpdateMomDetails", "Received MOM_ID: " + momId);
        } else {
            Log.e("UpdateMomDetails", "MOM_ID not found in intent.");
        }

        Button addChildButton = findViewById(R.id.addChildButton);
        addChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateMomDetails.this, CreatBabyAccount.class);
                intent.putExtra("MOM_ID", momId);
                startActivity(intent);
            }
        });
    }
}