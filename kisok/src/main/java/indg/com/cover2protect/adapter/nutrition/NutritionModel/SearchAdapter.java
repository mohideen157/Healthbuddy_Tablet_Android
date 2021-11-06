package indg.com.cover2protect.adapter.nutrition.NutritionModel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import indg.com.cover2protect.presenter.OnItemClick;
import indg.com.cover2protect.model.nutrition.nutitionmodel.Branded;
import indg.com.cover2protect.model.nutrition.nutitionmodel.Common;
import indg.com.cover2protect.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    String mTag;
    LayoutInflater inflater;
    View view;
    List<Common> mCommonList;
    List<Branded> mBrandedList;
    Context mContext;

    private OnItemClick listener;

    public SearchAdapter(String tag, List<Common> commonList, List<Branded> brandedList, Context context) {
        this.mTag = tag;
        this.mCommonList = commonList;
        this.mBrandedList = brandedList;
        this.mContext = context;
    }

    public void setListener(OnItemClick listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout_nutrition, viewGroup, false);
        return new SearchHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder searchHolder, final int i) {
        if (mTag.equalsIgnoreCase("common")) {
            searchHolder.item_search_common_layout.setVisibility(View.VISIBLE);
            searchHolder.item_search_brand_layout.setVisibility(View.GONE);
            if (mCommonList != null) {
                searchHolder.item_search_common_name_tv.setText(mCommonList.get(i).getFood_name());
                Glide.with(mContext).load(mCommonList.get(i).getPhoto().getThumb()).into(searchHolder.item_search_common_iv);
            }
            searchHolder.item_search_common_name_tv.setOnClickListener(v ->
                    listener.onClick(mCommonList.get(i).getTag_name()));

        } else {
            searchHolder.item_search_common_layout.setVisibility(View.GONE);
            searchHolder.item_search_brand_layout.setVisibility(View.VISIBLE);

            if (mBrandedList != null) {
                searchHolder.item_search_branded_name_tv.setText(mBrandedList.get(i).getFood_name());
                searchHolder.item_search_branded_cal_tv.setText("" + mBrandedList.get(i).getNf_calories());
                searchHolder.item_search_branded_desc_tv.setText(mBrandedList.get(i).getBrand_name() + ", " + mBrandedList.get(i).getServing_qty() + " " + mBrandedList.get(i).getServing_unit());
                Glide.with(mContext).load(mBrandedList.get(i).getPhoto().getThumb()).into(searchHolder.item_search_branded_iv);
                searchHolder.item_search_branded_name_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(mCommonList.get(i).getTag_name());

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        int count;
        if (mTag.equalsIgnoreCase("common")) {
            count = mCommonList.size();
        } else {
            count = mBrandedList.size();
        }
        return count;
    }

    public class SearchHolder extends RecyclerView.ViewHolder {
        public LinearLayout item_search_common_layout;
        public ImageView item_search_common_iv;
        public TextView item_search_common_name_tv;
        public RelativeLayout item_search_brand_layout;
        public ImageView item_search_branded_iv;
        public TextView item_search_branded_name_tv;
        public TextView item_search_branded_desc_tv;
        public TextView item_search_branded_cal_tv;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            item_search_common_layout = itemView.findViewById(R.id.item_search_common_layout);
            item_search_common_iv = itemView.findViewById(R.id.item_search_common_iv);
            item_search_common_name_tv = itemView.findViewById(R.id.item_search_common_name_tv);

        }
    }
}

