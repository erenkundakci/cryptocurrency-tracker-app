package ekundakci.comu.edu.tr.stocksapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ekundakci.comu.edu.tr.stocksapp.Adapter.CardAdapter;
import ekundakci.comu.edu.tr.stocksapp.CoinParseData;
import ekundakci.comu.edu.tr.stocksapp.DialogCardDetail;
import ekundakci.comu.edu.tr.stocksapp.DialogWarning;
import ekundakci.comu.edu.tr.stocksapp.R;
import ekundakci.comu.edu.tr.stocksapp.SavedCurrencies;
import ekundakci.comu.edu.tr.stocksapp.StockCardInfo;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {



    //Widgets
    private RecyclerView mRecyclerView;
    private CardAdapter mCardAdapter;
    private ImageView image_view_no_cards;
    private TextView text_view_no_cards;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private ProgressBar progressBar;

    //Variables
    private static final String TAG = "MainActivity";
    private static final int REQ_CODE = 123;
    private ArrayList<SavedCurrencies> savedCurrencies;
    private ArrayList<StockCardInfo> mItemList = new ArrayList<>();
    private boolean isTickerParseRunning = false;
    private String selectedCurrencyToConvert;
    private ArrayList<String> currenciesToConvert = new ArrayList<>();
    private ArrayList<Boolean> favoriteList = new ArrayList<>();
    private boolean isCardFavorited = false;
    private int remainingRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPump.init(ViewPump.builder()
            .addInterceptor(new CalligraphyInterceptor(
                    new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/blogger-sans.regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()))
        .build());

        image_view_no_cards = findViewById(R.id.image_view_no_cards);
        text_view_no_cards = findViewById(R.id.text_view_no_cards);
        image_view_no_cards.setVisibility(View.GONE);
        text_view_no_cards.setVisibility(View.GONE);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(MainActivity.this);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, REQ_CODE);
                overridePendingTransition(R.anim.activity_slide_right, R.anim.slide_out_left);
            }
        });

        loadData();
        parseCoinData();
        buildRecyclerView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            mItemList.clear();
            loadData();
            parseCoinData();
            mCardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mItemList.clear();
        saveData(savedCurrencies);
        loadData();
        parseCoinData();
        notifyAdapterWithAnimation(mRecyclerView);
        if (!isTickerParseRunning) swipeRefreshLayout.setRefreshing(false);
    }

    private void parseCoinData() {
        //Get user's selected currencies.
        if (savedCurrencies.size() == 0) {
            image_view_no_cards.setVisibility(View.VISIBLE);
            text_view_no_cards.setVisibility(View.VISIBLE);
        } else {
            image_view_no_cards.setVisibility(View.GONE);
            text_view_no_cards.setVisibility(View.GONE);
        }

        //Get API results related with selected currencies.
        for (int i = 0; i < savedCurrencies.size(); i++) {
            int id = savedCurrencies.get(i).getSavedCurrencyId();
            selectedCurrencyToConvert = savedCurrencies.get(i).getCurrencyToConvert();
            isCardFavorited = savedCurrencies.get(i).isFavorite();
//            Log.d(TAG, "loadAndParseCoinData: " + isCardFavorited);
            currenciesToConvert.add(selectedCurrencyToConvert);
            favoriteList.add(isCardFavorited);
//            Log.d(TAG, "loadAndParseCoinData: " + selectedCurrencyToConvert);
            String tickerUrl = "https://api.coinmarketcap.com/v2/ticker/" + id + "/?convert=" + selectedCurrencyToConvert;
            try {
                new TickerParse().execute(tickerUrl);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isTickerParseRunning = false;
            }
        }
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(MainActivity.this, resId);
        mRecyclerView.setLayoutAnimation(animationController);

        mCardAdapter = new CardAdapter(MainActivity.this, mItemList);
        mRecyclerView.setAdapter(mCardAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //Scrolling down
                    fab.hide();
                }
                else {
                    //Scrolling
                    fab.show();
                }
            }
        });

        mCardAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(MainActivity.this, "Clicked on: " + position, Toast.LENGTH_SHORT).show();
                openDetailDialog(position);
            }
        });

        mCardAdapter.setOnDeleteClickListener(new CardAdapter.OnDeleteClickedListener() {
            @Override
            public void onDeleteClicked(int position) {
//                Toast.makeText(MainActivity.this, "Delete on " + position, Toast.LENGTH_SHORT).show();
                openWarningDialog(position);
            }
        });

        mCardAdapter.setOnNotifyClickListener(new CardAdapter.OnNotifyClickedListener() {
            @Override
            public void onNotifyClicked(int position) {
                Toast.makeText(MainActivity.this, "Bildirim özelliği yakında...", Toast.LENGTH_SHORT).show();
            }
        });

        mCardAdapter.setOnFavoriteClickListener(new CardAdapter.OnFavoriteClickedListener() {
            @Override
            public void onFavoriteClicked(int position) {
//                Toast.makeText(MainActivity.this, "Favorite on " + position, Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onFavoriteClicked: BEFORE " + mItemList.get(position).isFavorited());
                mItemList.get(position).setFavorited(!mItemList.get(position).isFavorited());
                mCardAdapter.notifyDataSetChanged();
                String name = mItemList.get(position).getCurrency_name();
                String symbol = mItemList.get(position).getCurrency_to_convert();
                for (SavedCurrencies s : savedCurrencies) {
                    if (s.getCurrencyName().equals(name) && s.getCurrencyToConvert().equals(symbol)){
                        s.setFavorite(mItemList.get(position).isFavorited());
                    }
                }
//                savedCurrencies.get(position).setFavorite(mItemList.get(position).isFavorited());
                saveData(savedCurrencies);
//                for (SavedCurrencies s:savedCurrencies) {
//                    Log.d(TAG, "after loadData, onFavoriteClicked: " + s.getSavedCurrencyId() + s.getCurrencyName() + s.getCurrencyToConvert() + s.isFavorite());
//                }
            }
        });
    }

    private void notifyAdapterWithAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public void openWarningDialog(final int position) {
        DialogWarning dialogWarning = new DialogWarning();
        dialogWarning.setOnDialogClickListener(new DialogWarning.OnDialogClickListener() {
            @Override
            public void onPositive() {
//                savedCurrencies.remove(position);

                String name = mItemList.get(position).getCurrency_name();
                String symbol = mItemList.get(position).getCurrency_to_convert();

                for (int i = 0; i < savedCurrencies.size(); i++) {
                    if (savedCurrencies.get(i).getCurrencyName().equals(name) && savedCurrencies.get(i).getCurrencyToConvert().equals(symbol)){
                        savedCurrencies.remove(i);
                    }
                }

                mItemList.remove(position);
                mCardAdapter.notifyItemRemoved(position);
                saveData(savedCurrencies);
            }

            @Override
            public void onNegative() {

            }
        });
        dialogWarning.setDialogText("Silmek istediğine emin misin?");
        dialogWarning.show(getSupportFragmentManager(), "Warning Dialog");
    }

    public void openDetailDialog(final int position) {
        DialogCardDetail dialogCardDetail = new DialogCardDetail();
        dialogCardDetail.setOnDialogClickListener(new DialogCardDetail.OnDialogClickListener() {
            @Override
            public void onPositive() {

            }

            @Override
            public void onNegative() {

            }
        });
        dialogCardDetail.setDialogText(
                mItemList.get(position).getCurrency_image_id(),
                mItemList.get(position).getCurrency_name(),
                mItemList.get(position).getCurrency_to_convert(),
                mItemList.get(position).getCurrency_value(),
                mItemList.get(position).getCurrency_value_1h(),
                mItemList.get(position).getCurrency_value_24h(),
                mItemList.get(position).getCurrency_value_7d(),
                mItemList.get(position).getMarketcap(),
                mItemList.get(position).getLastupdated()
        );
        dialogCardDetail.show(getSupportFragmentManager(), "Detail Dialog");
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Saved Currencies", null);
        Type type = new TypeToken<ArrayList<SavedCurrencies>>() {
        }.getType();
        savedCurrencies = gson.fromJson(json, type);
        if (savedCurrencies != null) {
            remainingRequests = savedCurrencies.size();
        }


        if (savedCurrencies == null) {
            savedCurrencies = new ArrayList<>();
            remainingRequests = savedCurrencies.size();
        }
//        for (SavedCurrencies s: savedCurrencies){
//            Log.d(TAG, "loadData: " + s.getSavedCurrencyId() + s.getCurrencyName() + s.getCurrencyToConvert() + s.isFavorite());
//        }
    }

    private void saveData(ArrayList<SavedCurrencies> savedCurrencies) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(savedCurrencies);
        editor.putString("Saved Currencies", json);
        editor.apply();
    }

    private class TickerParse extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);

            isTickerParseRunning = true;
        }


        protected String doInBackground(String... url) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url[0]);
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String server_response = null;
            if (response.getStatusLine().getStatusCode() == 200) {
                server_response = null;
                try {
                    server_response = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Log.i("Server Response", "doInBackground: " + server_response);
            } else {
                Log.i("Server Response", "doInBackground: Failed to get server response");
            }

            return server_response;
        }

        @Override
        protected void onPostExecute(String response) {


            if (response != null) {
                Gson gson = new GsonBuilder().create();
                CoinParseData data1 = gson.fromJson(response, CoinParseData.class);

                int image_id = data1.getData().getId();
                String coinName = data1.getData().getName();
                double coinPrice;
                String percentChange1h;
                String percentChange24h;
                String percentChange7d;
                double marketcap;
                long lastupdated;

                if (currenciesToConvert.get(0).equals("USD")) {
                    coinPrice = data1.getData().getQuotes().getUSD().getPrice();
                    percentChange1h = String.valueOf(data1.getData().getQuotes().getUSD().getPercent_change_1h());
                    percentChange24h = String.valueOf(data1.getData().getQuotes().getUSD().getPercent_change_24h());
                    percentChange7d = String.valueOf(data1.getData().getQuotes().getUSD().getPercent_change_7d());
                    marketcap = data1.getData().getQuotes().getUSD().getMarket_cap();
                    lastupdated = data1.getData().getLast_updated();
                }
                else {
                    coinPrice = data1.getData().getQuotes().getSelectedCurrency().getPrice();
                    percentChange1h = String.valueOf(data1.getData().getQuotes().getSelectedCurrency().getPercent_change_1h());
                    percentChange24h = String.valueOf(data1.getData().getQuotes().getSelectedCurrency().getPercent_change_24h());
                    percentChange7d = String.valueOf(data1.getData().getQuotes().getSelectedCurrency().getPercent_change_7d());
                    marketcap = data1.getData().getQuotes().getSelectedCurrency().getMarket_cap();
                    lastupdated = data1.getData().getLast_updated();
                }

                mItemList.add(new StockCardInfo(image_id, coinName, coinPrice, percentChange1h, percentChange24h, currenciesToConvert.get(0), favoriteList.get(0), percentChange7d, marketcap, lastupdated));
                remainingRequests--;
//                Log.d(TAG, "onPostExecute: " + favoriteList.get(0));
                currenciesToConvert.remove(0);
                favoriteList.remove(0);
            }
            else {

            }

            if (remainingRequests == 0) {
                ArrayList<StockCardInfo> favItemList = new ArrayList<>();
                ArrayList<StockCardInfo> unfavItemList = new ArrayList<>();

                for (int i = 0; i < mItemList.size(); i++) {
                    if (mItemList.get(i).isFavorited()){
                        favItemList.add(mItemList.get(i));
                    }
                    else {
                        unfavItemList.add(mItemList.get(i));
                    }
                }
                mItemList.clear();
                mItemList.addAll(favItemList);
                mItemList.addAll(unfavItemList);

                progressBar.setVisibility(View.GONE);
                progressBar.setIndeterminate(false);

                notifyAdapterWithAnimation(mRecyclerView);
            }
        }
    }

}
