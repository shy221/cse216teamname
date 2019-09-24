package edu.lehigh.cse216.yut222.phase0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//import teamname.cse216.lehigh.edu.phase0.R;

class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mId;
        TextView mTitle;
        TextView mContent;

        ViewHolder(View itemView) {
            super(itemView);
            this.mId = (TextView) itemView.findViewById(R.id.listItemId);
            this.mTitle = (TextView) itemView.findViewById(R.id.listItemTitle);
            this.mContent = (TextView) itemView.findViewById(R.id.listItemContent);
        }
    }

    private ArrayList<Message> mData;
    private LayoutInflater mLayoutInflater;

    interface ClickListener{
        void onClick(Message m);

    }

    private ClickListener mClickListener;
    ClickListener getClickListener() {return mClickListener;}
    void setClickListener(ClickListener c) { mClickListener = c;}

    ItemListAdapter(Context context, ArrayList<Message> data) {
        mData = data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Message m = mData.get(position);
//        holder.mId.setText(Integer.toString(m.mId));
        holder.mTitle.setText(m.mTitle);
        holder.mContent.setText(m.mContent);


        // Attach a click listener to the view we are configuring
        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mClickListener.onClick(m);
            }
        };

//        holder.mId.setOnClickListener(listener);
        holder.mTitle.setOnClickListener(listener);
        holder.mContent.setOnClickListener(listener);

    }
}