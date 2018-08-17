package ekundakci.comu.edu.tr.stocksapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DialogCantAdd extends AppCompatDialogFragment {

    private TextView text_view_warning;
    private OnDialogClickListener onDialogClickListener;
    private String text;

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public void setDialogText(String text){
        this.text = text;
    }

    public DialogCantAdd() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.warning_dialog_layout, null);

        builder.setView(view)
                .setTitle("Uyarı")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        onDialogClickListener.onPositive();
                    }
                });
        text_view_warning = view.findViewById(R.id.text_view_dialog_warning);
        if (text != null) {
            text_view_warning.setText(text);
        }

        return builder.create();
    }

    public interface OnDialogClickListener {
        void onPositive();

        void onNegative();
    }
}
