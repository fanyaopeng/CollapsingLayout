package com.fan.collapsinglayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CollapsingLayout.OnScrollCallback {
    private RecyclerView mList;
    private CollapsingLayout mCollapsingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(new MAdapter());
        mCollapsingLayout = findViewById(R.id.scroll);
        mCollapsingLayout.setOnScrollCallback(this);
    }

    @Override
    public boolean canChildScroll(int direction) {
        Log.e("main", "scroll  " + mList.canScrollVertically(-direction));
        return mList.canScrollVertically(direction);
    }

    private class MAdapter extends RecyclerView.Adapter<MAdapter.VH> {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(MainActivity.this);
            tv.setBackgroundColor(Color.RED);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(-1, 150);
            tv.setLayoutParams(params);
            params.topMargin = 3;
            tv.setGravity(Gravity.CENTER);
            return new VH(tv);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            TextView tv = (TextView) holder.itemView;
            tv.setText("数据  " + position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
            }
        }
    }
}
