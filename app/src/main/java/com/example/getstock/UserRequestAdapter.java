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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.UserRequestViewHolder> implements Filterable {

    private List<User> userRequestList;
    private List<User> userRequestListFull;
    private List<String> userIds;
    Context ct;
    Fragment ft;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Broker broker;
    String brokerId;


    UserRequestAdapter(List<User> userRequestList, Context ct, Fragment ft, Broker broker, List<String> userIds, String brokerId) {
        this.userRequestList = userRequestList;
        userRequestListFull = new ArrayList<>(userRequestList);
        this.ct = ct;
        this.ft = ft;
        this.broker = broker;
        this.userIds = userIds;
        this.brokerId = brokerId;
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
            amount = itemView.findViewById(R.id.amount_sent);
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
        User user = userRequestList.get(position);
        holder.clientName.setText(user.getFullName());
        holder.amount.setText(broker.userRequests.get(userIds.get(holder.getAdapterPosition())).toString());



        //Create our fragment to show broker
        Fragment showPost = new showPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", "From Activity"); //Attach the new fragment an instance of broker to show.
        showPost.setArguments(bundle);

        holder.approvePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Insert id to our broker.
                broker.acceptClient(userIds.get(holder.getAdapterPosition()), user.getEmail());

                //
                db.collection("Brokers").document(brokerId)
                        .set(broker).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.brokerMap.put(brokerId, 1.0);
                        holder.approvePerson.setVisibility(View.INVISIBLE);
                        addBrokerToUser(userIds.get(holder.getAdapterPosition()), user);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return userRequestList.size();
    }

    /**
     * replaces our user instance, with a new instance.
     * Calls our Database.
     * @param uid
     * @param user
     */
    public void addBrokerToUser(String uid, User user){
        db.collection("Users").document(uid)
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
