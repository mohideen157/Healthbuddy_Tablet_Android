package indg.com.cover2protect.views.activity.deviceConnection;

import android.view.View;

public interface OnItemClickListener<T> {
    void onItemClick(View view, T object, int position);
}