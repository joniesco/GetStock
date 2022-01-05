package com.example.getstock;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BrokerListAdapter extends RecyclerView.Adapter<BrokerListAdapter.BrokerListViewHolder> implements Filterable {
    private List<String> brokerIdList;
    private List<Broker> brokerList;
    private List<Broker> brokerListFull;
    Context ct;
    Fragment ft;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user;
    String userId;

    BrokerListAdapter(List<Broker> brokerList, Context ct, Fragment ft, User user, List<String> brokerIdList, String userId) {
        this.userId = userId;
        this.brokerList = brokerList;
        brokerListFull = new ArrayList<>(brokerList);
        this.ct = ct;
        this.ft = ft;
        this.user = user;
        this.brokerIdList = brokerIdList;
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
            List<Broker> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(brokerListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Broker item : brokerListFull) {
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
            brokerList.clear();
            brokerList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     * Creates and binds our java objects to xml objects.
     */
    class BrokerListViewHolder extends RecyclerView.ViewHolder {

        CardView addPerson;
        CardView displayAddFeature;
        Button sendDetails;
        TextInputEditText inputBox;
        TextView brokerName;
        TextView commision;
        TextView numOfClients;


        BrokerListViewHolder(View itemView) {
            super(itemView);

            //Bind to xml items.
            addPerson = itemView.findViewById(R.id.add_broker);
            displayAddFeature = itemView.findViewById(R.id.add_box);
            brokerName = itemView.findViewById(R.id.broker_name);
            commision = itemView.findViewById(R.id.commision);
            numOfClients = itemView.findViewById(R.id.num_of_clients);
            inputBox = itemView.findViewById(R.id.input_amount);
            sendDetails = itemView.findViewById(R.id.send_details);
        }
    }

    @NonNull
    @Override
    public BrokerListAdapter.BrokerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.broker_item_list_view,
                parent, false);
        return new BrokerListAdapter.BrokerListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BrokerListAdapter.BrokerListViewHolder holder, int position) {

        holder.displayAddFeature.setVisibility(View.INVISIBLE);

        //Set our details
        Broker broker = brokerList.get(position);
        holder.brokerName.setText(broker.getFullName());
        holder.commision.setText(broker.getBrokerCommission().toString());

        if(broker.usersInvesting.keySet().size() == 0 || broker.usersInvesting.keySet() == null) {
            holder.numOfClients.setText("0");
        }
        else {
            holder.numOfClients.setText(broker.usersInvesting.keySet().size());
        }

        //Define what happens on the Add person icon
        holder.addPerson.setOnClickListener(new View.OnClickListener() {
            /**
             * Define an what happens when you click a widget.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                holder.displayAddFeature.setVisibility(View.VISIBLE);
            }

        });
        //Define what happens when you click the send button.
        holder.sendDetails.setOnClickListener(new View.OnClickListener() {
            /**
             * Define an what happens when you click a widget.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {

                //Get text from input
                String amount = holder.inputBox.getText().toString();

                //Check if user has enough money
                //need to implement method in user class ( also convert input to double ).
                if(user.isTransactionOkay(Double.parseDouble(amount))){

                    broker.userRequests.put(userId, Double.parseDouble(amount));

                    db.collection("Brokers").document(brokerIdList.get(holder.getAdapterPosition()))
                            .update("userRequests", broker.userRequests)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    notifyDataSetChanged();
                                    holder.addPerson.setVisibility(View.INVISIBLE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

                //add to user to broker with amount of money.
                //Should be a DB call here or call a local instance of broker and send the instance.

                //Refresh list of brokers from DB so now the user doesnt see that broker anymore.
            }

        });

    }

    @Override
    public int getItemCount() {
        return brokerList.size();
    }

}
