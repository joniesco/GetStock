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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.IOException;
import java.util.ArrayList;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * This is the full page which we show a Stock in.
 */
public class StockFullPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button sellStock, buyStock;

    //Image
    ImageView favoritesIcon;

    // variable for our bar chart
    LineChart lineChartDownFill;

    ArrayList<Entry> entryArrayList = new ArrayList<>();

    TextView symbolName, companyName, stockPrice,changeInPrice, percentChange;
    TextView openingPrice, previousClose, high, low, marketCap, volume, exchange;

    private String []  stockDetails = new String[10];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StockFullPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StockFullPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockFullPageFragment newInstance(String param1, String param2) {
        StockFullPageFragment fragment = new StockFullPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Stock myStock = YahooFinance.get("INTC");
                    stockDetails[0] = myStock.getSymbol(); //
                    stockDetails[1] = myStock.getStockExchange(); //Exchange
                    stockDetails[2] = myStock.getName(); //Company name
                    stockDetails[3] = String.valueOf(myStock.getQuote().getPrice()); //stock price

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

        //Image
        favoritesIcon = view.findViewById(R.id.favorites);
        favoritesIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                favoritesIcon.setColorFilter(Color.RED);
            }

        });

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
        lineChartDownFillWithData();


        //Bind buttons to button var
        sellStock = view.findViewById(R.id.SellStockButton);
        buyStock = view.findViewById(R.id.BuyStockButton);
        //Add on click listener
//        sellStock.setOnClickListener();
//        buyStock.setOnClickListener();
        return view;
    }

    /**
     * Fill our list with entries (x,y) ,
     * later need to be with actual stock entries.
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