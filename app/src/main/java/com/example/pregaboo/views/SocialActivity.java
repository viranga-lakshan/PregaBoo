package com.example.pregaboo.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pregaboo.R;
import com.example.pregaboo.adapters.PostAdapter;
import com.example.pregaboo.models.Post;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SocialActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Post> posts = new ArrayList<>();
        PostAdapter adapter = new PostAdapter(posts);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Post post = document.toObject(Post.class);
                    post.setTimestamp(document.getTimestamp("timestamp"));

                    // Set the Base64 string directly
                    String base64Image = document.getString("imageBase64");
                    if (base64Image != null) {
                        post.setImageBase64(base64Image); // Set the Base64 string
                    }

                    posts.add(post);
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(SocialActivity.this, "Error getting posts: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}