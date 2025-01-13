package com.example.pregaboo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.models.Post;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);


        if (post.getText() != null && !post.getText().isEmpty()) {
            holder.postText.setVisibility(View.VISIBLE);
            holder.postText.setText(post.getText());
        } else {
            holder.postText.setVisibility(View.GONE);
        }

        // Set post image from Base64 string
        String base64Image = post.getImageBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
            holder.imageView.setVisibility(View.VISIBLE);
            byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        // Format and set the timestamp
        if (post.getTimestamp() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String formattedDate = sdf.format(post.getTimestamp().toDate());
            holder.timestampTextView.setText(formattedDate);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView postText;
        TextView timestampTextView;

        public PostViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.postImage);
            postText = itemView.findViewById(R.id.postText);
            timestampTextView = itemView.findViewById(R.id.postTimestamp);
        }
    }
}