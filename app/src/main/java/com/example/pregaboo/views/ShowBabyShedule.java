package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.ClinicDateAdapter;
import com.example.pregaboo.models.ClinicDate;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ShowBabyShedule extends AppCompatActivity {
    private RecyclerView clinicDateRecyclerView;
    private ClinicDateAdapter clinicDateAdapter;
    private List<ClinicDate> clinicDateList;
    private FirebaseFirestore db;
    private String userId;
    private String babyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_baby_shedule);

        db = FirebaseFirestore.getInstance();
        clinicDateRecyclerView = findViewById(R.id.dates_recycler_view);

        userId = getIntent().getStringExtra("USER_ID");
        babyId = getIntent().getStringExtra("BABY_ID");

        if (babyId == null || userId == null) {
            Toast.makeText(this, "Error: Baby ID or User ID is missing.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        clinicDateList = new ArrayList<>();
        clinicDateAdapter = new ClinicDateAdapter(clinicDateList, this);
        clinicDateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        clinicDateRecyclerView.setAdapter(clinicDateAdapter);

        loadClinicDates();
    }

    private void loadClinicDates() {
        db.collection("users")
                .document(userId)
                .collection("babies")
                .document(babyId)
                .collection("BabyClinicSchedule")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        clinicDateList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ClinicDate clinicDate = document.toObject(ClinicDate.class);
                            clinicDate.setId(document.getId());
                            clinicDateList.add(clinicDate);
                        }
                        clinicDateAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ShowBabyShedule.this, "Error loading clinic dates.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}