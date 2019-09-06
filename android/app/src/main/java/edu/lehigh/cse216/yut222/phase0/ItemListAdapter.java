package edu.lehigh.cse216.yut222.phase0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mIndex;
        TextView mText;

        ViewHolder(View itemView) {
            super(itemView);
            this.mIndex = (TextView) itemView.findViewById(R.id.listItemIndex);
            this.mText = (TextView) itemView.findViewById(R.id.listItemText);
        }
    }

    private ArrayList<Datum> mData;
    private LayoutInflater mLayoutInflater;

    ItemListAdapter(Context context, ArrayList<Datum> data) {
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
        final Datum d = mData.get(position);
        holder.mIndex.setText(Integer.toString(d.mIndex));
        holder.mText.setText(d.mText);
        // Attach a click listener to the view we are configuring
        final View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mClickListener.onClick(d);
            }
        };
        holder.mIndex.setOnClickListener(listener);
        holder.mText.setOnClickListener(listener);
    }

    // Pop-up Messages
    interface ClickListener{
        void onClick(Datum d);
    }
    private ClickListener mClickListener;
    ClickListener getClickListener() {return mClickListener;}
    void setClickListener(ClickListener c) { mClickListener = c;}
}
