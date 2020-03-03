package com.zhl.androiddemo;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DragEventActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_event);
        final TextView tvDrag = findViewById(R.id.tv_drag);
        LinearLayout llayout = findViewById(R.id.layout_viewgroup);
        llayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                int action = dragEvent.getAction();
                switch (action){
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("mytag","--ACTION_DRAG_STARTED--");
                        if (dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                            // As an example of what your application might do,
                            // applies a blue color tint to the View to indicate that it can accept
                            // data.
//                            view.setBackgroundColor(Color.BLUE);
//                            // Invalidate the view to force a redraw in the new tint
//                            view.invalidate();

                            // returns true to indicate that the View can accept the dragged data.
                            return true;

                        }
                        return false;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d("mytag","--ACTION_DRAG_LOCATION--x=="+dragEvent.getX()+"--y=="+dragEvent.getY());
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        view.setBackgroundColor(Color.GREEN);
                        // Invalidate the view to force a redraw in the new tint
                        view.invalidate();
                        Log.d("mytag","--ACTION_DRAG_ENTERED--");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        view.setBackgroundColor(Color.parseColor("#eeffee"));
                        // Invalidate the view to force a redraw in the new tint
                        view.invalidate();
                        Log.d("mytag","--ACTION_DRAG_EXITED--");
                        break;
                    case DragEvent.ACTION_DROP:
                        // Gets the item containing the dragged data
                        ClipData.Item item = dragEvent.getClipData().getItemAt(0);

                        // Gets the text data from the item.
                        String dragData = item.getText().toString();

                        // Displays a message containing the dragged data.
                        Toast.makeText(DragEventActivity.this, "Dragged data is " + dragData, Toast.LENGTH_LONG).show();

                        // Turns off any color tints
                        view.setBackgroundColor(Color.parseColor("#eeffee"));

                        // Invalidates the view to force a redraw
                        view.invalidate();
                        Log.d("mytag","--ACTION_DROP--");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        view.setBackgroundColor(Color.parseColor("#eeffee"));
                        // Invalidates the view to force a redraw
                        view.invalidate();
                        Log.d("mytag","--ACTION_DRAG_ENDED--");
                        break;
                }
                return true;
            }
        });
        tvDrag.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData.Item clipItem = new ClipData.Item("拖动复制数据");
                ClipData clipData = new ClipData("拖动复制数据", new String[]{
                        ClipDescription.MIMETYPE_TEXT_PLAIN
                }, clipItem);
                View.DragShadowBuilder shadowBuilder = new MyDragShadowBuilder(tvDrag);
                tvDrag.startDrag(clipData, shadowBuilder, null, 0);
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(50,100));
                return true;
            }
        });
    }


    @Override
    public void onClick(View view) {

    }

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
        private static Drawable shadow;
        public MyDragShadowBuilder(View view) {
            super(view);
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            // Defines local variables
            int width, height;

            // Sets the width of the shadow to half the width of the original View
            width = (int) (getView().getWidth()*1.2f);

            // Sets the height of the shadow to half the height of the original View
            height = (int) (getView().getHeight()*1.2f);

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            outShadowSize.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            outShadowTouchPoint.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }
    }
}
