package com.example.photopuzzelgame.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.photopuzzelgame.Photo;
import com.example.photopuzzelgame.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Second_Activity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    ImageView[] tom = new ImageView[25];
    LinearLayout[] ll = new LinearLayout[25];
    List<Photo> arrtom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        arrtom.add(new Photo(R.drawable.tom1, 0));
        arrtom.add(new Photo(R.drawable.tom2, 1));
        arrtom.add(new Photo(R.drawable.tom3, 2));
        arrtom.add(new Photo(R.drawable.tom4, 3));
        arrtom.add(new Photo(R.drawable.tom5, 4));
        arrtom.add(new Photo(R.drawable.tom6, 5));
        arrtom.add(new Photo(R.drawable.tom7, 6));
        arrtom.add(new Photo(R.drawable.tom8, 7));
        arrtom.add(new Photo(R.drawable.tom9, 8));
        arrtom.add(new Photo(R.drawable.tom10, 9));
        arrtom.add(new Photo(R.drawable.tom11, 10));
        arrtom.add(new Photo(R.drawable.tom12, 11));
        arrtom.add(new Photo(R.drawable.tom13, 12));
        arrtom.add(new Photo(R.drawable.tom14, 13));
        arrtom.add(new Photo(R.drawable.tom15, 14));
        arrtom.add(new Photo(R.drawable.tom16, 15));
        arrtom.add(new Photo(R.drawable.tom17, 16));
        arrtom.add(new Photo(R.drawable.tom18, 17));
        arrtom.add(new Photo(R.drawable.tom19, 18));
        arrtom.add(new Photo(R.drawable.tom20, 19));
        arrtom.add(new Photo(R.drawable.tom21, 20));
        arrtom.add(new Photo(R.drawable.tom22, 21));
        arrtom.add(new Photo(R.drawable.tom23, 22));
        arrtom.add(new Photo(R.drawable.tom24, 23));
        arrtom.add(new Photo(R.drawable.tom25, 24));

        Collections.shuffle(arrtom);

        for (int i = 0; i < 25; i++) {
            int id = getResources().getIdentifier("tom" + i, "id", getPackageName());
            tom[i] = findViewById(id);
            tom[i].setTag(String.valueOf(i));
        }

        for (int i = 0; i < 25; i++) {

            int id = getResources().getIdentifier("ll" + i, "id", getPackageName());
            ll[i] = findViewById(id);
            ll[i].setOnDragListener(this);

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 25; i++) {
                    tom[i].setImageResource(arrtom.get(i).getImg());
                    tom[i].setTag(String.valueOf(arrtom.get(i).getTag()));
                    tom[i].setOnLongClickListener(Second_Activity.this);
                }
            }
        }, 5000);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:
                v.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                v.getBackground().clearColorFilter();
                v.invalidate();

                LinearLayout layout = (LinearLayout) v;

                if (layout.getChildCount() == 0) {
                    View view = (View) event.getLocalState();
                    ViewGroup group = (ViewGroup) view.getParent();
                    group.removeView(view);
                    if (group.getBackground() == null) {
                        group.setVisibility(view.GONE);
                    }
                    layout.addView(view);
                    view.setVisibility(View.VISIBLE);
                } else {
                    View view1 = (View) event.getLocalState();
                    ViewGroup group = (ViewGroup) view1.getParent();
                    group.removeView(view1);
                    layout.addView(view1);

                    View view2 = layout.getChildAt(0);
                    layout.removeView(view2);
                    group.addView(view2);

                    view1.setVisibility(view1.VISIBLE);
                    view2.setVisibility(view2.VISIBLE);

                    win();
                }
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;
        }
        return true;
    }

    @Override
    public boolean onLongClick(View v) {

        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        String[] type = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData clipData = new ClipData(v.getTag().toString(), type, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(clipData, shadowBuilder, v, 0);
        return true;
    }

    public void win() {
        int count = 0;
        for (int i = 0; i < 25; i++) {

            View view = ll[i].getChildAt(0);
            int tag = Integer.parseInt(view.getTag().toString());
            if (tag != i) {
                count++;
            }
        }
        if (count == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Second_Activity.this);
            builder.create();
            builder.setCancelable(false);
            builder.setTitle("Winner");
            builder.setMessage("Are You Winner!!!");
            builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.show();
        }
    }
}