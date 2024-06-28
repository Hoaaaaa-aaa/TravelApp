package vn.com.travel.travelapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.com.travel.travelapp.Adapter.ListAdapter;
import vn.com.travel.travelapp.Adapter.PopularAdapter;
import vn.com.travel.travelapp.Adapter.RecommendedAdapter;
import vn.com.travel.travelapp.Domain.ItemDomain;
import vn.com.travel.travelapp.R;
import vn.com.travel.travelapp.databinding.ActivityPopularListBinding;

public class PopularListActivity extends BaseActivity {

    TextView txtSearch;
    EditText edtSearch;
    ActivityPopularListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopularListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        edtSearch = findViewById(R.id.edtSearch);
        txtSearch = findViewById(R.id.txtid);

        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edtSearch.getText().toString().trim();

                if (!query.isEmpty()) {
                    searchPopularItems(query);
                } else {
                    Toast.makeText(PopularListActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                }
            }
        });

        initPopular();
    }

    private void searchPopularItems(String query) {
        DatabaseReference myRef = database.getReference("Popular");
        binding.progressBarList.setVisibility(View.VISIBLE);
        ArrayList<ItemDomain> list = new ArrayList<>();
        Query searchQuery = myRef.orderByChild("title").equalTo(query);

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot issue: dataSnapshot.getChildren()) {
                    ItemDomain item = issue.getValue(ItemDomain.class);
                    if (item != null) {
                        list.add(item);
                    }
                }

                if (list.isEmpty()) {
                    edtSearch.setHint("No results found");
                } else {
                    binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(PopularListActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    RecyclerView.Adapter adapter = new PopularAdapter(list);
                    binding.recyclerViewSearch.setAdapter(adapter);
                    binding.recyclerViewSearch.setVisibility(View.VISIBLE);
                    binding.recyclerViewList.setVisibility(View.GONE);
                }
                binding.progressBarList.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PopularListActivity.this, "Search failed: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPopular() {
        DatabaseReference myRef = database.getReference("Popular");
        binding.backBtn.setOnClickListener(v -> finish());
        binding.progressBarList.setVisibility(View.VISIBLE);
        ArrayList<ItemDomain> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(ItemDomain.class));
                    }
                    if (!list.isEmpty()) {
                        binding.recyclerViewList.setLayoutManager(new LinearLayoutManager(PopularListActivity.this, LinearLayoutManager.VERTICAL, false));
                        RecyclerView.Adapter adapter = new ListAdapter(list);
                        binding.recyclerViewList.setAdapter(adapter);
                    }
                    binding.progressBarList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}