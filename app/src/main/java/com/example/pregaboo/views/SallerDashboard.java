package com.example.pregaboo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;

public class SallerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saller_dashboard);

        // Get the seller ID from the intent
        String sellerId = getIntent().getStringExtra("SELLER_ID");
        if (sellerId != null) {
            // Use the seller ID as needed
            Toast.makeText(this, "Seller ID: " + sellerId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Seller ID found", Toast.LENGTH_SHORT).show();
        }

        // Set up the button click listener
        Button btnAddProducts = findViewById(R.id.btnAddProducts);
        btnAddProducts.setOnClickListener(v -> {
            Intent intent = new Intent(SallerDashboard.this, SellerAddProducte.class);
            intent.putExtra("SELLER_ID", sellerId);
            startActivity(intent);
        });
    }
}