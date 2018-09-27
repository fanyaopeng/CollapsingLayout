package com.fan.collapsinglayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huisoucw on 2018/9/18.
 */

public class TestFragment extends Fragment {
    private RecyclerView mList;
    private List<String> mData = new ArrayList<>();
    private int max = 12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mList = getView().findViewById(R.id.list);
        for (int i = 0; i < 5; i++) {
            mData.add("这是数据" + i);
        }
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setAdapter(new DataAdapter());
    }

    public boolean canScrollVertically(int direction) {
        return mList != null && mList.canScrollVertically(direction);
    }

    private class DataAdapter extends RecyclerView.Adapter<DataAdapter.VH> {


        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText(mData.get(position));
            holder.img.setImageResource(R.mipmap.image1);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"点击到了哦",Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class VH extends RecyclerView.ViewHolder {
            TextView tv;
            ImageView img;

            VH(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                img = itemView.findViewById(R.id.image);
            }
        }
    }
}
