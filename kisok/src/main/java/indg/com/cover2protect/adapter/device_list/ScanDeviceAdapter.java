package indg.com.cover2protect.adapter.device_list;

import android.content.Context;
import android.util.SparseArray;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ficat.easyble.BleDevice;

import java.util.List;

import indg.com.cover2protect.R;


public class ScanDeviceAdapter extends CommonRecyclerViewAdapter<BleDevice> {

    public ScanDeviceAdapter(@NonNull Context context, @NonNull List<BleDevice> dataList, @NonNull SparseArray<int[]> resLayoutAndViewIds) {
        super(context, dataList, resLayoutAndViewIds);
    }

    @Override
    public int getItemReslayoutType(int position) {
        return R.layout.device_viewconnect;
    }

    @Override
    public void bindDataToItem(MyViewHolder holder, BleDevice data, int position) {
        LinearLayout layout = (LinearLayout) holder.mViews.get(R.id.itemview);
        TextView name = (TextView) holder.mViews.get(R.id.deviceName);
        name.setText(data.name);

    }
}