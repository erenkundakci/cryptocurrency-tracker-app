package ekundakci.comu.edu.tr.stocksapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.r0adkll.slidr.Slidr;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ekundakci.comu.edu.tr.stocksapp.Adapter.SavedCurrencyAdapter;
import ekundakci.comu.edu.tr.stocksapp.DialogCantAdd;
import ekundakci.comu.edu.tr.stocksapp.DialogWarning;
import ekundakci.comu.edu.tr.stocksapp.ListingData;
import ekundakci.comu.edu.tr.stocksapp.R;
import ekundakci.comu.edu.tr.stocksapp.SavedCurrencies;
import ekundakci.comu.edu.tr.stocksapp.SpinnerData;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class AddCardActivity extends AppCompatActivity {

    //Widgets
    private TextView text_view_listings_not_found;
    private SearchableSpinner searchableSpinner;
    private SearchableSpinner searchableSpinnerCurrenciesToConvert;
    private ConstraintLayout addCardLayout;
    private ImageView img_add_currency;
    private Button btn_show_addCardLayout;
    private Button btn_remove_all_currency;
    private Button btn_save_selections;
    private RecyclerView mRecyclerView;
    private SavedCurrencyAdapter mCardAdapter;
    private ConstraintLayout parentConstraint;

    //Variables
    private static final String LISTING_URL = "https://api.coinmarketcap.com/v2/listings/";
    private static final String TAG = "AddCardActivity";
    private static final String SHARED_PREFS = "shared preferences";
    private ArrayList<SavedCurrencies> savedCurrencies;
    private List<SpinnerData> currencyList = new ArrayList<>();
    private List<String> currencyToConvertList = new ArrayList<>();
    private boolean animationPlayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_before);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/blogger-sans.regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        Slidr.attach(this);

        new ListingParse().execute();

        text_view_listings_not_found = findViewById(R.id.text_view_listings_not_found);
        addCardLayout = findViewById(R.id.addCardLayout);
        img_add_currency = findViewById(R.id.img_add_currency);
        btn_show_addCardLayout = findViewById(R.id.btn_show_addcardLayout);
        btn_remove_all_currency = findViewById(R.id.btn_remove_all_currency);
        btn_save_selections = findViewById(R.id.btn_save_selections);
        parentConstraint = findViewById(R.id.root);

        searchableSpinner = findViewById(R.id.searchableSpinner);
        searchableSpinner.setTitle("Eklemek istediğiniz para birimini seçiniz.");
        searchableSpinner.setPositiveButton("Kapat");

        currencyToConvertList.addAll(Arrays.asList("AUD", "BRL", "CAD", "CHF", "CLP", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PKR", "PLN", "RUB", "SEK", "SGD", "THB", "TRY", "TWD", "USD", "ZAR"));
        searchableSpinnerCurrenciesToConvert = findViewById(R.id.searchableSpinnerCurrenciesToConvert);
        searchableSpinnerCurrenciesToConvert.setTitle("Seçtiğiniz kripto para hangi birime çevrilsin?");
        searchableSpinnerCurrenciesToConvert.setPositiveButton("Kapat");

        performAnimations();
        loadData();
        buildRecyclerView();

        final ConstraintSet constraintOne = new ConstraintSet();
        constraintOne.clone(parentConstraint);
        final ConstraintSet constraintTwo = new ConstraintSet();
        constraintTwo.load(this, R.layout.activity_add_card);
        btn_show_addCardLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                new TransitionManager();
                TransitionManager.beginDelayedTransition(parentConstraint);
                if (animationPlayed){
                    constraintOne.applyTo(parentConstraint);
                }
                else {
                    constraintTwo.applyTo(parentConstraint);
                }
                animationPlayed = !animationPlayed;
            }
        });

        img_add_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = searchableSpinner.getSelectedItemPosition();
                int id = currencyList.get(position).getId();
                String symbol = String.valueOf(searchableSpinnerCurrenciesToConvert.getSelectedItem());

                boolean containsSame = false;
                for (SavedCurrencies s: savedCurrencies) {
                    if (s.getSavedCurrencyId() == id && s.getCurrencyToConvert().equals(symbol)){
                        containsSame = true;
                    }
                }
                if (!containsSame) {
                    savedCurrencies.add(new SavedCurrencies(id, searchableSpinner.getSelectedItem().toString(), false, symbol));
                    saveData(savedCurrencies);
                    mCardAdapter.notifyDataSetChanged();
                    Toast.makeText(AddCardActivity.this, searchableSpinner.getSelectedItem().toString() + " - " + symbol + " eklendi.", Toast.LENGTH_SHORT).show();
                }
                else {
                    openCantAddDialog();
                }
            }
        });

        btn_remove_all_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWarningDialog();

            }
        });

        btn_save_selections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setResult(RESULT_OK, new Intent());
                finish();
            }
        });

    }

    public void performAnimations(){

        btn_show_addCardLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_slide_left, R.anim.slide_out_right);
    }

    public void openWarningDialog(){
        DialogWarning dialogWarning = new DialogWarning();
        dialogWarning.setOnDialogClickListener(new DialogWarning.OnDialogClickListener() {
            @Override
            public void onPositive() {
                savedCurrencies.clear();
                removeAllData();
                mCardAdapter.notifyDataSetChanged();
                setResult(RESULT_OK, new Intent());
            }

            @Override
            public void onNegative() {

            }
        });

        dialogWarning.show(getSupportFragmentManager(), "Warning Dialog");
    }

    public void openCantAddDialog(){
        DialogCantAdd dialogWarning = new DialogCantAdd();
        dialogWarning.setOnDialogClickListener(new DialogCantAdd.OnDialogClickListener() {
            @Override
            public void onPositive() {

            }

            @Override
            public void onNegative() {

            }
        });

        dialogWarning.setDialogText("Aynı karttan bir tane daha ekleyemezsin.");
        dialogWarning.show(getSupportFragmentManager(), "Warning Dialog");
    }

    private void saveData(ArrayList<SavedCurrencies> savedCurrencies){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(savedCurrencies);
        editor.putString("Saved Currencies", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Saved Currencies", null);
        Type type = new TypeToken<ArrayList<SavedCurrencies>>() {}.getType();
        savedCurrencies = gson.fromJson(json, type);

        if (savedCurrencies == null){
            savedCurrencies = new ArrayList<>();
        }
    }

    private void removeAllData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        Toast.makeText(this, "Tüm kayıtlar hafızadan silindi.", Toast.LENGTH_SHORT).show();
    }

    private void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.add_card_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AddCardActivity.this));
        mCardAdapter = new SavedCurrencyAdapter(AddCardActivity.this, savedCurrencies);
        mRecyclerView.setAdapter(mCardAdapter);

    }

