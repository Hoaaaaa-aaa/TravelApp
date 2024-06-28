package vn.com.travel.travelapp.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.com.travel.travelapp.Adapter.ListAdapter;
import vn.com.travel.travelapp.Adapter.PopularAdapter;
import vn.com.travel.travelapp.Adapter.RecommendedAdapter;
import vn.com.travel.travelapp.Domain.ItemDomain;
import vn.com.travel.travelapp.databinding.ActivityRecommendedListBinding;

public class RecommendedListActivity extends BaseActivity {

    ActivityRecommendedListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecommendedListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecommended();
    }

    private void initRecommended() {
        DatabaseReference myRef = database.getReference("Item");
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
                        binding.recyclerViewList.setLayoutManager(new LinearLayoutManager(RecommendedListActivity.this, LinearLayoutManager.VERTICAL, false));
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