package com.terabyte.testviolation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.terabyte.testviolation.DataModel;

import java.util.ArrayList;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.ownbookView> {
    public DBAdapter(Context mContext, ArrayList<DataModel> mlocationlist) {
        this.mContext = mContext;
        this.mlocationlist = mlocationlist;
    }

    Context mContext;
    private ArrayList<DataModel> mlocationlist;
    @NonNull
    @Override
    public ownbookView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.singleitemlayout,parent,false);
        return new ownbookView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ownbookView holder, final int position) {
//        holder.mbkname.setText(mlocationlist.get(position).getBookname());
//        holder.mbkauthor.setText("Author:"+mlocationlist.get(position).getAuthor());
//        holder.mbkisbn.setText("ISBN:"+mlocationlist.get(position).getIsbn());
            holder.muserlocation.setText("lat:"+mlocationlist.get(position).LATITUDE+"\nlong:"+mlocationlist.get(position).LONGITUDE);



    }

    @Override
    public int getItemCount() {

        if(!(mlocationlist==null))
        {
            return mlocationlist.size();
        }
        else
        {
            return 0;
        }

    }

    public class ownbookView extends RecyclerView.ViewHolder{

        private Context context;

        public TextView muserlocation;


        public ownbookView(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            muserlocation=itemView.findViewById(R.id.locationtext);

        }
    }

}
