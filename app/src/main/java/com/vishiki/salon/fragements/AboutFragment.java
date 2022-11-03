package com.vishiki.salon.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.vishiki.salon.R;

import java.util.ArrayList;

public class AboutFragment extends Fragment {

    ImageSlider image_slider;
    ArrayList<SlideModel> imageList =new ArrayList<>();

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        image_slider = view.findViewById(R.id.image_slider);
        imageList.add(new SlideModel("https://www.shutterstock.com/image-photo/barber-applies-black-charcoal-mask-600w-1690328941.jpg", "Skin Care", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://img.freepik.com/premium-photo/young-man-receiving-waxing-chest-by-young-female-cosmetologist-beauty-salon-portrait-young-female-cosmetologist-during-waxing-male-chest_141172-4422.jpg", "Waxing" ,ScaleTypes.FIT));
        imageList.add(new SlideModel("https://previews.123rf.com/images/innareznik/innareznik2009/innareznik200900176/156060875-little-girl-having-her-hair-cut-little-girl-sitting-in-beauty-hair-salon-style-for-children-.jpg", "Hair Trim", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://iconichairdressing.com/wp-content/uploads/2019/09/foils-1024x1024.jpg", "Hair Color", ScaleTypes.FIT));
        image_slider.setImageList(imageList);

        return view;
    }
}