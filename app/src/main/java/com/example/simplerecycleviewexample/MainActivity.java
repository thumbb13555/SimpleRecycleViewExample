package com.example.simplerecycleviewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String TAG = "mExample";
    RecyclerView mRecyclerView;
    MyListAdapter myListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //製造資料
        makeData();

        //設置RecycleView
        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myListAdapter = new MyListAdapter();
        mRecyclerView.setAdapter(myListAdapter);
        //下拉刷新
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_RURI));
        swipeRefreshLayout.setOnRefreshListener(()->{
            arrayList.clear();
            makeData();
            myListAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        });
    }//onCreate

    private void makeData() {
        for (int i = 0;i<30;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("Id","座號："+String.format("%02d",i+1));
            hashMap.put("Sub1",String.valueOf(new Random().nextInt(80) + 20));
            hashMap.put("Sub2",String.valueOf(new Random().nextInt(80) + 20));
            hashMap.put("Avg",String.valueOf(
                    (Integer.parseInt(hashMap.get("Sub1"))
                    +Integer.parseInt(hashMap.get("Sub2")))/2));

            arrayList.add(hashMap);
        }
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView tvId,tvSub1,tvSub2,tvAvg;
            private View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvId = itemView.findViewById(R.id.textView_Id);
                tvSub1 = itemView.findViewById(R.id.textView_sub1);
                tvSub2 = itemView.findViewById(R.id.textView_sub2);
                tvAvg  = itemView.findViewById(R.id.textView_avg);
                mView  = itemView;
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            int avgS = Integer.parseInt(arrayList.get(position).get("Avg"));
            if (avgS>=80){
                holder.tvId.setBackgroundColor(getColor(R.color.green_TOKIWA));
            }else if (avgS<80 &&avgS>=60){
                holder.tvId.setBackgroundColor(getColor(R.color.blue_RURI));
            }else if(avgS<60 &&avgS>=40){
                holder.tvId.setBackgroundColor(getColor(R.color.yellow_YAMABUKI));
            }else {
                holder.tvId.setBackgroundColor(getColor(R.color.red_GINSYU));
            }
            holder.tvId.setText(arrayList.get(position).get("Id"));
            holder.tvSub1.setText(arrayList.get(position).get("Sub1"));
            holder.tvSub2.setText(arrayList.get(position).get("Sub2"));
            holder.tvAvg.setText(arrayList.get(position).get("Avg"));

            holder.mView.setOnClickListener((v)->{
                Toast.makeText(getBaseContext(),holder.tvAvg.getText(),Toast.LENGTH_SHORT).show();
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }


}
