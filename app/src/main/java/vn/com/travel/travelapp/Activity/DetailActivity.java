package vn.com.travel.travelapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import vn.com.travel.travelapp.Domain.ItemDomain;
import vn.com.travel.travelapp.databinding.ActivityDetailBinding;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private ItemDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText(object.getPrice());
        binding.backBtn.setOnClickListener(v -> finish());
        binding.bedTxt.setText(object.getBed());
        binding.durationTxt.setText(object.getDistance());
        binding.descriptionTxt.setText(object.getDescription());
        binding.addressTxt.setText(object.getAddress());
        binding.ratingTxt.setText(object.getScore() + " Rating");
        binding.ratingBar.setRating((float) object.getScore());

        Glide.with(DetailActivity.this)
                .load(object.getPic())
                .into(binding.pic);

        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getIntentExtra() {
        object = (ItemDomain) getIntent().getSerializableExtra("object");
    }
}