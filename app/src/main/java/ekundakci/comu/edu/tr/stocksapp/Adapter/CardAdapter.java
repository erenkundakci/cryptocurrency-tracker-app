package ekundakci.comu.edu.tr.stocksapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import ekundakci.comu.edu.tr.stocksapp.R;
import ekundakci.comu.edu.tr.stocksapp.SavedCurrencies;
import ekundakci.comu.edu.tr.stocksapp.StockCardInfo;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<StockCardInfo> mCardItems;
    private OnDeleteClickedListener mDeleteListener;
    private OnFavoriteClickedListener mFavoriteListener;
    private OnNotifyClickedListener mNotifyListener;
    private OnItemClickListener mItemClickListener;

    public static boolean clickable = true;



    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnDeleteClickedListener {
        void onDeleteClicked(int position);
    }

    public interface OnFavoriteClickedListener {
        void onFavoriteClicked(int position);
    }

    public interface OnNotifyClickedListener {
        void onNotifyClicked(int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickedListener listener){
        mDeleteListener = listener;
    }

    public void setOnFavoriteClickListener(OnFavoriteClickedListener listener){
        mFavoriteListener = listener;
    }

    public void setOnNotifyClickListener(OnNotifyClickedListener listener){
        mNotifyListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public CardAdapter(Context mContext, ArrayList<StockCardInfo> mCardItems) {
        this.mContext = mContext;
        this.mCardItems = mCardItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mCurrencyImageView;
        TextView mCurrencyName;
        TextView mCurrencyUnit;
        TextView mCurrencyValue;
        TextView mCurrencyValue1h;
        TextView mCurrencyValue24h;
        public SwipeLayout swipeLayout;
        ImageView notify;
        ImageView favorite;
        ImageView delete;

        public ViewHolder(@NonNull View itemView, final OnDeleteClickedListener deleteListener, final  OnFavoriteClickedListener favoriteListener, final OnItemClickListener itemclickListener, final OnNotifyClickedListener notifyListener) {
            super(itemView);

            mCurrencyImageView = itemView.findViewById(R.id.image_view_currency);
            mCurrencyName = itemView.findViewById(R.id.text_view_coin_name);
            mCurrencyUnit = itemView.findViewById(R.id.text_view_currency_unit);
            mCurrencyValue = itemView.findViewById(R.id.text_view_coin_value);
            mCurrencyValue1h = itemView.findViewById(R.id.text_view_coin_value_1hour);
            mCurrencyValue24h = itemView.findViewById(R.id.text_view_coin_value_24hour);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            notify = itemView.findViewById(R.id.image_view_card_notify);
            favorite = itemView.findViewById(R.id.image_view_card_favorite);
            delete = itemView.findViewById(R.id.image_view_card_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemclickListener != null && clickable){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                        itemclickListener.onItemClick(position);
                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            deleteListener.onDeleteClicked(position);
                        }
                    }
                }
            });

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favoriteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            favoriteListener.onFavoriteClicked(position);
                        }
                    }
                }
            });

            notify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notifyListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            notifyListener.onNotifyClicked(position);
                        }
                    }
                }
            });


            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {
                    clickable = false;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickable = true;
                        }
                    },1000);
                }

                @Override
                public void onOpen(SwipeLayout layout) {

                }

                @Override
                public void onStartClose(SwipeLayout layout) {
                    clickable = false;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickable = true;
                        }
                    },1500);
                }

                @Override
                public void onClose(SwipeLayout layout) {

                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    clickable = false;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickable = true;
                        }
                    },1000);
                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }
            });
        }
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stock_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mDeleteListener, mFavoriteListener, mItemClickListener, mNotifyListener);

        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final StockCardInfo currentCard = mCardItems.get(i);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        int imageId = currentCard.getCurrency_image_id();

        String currencyImageUrl = "https://s2.coinmarketcap.com/static/img/coins/128x128/" + imageId + ".png";
        String currencyName = currentCard.getCurrency_name();
        String currencyUnit = currentCard.getCurrency_to_convert();
        String currencyValue = String.format("%.4f",currentCard.getCurrency_value());
        String currencyValue1h = currentCard.getCurrency_value_1h();
        String currencyValue24h = currentCard.getCurrency_value_24h();
        boolean isCardFavorited = currentCard.isFavorited();

        Glide.with(mContext).load(currencyImageUrl).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(viewHolder.mCurrencyImageView);
        viewHolder.mCurrencyName.setText(currencyName);
        viewHolder.mCurrencyUnit.setText(currencyUnit);
        viewHolder.mCurrencyValue.setText(currencyValue);

        if(Double.parseDouble(currencyValue1h) > 0){
            currencyValue1h = "▲" + currencyValue1h + "%";
            viewHolder.mCurrencyValue1h.setTextColor(Color.GREEN);
        }
        else if (Double.parseDouble(currencyValue1h) == 0) {
            currencyValue1h = currencyValue1h + "%";
            viewHolder.mCurrencyValue1h.setTextColor(Color.DKGRAY);
        }
        else {
            currencyValue1h = "▼" + currencyValue1h + "%";
            viewHolder.mCurrencyValue1h.setTextColor(Color.RED);
        }

        if (Double.parseDouble(currencyValue24h) > 0) {
            currencyValue24h = "▲" + currencyValue24h + "%";
            viewHolder.mCurrencyValue24h.setTextColor(Color.GREEN);
        }
        else if (Double.parseDouble(currencyValue24h) == 0) {
            currencyValue24h = currencyValue24h + "%";
            viewHolder.mCurrencyValue24h.setTextColor(Color.DKGRAY);
        }
        else {
            currencyValue24h = "▼" + currencyValue24h + "%";
            viewHolder.mCurrencyValue24h.setTextColor(Color.RED);
        }

        viewHolder.mCurrencyValue1h.setText(currencyValue1h);
        viewHolder.mCurrencyValue24h.setText(currencyValue24h);
        if (isCardFavorited){
            viewHolder.favorite.setImageResource(R.drawable.ic_star_favorited);
        }
        else {
            viewHolder.favorite.setImageResource(R.drawable.ic_star_unfavorited);
        }
    }

    @Override
    public int getItemCount() {
        return mCardItems.size();
    }

}
