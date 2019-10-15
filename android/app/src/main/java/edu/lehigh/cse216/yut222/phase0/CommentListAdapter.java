//Shenyi Yu Phase 1
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

class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mId;
        TextView cId;
        TextView uId;
        TextView cText;
        TextView cUsername;

        ViewHolder(View itemView) {
            super(itemView);
            this.mId = (TextView) itemView.findViewById(R.id.commentItemIdm);
            this.uId = (TextView) itemView.findViewById(R.id.commentItemIdu);
            this.cId = (TextView) itemView.findViewById(R.id.commentItemIdc);
            this.cText = (TextView) itemView.findViewById(R.id.commentItemText);
            this.cUsername = (TextView) itemView.findViewById(R.id.commentItemusername);
        }
    }

    private ArrayList<Comment> mComments;
    private LayoutInflater mLayoutInflater;

    interface ClickListener{
        void onClick(Comment c);
    }

    private ClickListener cClickListener;
    ClickListener getClickListener() {return cClickListener;}
    void setClickListener(ClickListener c) { cClickListener = c;}

    CommentListAdapter(Context context, ArrayList<Comment> comments) {
        mComments = comments;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.comment_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Comment c = mComments.get(position);
//        holder.mId.setText(Integer.toString(m.mId));
        holder.cText.setText(c.cText);
        holder.cId.setText(c.cId);
        holder.uId.setText(c.cId);
        holder.mId.setText(c.cId);
        holder.cUsername.setText("list of comments");


        // Attach a click listener to the view we are configuring
        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                cClickListener.onClick(c);
            }
        };

//        holder.mId.setOnClickListener(listener);
        holder.cText.setOnClickListener(listener);
        holder.cId.setOnClickListener(listener);
    }
}