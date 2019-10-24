//zehui xiao
package edu.lehigh.cse216.yut222.phase0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.preference.PreferenceManager;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;
//import teamname.cse216.lehigh.edu.phase0.R;

class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView uId;
        TextView email;
        TextView Intro;
        TextView username;

        ViewHolder(View itemView) {
            super(itemView);
            this.email = (TextView) itemView.findViewById(R.id.profileItemEmail);
            this.uId = (TextView) itemView.findViewById(R.id.profileItemIdu);
            this.Intro = (TextView) itemView.findViewById(R.id.profileItemIntro);
            this.username= (TextView) itemView.findViewById(R.id.profileItemUsername);

        }
    }

    private ArrayList<User> mData;
    private LayoutInflater mLayoutInflater;

    interface ClickListener {
        void onClick(Message m);

    }

    private ClickListener mClickListener;

    ClickListener getClickListener() {
        return mClickListener;
    }

    void setClickListener(ClickListener c) {
        mClickListener = c;
    }

    ProfileListAdapter(Context context, ArrayList<User> data) {
        mData = data;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.profile_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        final User u = mData.get(pos);
        holder.uId.setText(Integer.toString(u.uId));
        holder.Intro.setText(u.uIntro);
        holder.email.setText(u.uEmail);
        holder.username.setText(u.uName);
        // Attach a click listener to the view we are configuring
        /*final View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mClickListener.onClick(m);
            }
        };

        holder.mId.setOnClickListener(listener);

        holder.mTitle.setOnClickListener(listener);
        //holder.mContent.setOnClickListener(listener);*/

    }
}