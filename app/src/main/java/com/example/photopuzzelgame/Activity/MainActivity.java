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
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    ImageView[] img = new ImageView[25];
    //    ImageView[] pic = new ImageView[9];
    LinearLayout[] ll = new LinearLayout[25];
    List<Photo> arrimg = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrimg.add(new Photo(R.drawable.img1, 24));
        arrimg.add(new Photo(R.drawable.img2, 23));
        arrimg.add(new Photo(R.drawable.img3, 22));
        arrimg.add(new Photo(R.drawable.img4, 21));
        arrimg.add(new Photo(R.drawable.img5, 20));
        arrimg.add(new Photo(R.drawable.img6, 19));
        arrimg.add(new Photo(R.drawable.img7, 18));
        arrimg.add(new Photo(R.drawable.img8, 17));
        arrimg.add(new Photo(R.drawable.img9, 16));
        arrimg.add(new Photo(R.drawable.img10, 15));
        arrimg.add(new Photo(R.drawable.img11, 14));
        arrimg.add(new Photo(R.drawable.img12, 13));
        arrimg.add(new Photo(R.drawable.img13, 12));
        arrimg.add(new Photo(R.drawable.img14, 11));
        arrimg.add(new Photo(R.drawable.img15, 10));
        arrimg.add(new Photo(R.drawable.img16, 9));
        arrimg.add(new Photo(R.drawable.img17, 8));
        arrimg.add(new Photo(R.drawable.img18, 7));
        arrimg.add(new Photo(R.drawable.img19, 6));
        arrimg.add(new Photo(R.drawable.img20, 5));
        arrimg.add(new Photo(R.drawable.img21, 4));
        arrimg.add(new Photo(R.drawable.img22, 3));
        arrimg.add(new Photo(R.drawable.img23, 2));
        arrimg.add(new Photo(R.drawable.img24, 1));
        arrimg.add(new Photo(R.drawable.img25, 0));


        Collections.shuffle(arrimg);

        for (int i = 0; i < 25; i++) {
            int id = getResources().getIdentifier("img" + i, "id", getPackageName());
            img[i] = findViewById(id);
            img[i].setTag(String.valueOf(i));

        }
//        for (int i = 0; i < 9; i++) {
//            int id = getResources().getIdentifier("pic" + i, "id", getPackageName());
//            pic[i] = findViewById(id);
//            pic[i].setOnLongClickListener(this);
//            pic[i].setTag(String.valueOf(i));
//        }
        for (int i = 0; i < 25; i++) {
            int id = getResources().getIdentifier("ll" + i, "id", getPackageName());
            ll[i] = findViewById(id);
            ll[i].setOnDragListener(this);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 25; i++) {

                    img[i].setImageResource(arrimg.get(i).getImg());
                    img[i].setTag(String.valueOf(arrimg.get(i).getTag()));
                    img[i].setOnLongClickListener(MainActivity.this);
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

                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                v.getBackground().clearColorFilter();
                v.invalidate();
                return true;

            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;

        }
        return false;
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.create();
            builder.setCancelable(false);
            builder.setTitle("Winner");
            builder.setMessage("Are You Winner!!!");
            builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.show();
        }
    }
}