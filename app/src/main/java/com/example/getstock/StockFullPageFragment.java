package com.example.getstock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * This is the full page which we show a Stock in.
 */
public class StockFullPageFragment extends Fragment {

    Button sellStock, buyStock;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    boolean click = true;

    //Image
    ImageView favoritesIcon;

    // variable for our bar chart
    LineChart lineChartDownFill;

    ArrayList<Entry> entryArrayList = new ArrayList<>();

    //Our views.
    TextView symbolName, companyName, stockPrice,changeInPrice, percentChange;
    TextView openingPrice, previousClose, high, low, marketCap, volume, exchange;
    TextView balance;

    //our sell stock and buy stock views
    ImageView increaseBuyStock, decreaseBuyStock, increaseSellStock, decreaseSellStock;
    TextView inputBuy, inputSell;
    Spinner spinner;

    //user logged in settings
    int userType; //Set user type to display the correct view.
    Bundle args;
    User user;
    Broker broker;
    String userId;

    String symbol = "INTC";

    private String []  stockDetails = new String[11];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void unused) {
                symbolName.setText(stockDetails[0]);
                exchange.setText(stockDetails[1]);
                stockPrice.setText(stockDetails[3]);
                companyName.setText(stockDetails[2]);
                changeInPrice.setText(stockDetails[4]);
//                percentChange.setText(stockDetails[5]);
                openingPrice.setText(stockDetails[6]);
                previousClose.setText(stockDetails[7]);
                high.setText(stockDetails[8]);
                low.setText(stockDetails[9]);
                volume.setText(stockDetails[10]);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Stock myStock = YahooFinance.get("INTC");
                    stockDetails[0] = myStock.getSymbol(); //
                    stockDetails[1] = myStock.getStockExchange(); //Exchange
                    stockDetails[2] = myStock.getName(); //Company name
                    stockDetails[3] = String.valueOf(myStock.getQuote().getPrice()); //stock price
                    stockDetails[4] = String.valueOf(myStock.getQuote().getChange()); //stock change
                    stockDetails[5] = myStock.getQuote().getChangeInPercent().toPlainString();
                    stockDetails[6] = myStock.getQuote().getOpen().toPlainString();
                    stockDetails[7] = myStock.getQuote().getPreviousClose().toPlainString();
                    stockDetails[8] = myStock.getQuote().getDayHigh().toPlainString();
                    stockDetails[9] = myStock.getQuote().getDayLow().toPlainString();
                    stockDetails[10] = myStock.getQuote().getVolume().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stock_full_page, container, false);


        //Bind texts
        symbolName = view.findViewById(R.id.stock_symbol);
        companyName = view.findViewById(R.id.company_name);
        stockPrice = view.findViewById(R.id.stock_price);
        exchange = view.findViewById(R.id.exchange);
        changeInPrice = view.findViewById(R.id.Price_Change);
        percentChange = view.findViewById(R.id.percentageChange);
        openingPrice = view.findViewById(R.id.opening_price);
        previousClose = view.findViewById(R.id.previous_close);
        high = view.findViewById(R.id.high);
        low = view.findViewById(R.id.low);
        marketCap = view.findViewById(R.id.market_cap);
        volume = view.findViewById(R.id.volume);
        exchange = view.findViewById(R.id.exchange);
        balance = view.findViewById(R.id.balance_value);

        //Bind our selling and buying
        increaseBuyStock = view.findViewById(R.id.up_arrow_buy);
        decreaseBuyStock = view.findViewById(R.id.down_arrow_buy);
        increaseSellStock = view.findViewById(R.id.up_arrow_sell);
        decreaseSellStock = view.findViewById(R.id.down_arrow_sell);
        inputBuy = view.findViewById(R.id.input_buy);
        inputSell = view.findViewById(R.id.input_sell);
        spinner = view.findViewById(R.id.spinner);

        //put our click listeners
        increaseBuyStock.setOnClickListener(addStockBuyListener);
        increaseSellStock.setOnClickListener(addStockSellListener);
        decreaseSellStock.setOnClickListener(subStockSellListener);
        decreaseBuyStock.setOnClickListener(subStockBuyListener);


        favoritesIcon = view.findViewById(R.id.favorites);
        userType = getArguments().getInt("userType");
        userId = getArguments().getString("userId", userId);
        args = new Bundle();
        String TAG = "";
        View.OnClickListener viewClickFavorites;
        if(userType == 1) { //broker

            broker = (Broker) getArguments().getSerializable("broker");
            balance.setText(broker.getInitialMoney().toString());

            //put args for next fragment.
            args.putInt("userType", 1);
            args.putSerializable("broker", broker);

            if(broker.favorites.contains(symbol)){
                favoritesIcon.setColorFilter(R.color.nice_red);
            }

            viewClickFavorites = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!broker.favorites.contains(symbol)){
                        broker.favorites.add(symbol);
                        favoritesIcon.setColorFilter(R.color.nice_red);
                        db.collection("Brokers").document(userId)
                                .update("favorites", broker.favorites)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("", "Added Successfully to favorites");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("", "Failed to add to favorites");
                            }
                        });
                    }

                }
            };
        }
        else { //user

            user = (User) getArguments().getSerializable("user");
            balance.setText(user.getInitialMoney());

            if(user.favorites.contains(symbol)){
                favoritesIcon.setColorFilter(R.color.nice_red);
            }
            //put args for next fragment.
            args.putInt("userType", 2);
            args.putSerializable("user", user);
            viewClickFavorites = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!user.favorites.contains(symbol)){
                        user.favorites.add(symbol);
                        favoritesIcon.setColorFilter(R.color.nice_red);
                        db.collection("Users").document(userId)
                                .update("favorites", user.favorites)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("", "Added Successfully to favorites");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("", "Failed to add to favorites");
                            }
                        });
                    }
                }
            };

        }

        favoritesIcon.setOnClickListener(viewClickFavorites);

        //Setup our Chart.
        lineChartDownFill = view.findViewById(R.id.idLineChart);
        lineChartDownFill.setTouchEnabled(false);
        lineChartDownFill.setDragEnabled(true);
        lineChartDownFill.setScaleEnabled(true);
        lineChartDownFill.setPinchZoom(false);
        lineChartDownFill.setDrawGridBackground(false);
        lineChartDownFill.getAxisLeft().setDrawGridLines(false);
        lineChartDownFill.getAxisRight().setDrawGridLines(false);
        lineChartDownFill.getXAxis().setDrawGridLines(false);
        lineChartDownFill.getXAxis().setEnabled(false);
        lineChartDownFill.setMaxHighlightDistance(200);
        lineChartDownFill.setViewPortOffsets(0, 0, 0, 0);

        //This function creates our graph, later need to be moved under postExecute method.
        lineChartDownFillWithData();


        //Bind buttons to button var
        sellStock = view.findViewById(R.id.SellStockButton);
        buyStock = view.findViewById(R.id.BuyStockButton);




        if(userType == 1){
            updateBrokerInstance();

        }
        else {
            sellStock.setVisibility(View.INVISIBLE);
            buyStock.setVisibility(View.INVISIBLE);
        }
        //Add on click listener
