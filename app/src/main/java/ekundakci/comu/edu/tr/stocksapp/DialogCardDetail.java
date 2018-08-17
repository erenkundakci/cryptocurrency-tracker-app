package ekundakci.comu.edu.tr.stocksapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import ekundakci.comu.edu.tr.stocksapp.Activity.MainActivity;

public class DialogCardDetail extends AppCompatDialogFragment {

    private OnDialogClickListener onDialogClickListener;
    private CircleImageView image_view_detail_img;
    private TextView text_view_detail_name;
    private TextView text_view_detail_value;
    private TextView text_view_detail_1h_change;
    private TextView text_view_detail_24h_change;
    private TextView text_view_detail_7d_change;
    private TextView text_view_detail_marketcap;
    private TextView text_view_detail_lastupdated;

    private String detail_imageUrl;
    private String detail_name;
    private String detail_currency_to_convert;
    private double detail_value;
    private String detail_1h_change;
    private String detail_24h_change;
    private String detail_7d_change;
    private String detail_marketcap;
    private String detail_lastupdated;

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public void setDialogText(int imageId, String name, String currencyToConvert, double value, String change1h, String change24h, String change7d, double marketcap, long lastupdated){
        detail_imageUrl = "https://s2.coinmarketcap.com/static/img/coins/128x128/" + imageId + ".png";
        detail_name = name;
        detail_currency_to_convert = currencyToConvert;
        detail_value = value;
        detail_1h_change = change1h;
        detail_24h_change = change24h;
        detail_7d_change = change7d;
        detail_marketcap = "Market Cap: " + String.format("%,f", (marketcap)) + " " + detail_currency_to_convert;
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(tz);
        detail_lastupdated = "Son güncelleme:\n" + String.valueOf(sdf.format(new Date(lastupdated * 1000L)));
    }

    public DialogCardDetail() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTransparentDialog);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.card_detail_dialog_layout, null);

        builder.setView(view);

        image_view_detail_img = view.findViewById(R.id.image_view_detail_img);
        text_view_detail_name = view.findViewById(R.id.text_view_detail_name);
        text_view_detail_value = view.findViewById(R.id.text_view_detail_value);
        text_view_detail_1h_change = view.findViewById(R.id.text_view_detail_1h_value);
        text_view_detail_24h_change = view.findViewById(R.id.text_view_detail_24h_value);
        text_view_detail_7d_change = view.findViewById(R.id.text_view_detail_7d_value);
        text_view_detail_marketcap = view.findViewById(R.id.text_view_detail_marketcap);
        text_view_detail_lastupdated = view.findViewById(R.id.text_view_detail_lastupdated);

        if(Double.parseDouble(detail_1h_change) > 0){
            detail_1h_change = "▲" + detail_1h_change + "%";
            text_view_detail_1h_change.setTextColor(Color.GREEN);
        }
        else if (Double.parseDouble(detail_1h_change) == 0) {
            detail_1h_change = detail_1h_change + "%";
            text_view_detail_1h_change.setTextColor(Color.DKGRAY);
        }
        else {
            detail_1h_change = "▼" + detail_1h_change + "%";
            text_view_detail_1h_change.setTextColor(Color.RED);
        }

        if (Double.parseDouble(detail_24h_change) > 0) {
            detail_24h_change = "▲" + detail_24h_change + "%";
            text_view_detail_24h_change.setTextColor(Color.GREEN);
        }
        else if (Double.parseDouble(detail_24h_change) == 0) {
            detail_24h_change = detail_24h_change + "%";
            text_view_detail_24h_change.setTextColor(Color.DKGRAY);
        }
        else {
            detail_24h_change = "▼" + detail_24h_change + "%";
            text_view_detail_24h_change.setTextColor(Color.RED);
        }

        if (Double.parseDouble(detail_7d_change) > 0) {
            detail_7d_change = "▲" + detail_7d_change + "%";
            text_view_detail_7d_change.setTextColor(Color.GREEN);
        }
        else if (Double.parseDouble(detail_7d_change) == 0) {
            detail_7d_change = detail_7d_change + "%";
            text_view_detail_7d_change.setTextColor(Color.DKGRAY);
        }
        else {
            detail_7d_change = "▼" + detail_7d_change + "%";
            text_view_detail_7d_change.setTextColor(Color.RED);
        }

        Glide.with(getActivity()).load(detail_imageUrl).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(image_view_detail_img);
        text_view_detail_name.setText(detail_name);
        text_view_detail_value.setText(detail_value + " " + detail_currency_to_convert);
        text_view_detail_1h_change.setText(detail_1h_change);
        text_view_detail_24h_change.setText(detail_24h_change);
        text_view_detail_7d_change.setText(detail_7d_change);
        text_view_detail_marketcap.setText(detail_marketcap);
        text_view_detail_lastupdated.setText(detail_lastupdated);
        return builder.create();
    }

    public interface OnDialogClickListener {
        void onPositive();

        void onNegative();
    }
}
