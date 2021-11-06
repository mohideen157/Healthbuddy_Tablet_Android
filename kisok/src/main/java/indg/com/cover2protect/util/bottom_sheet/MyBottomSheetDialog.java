package indg.com.cover2protect.util.bottom_sheet;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.View;
import android.widget.TextView;

import indg.com.cover2protect.R;

public class MyBottomSheetDialog extends BottomSheetDialog implements View.OnClickListener {


    private TextView tvTitle;
    private TextView tvSubTitle;
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static MyBottomSheetDialog instance;

    public static MyBottomSheetDialog getInstance(@NonNull Context context) {
        return instance == null ? new MyBottomSheetDialog(context) : instance;
    }

    public MyBottomSheetDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        create();
    }

    public void create() {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_dialog, null);
        setContentView(bottomSheetView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // do something
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // do something
            }
        };

        tvTitle = (TextView) bottomSheetView.findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) bottomSheetView.findViewById(R.id.tvSubTitle);


        tvTitle.setOnClickListener(this);

    }


    public void setTvTitle(String tvTitle) {
        this.tvTitle.setText(tvTitle);
    }

    public void setTvSubTitle(String tvSubTitle) {
        this.tvSubTitle.setText(tvSubTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitle:
//                hide();
                break;

        }
    }
}