package com.example.pregaboo.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;

public class KickCounterActivity extends AppCompatActivity {
    private TextView kickCountText;
    private Button countKickButton;
    private int kickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kick_counter);

        kickCountText = findViewById(R.id.kickCountText);
        countKickButton = findViewById(R.id.countKickButton);

        countKickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kickCount++;
                kickCountText.setText(String.valueOf(kickCount));
            }
        });
    }
}
