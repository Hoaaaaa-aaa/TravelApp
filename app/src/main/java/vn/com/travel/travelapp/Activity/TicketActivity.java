package vn.com.travel.travelapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.zip.Inflater;

import vn.com.travel.travelapp.Domain.ItemDomain;
import vn.com.travel.travelapp.databinding.ActivityTicketBinding;

public class TicketActivity extends BaseActivity{
    ActivityTicketBinding binding;
    private ItemDomain object;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();

        setVariable();
    }
    private void setVariable(){
        Glide.with(TicketActivity.this)
                .load(object.getPic())
                .into(binding.pic);
        Glide.with(TicketActivity.this)
                .load(object.getTourGuide())
                .into(binding.profile);
        binding.btnback.setOnClickListener(v -> finish());;
        binding.txtTitle.setText(object.getTitle());
        binding.txtDuration.setText(object.getDuration());
        binding.txtTourGuide.setText(object.getDateTour());
        binding.txtTime.setText(object.getTourGuideName());
        binding.btncall.setOnClickListener(v -> {
            Intent sendIntent=new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"+ object.getTourGuidePhone() ));
            sendIntent.putExtra("sms_body", "type your message");
            startActivity(sendIntent);
        });
        binding.btnmess.setOnClickListener(v -> {
            String phone=object.getTourGuidePhone();
            Intent intent=new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel", phone,null));
            startActivity(intent);
        });
    }
    private void getIntentExtra(){
        object=(ItemDomain)getIntent().getSerializableExtra("object");
    }
}

