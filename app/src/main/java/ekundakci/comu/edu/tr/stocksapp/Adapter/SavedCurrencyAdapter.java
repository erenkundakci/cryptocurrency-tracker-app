package ekundakci.comu.edu.tr.stocksapp.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ekundakci.comu.edu.tr.stocksapp.R;
import ekundakci.comu.edu.tr.stocksapp.SavedCurrencies;

public class SavedCurrencyAdapter extends RecyclerView.Adapter<SavedCurrencyAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SavedCurrencies> mCardItems;

    public SavedCurrencyAdapter(Context mContext, ArrayList<SavedCurrencies> mCardItems) {
        this.mContext = mContext;
        this.mCardItems = mCardItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mCurrencyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCurrencyName = itemView.findViewById(R.id.text_view_saved_coin_name);
        }
    }

    @NonNull
    @Override
    public SavedCurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.saved_currency_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SavedCurrencies currentCard = mCardItems.get(i);

        String currencyName = currentCard.getCurrencyName();
        String currencyToConvert = currentCard.getCurrencyToConvert();

        viewHolder.mCurrencyName.setText(i+1 + ". " + currencyName + " - " + currencyToConvert);
    }

    @Override
    public int getItemCount() {
        return mCardItems.size();
    }

}