//        sellStock.setOnClickListener();
//        buyStock.setOnClickListener();
        return view;
    }

    /**
     *  Update our Broker instance.
     */
    public void updateBrokerInstance(){
        db.collection("Brokers").document(mAuth.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                broker = documentSnapshot.toObject(Broker.class);
                List<String> names = new ArrayList<>();
                Log.d("", broker.toString());
                for(String s:broker.IdsToNames.keySet()){
                    names.add(broker.IdsToNames.get(s));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, names);
                spinner.setAdapter(adapter);

                buyStock.setOnClickListener(pressBuy);
                sellStock.setOnClickListener(pressSell);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void updateBroker(){
        db.collection("Brokers").document(mAuth.getUid())
                .set(broker)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("", "Success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("", "Failed");
            }
        });
    }

    //Buy stock on Click event.
    View.OnClickListener pressBuy = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String clientMail = spinner.getSelectedItem().toString();
            String clientId = broker.clientMailToId(clientMail);

            int amountOfStocks = Integer.parseInt(inputBuy.getText().toString());
            double price = Double.parseDouble(stockPrice.getText().toString());
            String symbol = symbolName.getText().toString();

            Log.d("", clientId + "");
            broker.BuyStock(symbol, amountOfStocks, price, clientId);

            updateBroker();

        }
    };

    //Sell stock on Click event.
    View.OnClickListener pressSell = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String clientMail = spinner.getSelectedItem().toString();
            String clientId = broker.clientMailToId(clientMail);

            int amountOfStocks = Integer.parseInt(inputSell.getText().toString());
            double price = Double.parseDouble(stockPrice.getText().toString());
            String symbol = symbolName.getText().toString();

            broker.sellStock(symbol, amountOfStocks, price, clientId);

            Log.d("",broker.toString());
        }
    };

    //Our listeners
    View.OnClickListener addStockBuyListener = new View.OnClickListener () {

        @Override
        public void onClick(View view) {
            int getInput = Integer.parseInt(inputBuy.getText().toString());
            getInput++;
            inputBuy.setText(Integer.toString(getInput));
            inputSell.setText("0");
        }
    };
    View.OnClickListener subStockBuyListener = new View.OnClickListener () {

        @Override
        public void onClick(View view) {
            int getInput = Integer.parseInt(inputBuy.getText().toString());
            inputSell.setText("0");
            if(getInput>0){
                getInput--;
                inputBuy.setText(Integer.toString(getInput));
            }
        }
    };
    View.OnClickListener addStockSellListener = new View.OnClickListener () {

        @Override
        public void onClick(View view) {
            inputBuy.setText("0");
            int getInput = Integer.parseInt(inputSell.getText().toString());
            getInput++;
            inputSell.setText(Integer.toString(getInput));
        }
    };
    View.OnClickListener subStockSellListener = new View.OnClickListener () {

        @Override
        public void onClick(View view) {
            inputBuy.setText("0");
            int getInput = Integer.parseInt(inputSell.getText().toString());
            if(getInput>0){
                getInput--;
                inputSell.setText(Integer.toString(getInput));
            }

        }
    };

    /**
     * Fill our list with entries (x,y) ,
     * later need to be with actual stock entries.
     * should first get values from YahooQuotes , Parse it then update here.
     */
    private void fillGraphWithEntries(){
        entryArrayList.add(new Entry(0, 60f, "1"));
        entryArrayList.add(new Entry(1, 55f, "2"));
        entryArrayList.add(new Entry(2, 60f, "3"));
        entryArrayList.add(new Entry(3, 40f, "4"));
        entryArrayList.add(new Entry(4, 45f, "5"));
        entryArrayList.add(new Entry(5, 36f, "6"));
        entryArrayList.add(new Entry(6, 30f, "7"));
        entryArrayList.add(new Entry(7, 40f, "8"));
        entryArrayList.add(new Entry(8, 45f, "9"));
        entryArrayList.add(new Entry(9, 60f, "10"));
        entryArrayList.add(new Entry(10, 45f, "10"));
        entryArrayList.add(new Entry(11, 20f, "10"));

    }

    /**
     * Setup our graph to display.
     * Mostly graphic setups.
     */
    private void lineChartDownFillWithData() {


        Description description = new Description();
        description.setText("Days Data");

        lineChartDownFill.setDescription(description);

        //Fill our Graph with entries.
        fillGraphWithEntries();


        //LineDataSet is the line on the graph
        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "This is y bill");

        lineDataSet.setLineWidth(5f);
        lineDataSet.setColor(Color.GRAY);
        //lineDataSet.setCircleColorHole(Color.GREEN);
        lineDataSet.setCircleColor(R.color.white);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleRadius(10f);
        lineDataSet.setCircleColor(Color.YELLOW);

        //to make the smooth line as the graph is adrapt change so smooth curve
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //to enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.setCubicIntensity(0.2f);

        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.BLACK);
        //set the transparency
        lineDataSet.setFillAlpha(80);

        //set the gradiant then the above draw fill color will be replace
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
        lineDataSet.setFillDrawable(drawable);

        //set legend disable or enable to hide {the left down corner name of graph}
        Legend legend = lineChartDownFill.getLegend();
        legend.setEnabled(false);

        //to remove the cricle from the graph
        lineDataSet.setDrawCircles(false);

        //lineDataSet.setColor(ColorTemplate.COLORFUL_COLORS);


        ArrayList<ILineDataSet> iLineDataSetArrayList = new ArrayList<>();
        iLineDataSetArrayList.add(lineDataSet);

        //LineData is the data accord
        LineData lineData = new LineData(iLineDataSetArrayList);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);


        lineChartDownFill.setData(lineData);
        lineChartDownFill.invalidate();


    }
}