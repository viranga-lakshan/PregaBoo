package com.example.pregaboo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Product;
import java.util.List;
import android.util.Base64;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.content.Intent;
import com.example.pregaboo.views.EachProducteDetails;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("$%.2f", product.getPrice()));

        // Decode Base64 string to Bitmap
        String base64Image = product.getImageBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.productImage.setImageBitmap(decodedByte);
        } else {
            // Set a placeholder image if no image is available
            holder.productImage.setImageResource(R.drawable.placeholder_image);
        }

        // Set click listener on the item view
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EachProducteDetails.class);
            intent.putExtra("PRODUCT_NAME", product.getName());
            intent.putExtra("PRODUCT_PRICE", product.getPrice());
            intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
            intent.putExtra("PRODUCT_WARRANTY", product.getWarrantyPeriod());
            intent.putExtra("PRODUCT_CATEGORY", product.getCategory());

            // Save the image to a file and pass the file URI
            if (base64Image != null && !base64Image.isEmpty()) {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                try {
                    File file = new File(context.getCacheDir(), "product_image.png");
                    FileOutputStream fos = new FileOutputStream(file);
                    decodedByte.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    intent.putExtra("PRODUCT_IMAGE_URI", file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void filterList(List<Product> filteredList) {
        productList = filteredList;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}