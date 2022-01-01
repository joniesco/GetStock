package com.example.getstock;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.UserRequestViewHolder> implements Filterable {

    private List<User> userRequestList;
    private List<User> userRequestListFull;
    Context ct;
    Fragment ft;


    UserRequestAdapter(List<User> userRequestList, Context ct, Fragment ft) {
        this.userRequestList = userRequestList;
        userRequestListFull = new ArrayList<>(userRequestList);
        this.ct = ct;
        this.ft = ft;
    }

    @Override
    public Filter getFilter() {
        return stockFilter;
    }

    /**
     * Will used to search in adapter, ignore for now.
     */
    private Filter stockFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(userRequestListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (User item : userRequestListFull) {
                    if (item.getAge().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userRequestList.clear();
            userRequestList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     * Creates and binds our java objects to xml objects.
     */
    class UserRequestViewHolder extends RecyclerView.ViewHolder {

        //Our paramenters
        CardView approvePerson;
        TextView clientName;
        TextView amount;
        ImageView userProfilePic;


        UserRequestViewHolder(View itemView) {
            super(itemView);

            //Bind to xml items.
            approvePerson = itemView.findViewById(R.id.approve_client);
            clientName = itemView.findViewById(R.id.user_fullname);
            amount = itemView.findViewById(R.id.amount);
            userProfilePic = itemView.findViewById(R.id.user_profile_pic);
        }
    }

    @NonNull
    @Override
    public UserRequestAdapter.UserRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_request_item_view,
                parent, false);
        return new UserRequestAdapter.UserRequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRequestAdapter.UserRequestViewHolder holder, int position) {

//        //Set our details
//        User user = userRequestList.get(position);
//        holder.brokerName.setText(user.getFullName());
//        holder.commision.setText(user.getBrokerCommission().toString());
//        if(broker.usersInvesting.keySet().size() == 0 || broker.usersInvesting.keySet() == null) {
//            holder.numOfClients.setText("0");
//        }
//        else {
//            holder.numOfClients.setText(broker.usersInvesting.keySet().size());
//        }



        //Create our fragment to show broker
        Fragment showPost = new showPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", "From Activity"); //Attach the new fragment an instance of broker to show.
        showPost.setArguments(bundle);


    }

    @Override
    public int getItemCount() {
        return userRequestList.size();
    }
}
