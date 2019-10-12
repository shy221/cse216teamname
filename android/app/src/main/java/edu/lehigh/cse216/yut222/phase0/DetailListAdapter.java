//Shenyi Yu Phase 1
package edu.lehigh.cse216.yut222.phase0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//import teamname.cse216.lehigh.edu.phase0.R;

class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mId;
        TextView mTitle;
        TextView uId;
        TextView mLikes;
        TextView mContent;
        TextView mCreated;
        TextView mDislikes;
        TextView cUsername;

        ViewHolder(View itemView) {
            super(itemView);
            this.mId = (TextView) itemView.findViewById(R.id.detailItemIdm);
            this.uId = (TextView) itemView.findViewById(R.id.detailItemIdu);
            this.mTitle = (TextView) itemView.findViewById(R.id.detailItemTitle);
            //this.mContent = (TextView) itemView.findViewById(R.id.detailItemContent);
            this.mLikes = (TextView) itemView.findViewById(R.id.detailItemLikes);
            this.mDislikes = (TextView) itemView.findViewById(R.id.detailItemDislikes);
            this.mContent = (TextView) itemView.findViewById(R.id.detailItemContent);
            this.mCreated= (TextView) itemView.findViewById(R.id.detailItemTime);
            this.cUsername = (TextView) itemView.findViewById(R.id.detailItemUsername);
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

    DetailListAdapter(Context context, ArrayList<Message> data) {
        mData = data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.detail_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Message m = mData.get(position);
        holder.mId.setText(Integer.toString(m.mId));
        holder.uId.setText(Integer.toString(m.uId));
        holder.mTitle.setText(m.mTitle);
        holder.mContent.setText(m.mContent);
        holder.mCreated.setText(m.mCreated);
        holder.cUsername.setText(m.cUsername);
        holder.mLikes.setText(Integer.toString(m.mLikes));
        holder.mDislikes.setText(Integer.toString(m.mDislikes));
        //String Comments = CommentSetText(m.mComments);
        //holder.mComments.setText(Comments);


        // Attach a click listener to the view we are configuring
        final View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mClickListener.onClick(m);
            }
        };

        holder.mId.setOnClickListener(listener);

        holder.mTitle.setOnClickListener(listener);
        //holder.mContent.setOnClickListener(listener);

    }
    /*
    public String CommentSetText(ArrayList<Comment> m){
        /*for (int i = 0; i <= m.size(); i++) {
            String []s = new
            s[i]= Integer.toString(m.get(i).cId);
        }*/

        //implement!!
        //return "comment";
    //}
}