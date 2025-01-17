package com.example.pregaboo.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SellerAddProducte extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private EditText editProductName, editProductDescription, editProductPrice, editWarrantyPeriod;
    private ImageView productImageView;
    private Button buttonSelectImage, buttonAddProduct;
    private Uri imageUri;
    private FirebaseFirestore db;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seller_add_producte);

        editProductName = findViewById(R.id.editProductName);
        editProductDescription = findViewById(R.id.editProductDescription);
        editProductPrice = findViewById(R.id.editProductPrice);
        editWarrantyPeriod = findViewById(R.id.editWarrantyPeriod);
        productImageView = findViewById(R.id.productImageView);

        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        db = FirebaseFirestore.getInstance();

        buttonSelectImage.setOnClickListener(v -> openGallery());

        spinnerCategory = findViewById(R.id.spinnerCategory);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        buttonAddProduct.setOnClickListener(v -> {
            String sellerId = getIntent().getStringExtra("SELLER_ID");
            addProduct(sellerId);
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            if (imageUri != null) {
                productImageView.setImageURI(imageUri);
            } else {
                Toast.makeText(this, "Failed to get image URI", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addProduct(String sellerId) {
        String name = editProductName.getText().toString().trim();
        String description = editProductDescription.getText().toString().trim();
        double price = Double.parseDouble(editProductPrice.getText().toString().trim());
        int warrantyPeriod = Integer.parseInt(editWarrantyPeriod.getText().toString().trim());
        String category = spinnerCategory.getSelectedItem().toString();

        // Check if imageUri is not null before using it
        if (imageUri != null) {
            // Convert image to Base64
            Bitmap bitmap = ((BitmapDrawable) productImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            String imageUriString = imageUri.toString(); // Use a different variable name
            Product product = new Product(name, description, imageBase64, imageUriString, price, warrantyPeriod, category);

            // Use the seller ID to add the product under the seller's sub-collection
            db.collection("Seller").document(sellerId).collection("products")
                    .add(product)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(SellerAddProducte.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(SellerAddProducte.this, "Error adding product: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Please select an image before adding the product.", Toast.LENGTH_SHORT).show();
        }
    }
}