//    public void sortArrayListAlphabetically(){
//        Collections.sort(currencyList, new Comparator<SpinnerData>() {
//            @Override
//            public int compare(SpinnerData t1, SpinnerData t2) {
//                return t1.getCoinName().compareTo(t2.getCoinName());
//            }
//        });
//    }

    private class ListingParse extends AsyncTask<String, String, String> {

        private ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar = new ProgressBar(AddCardActivity.this);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        }


        protected String doInBackground(String... strings) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(LISTING_URL);
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
            progressBar.setVisibility(View.GONE);
            progressBar.setIndeterminate(false);

            if (response != null) {
                Gson gson = new GsonBuilder().create();
                ListingData data1 = gson.fromJson(response, ListingData.class);
                searchableSpinner.setVisibility(View.VISIBLE);
                searchableSpinnerCurrenciesToConvert.setVisibility(View.VISIBLE);
                img_add_currency.setVisibility(View.VISIBLE);
                btn_remove_all_currency.setVisibility(View.VISIBLE);
                btn_save_selections.setVisibility(View.VISIBLE);
                btn_show_addCardLayout.setVisibility(View.VISIBLE);

                for (int i = 0; i < data1.getData().size(); i++) {
                    currencyList.add(new SpinnerData(data1.getData().get(i).getİd(),data1.getData().get(i).getName(), data1.getData().get(i).getSymbol()));
                }

                //Order Currency List Alphabetically
//                sortArrayListAlphabetically();

                ArrayAdapter<SpinnerData> currencySpinnerAdapter = new ArrayAdapter<>(AddCardActivity.this, R.layout.custom_spinner_item, currencyList);
                currencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchableSpinner.setAdapter(currencySpinnerAdapter);

                ArrayAdapter<String> currencyToConvertSpinnerAdapter = new ArrayAdapter<>(AddCardActivity.this, R.layout.custom_spinner_item, currencyToConvertList);
                currencyToConvertSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchableSpinnerCurrenciesToConvert.setAdapter(currencyToConvertSpinnerAdapter);

            }
            else {
                searchableSpinner.setVisibility(View.GONE);
                searchableSpinnerCurrenciesToConvert.setVisibility(View.GONE);
                btn_save_selections.setVisibility(View.GONE);
                img_add_currency.setVisibility(View.GONE);
                btn_remove_all_currency.setVisibility(View.GONE);
                btn_show_addCardLayout.setVisibility(View.GONE);
                text_view_listings_not_found.setVisibility(View.VISIBLE);
            }
        }
    }
}
