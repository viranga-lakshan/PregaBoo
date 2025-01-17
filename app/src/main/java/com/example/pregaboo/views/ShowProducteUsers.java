package com.example.pregaboo.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.ProductAdapter;
import com.example.pregaboo.models.Product;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;


public class ShowProducteUsers extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_producte_users);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        db = FirebaseFirestore.getInstance();
        fetchProducts();

        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchProducts() {
        db.collection("Seller")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                // Assuming products are stored in a sub-collection named "products"
                                db.collection("Seller").document(document.getId()).collection("products")
                                        .get()
                                        .addOnCompleteListener(productTask -> {
                                            if (productTask.isSuccessful()) {
                                                QuerySnapshot productQuerySnapshot = productTask.getResult();
                                                if (productQuerySnapshot != null) {
                                                    for (DocumentSnapshot productDoc : productQuerySnapshot.getDocuments()) {
                                                        String name = productDoc.getString("name");
                                                        double price = productDoc.getDouble("price");
                                                        String description = productDoc.getString("description");
                                                        String imageBase64 = productDoc.getString("imageBase64");
                                                        int warrantyPeriod = productDoc.getLong("warrantyPeriod").intValue();
                                                        String category = productDoc.getString("category");

                                                        Product product = new Product(name, description, imageBase64, null, price, warrantyPeriod, category);
                                                        productList.add(product);
                                                    }
                                                    productAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                            }
                        }
                    } else {
                        Toast.makeText(this, "Error fetching sellers: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void filter(String text) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter.filterList(filteredList);
    }
}