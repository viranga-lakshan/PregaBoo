package com.example.pregaboo.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EachProducteDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_producte_details);

        ImageView productImage = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productOldPrice = findViewById(R.id.productOldPrice);
        TextView productDescription = findViewById(R.id.productDescription);
        TextView productWarranty = findViewById(R.id.productWarranty);
        TextView productCategory = findViewById(R.id.productCategory);

        // Get data from intent
        String name = getIntent().getStringExtra("PRODUCT_NAME");
        double price = getIntent().getDoubleExtra("PRODUCT_PRICE", 0);
        String imageUri = getIntent().getStringExtra("PRODUCT_IMAGE_URI");
        String description = getIntent().getStringExtra("PRODUCT_DESCRIPTION");
        int warrantyPeriod = getIntent().getIntExtra("PRODUCT_WARRANTY", 0);
        String category = getIntent().getStringExtra("PRODUCT_CATEGORY");

        // Set data to views
        productName.setText(name);
        productPrice.setText(String.format("LKR %.2f", price));
        productDescription.setText(description);
        productWarranty.setText(String.format("Warranty: %d months", warrantyPeriod));
        productCategory.setText(String.format("Category: %s", category));

        // Load the image from the file URI
        if (imageUri != null && !imageUri.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageUri);
            productImage.setImageBitmap(bitmap);
        } else {
            productImage.setImageResource(R.drawable.placeholder_image);
        }
    }
}