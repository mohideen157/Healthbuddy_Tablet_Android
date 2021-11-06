package indg.com.cover2protect.views.activity.deviceConnection.bpMonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.getmedcheck.lib.model.BleDevice;

import indg.com.cover2protect.R;
import indg.com.cover2protect.views.activity.deviceConnection.BaseAdapter;

public class AdapterBpDeviceScanner extends BaseAdapter<BleDevice, AdapterBpDeviceScanner.ViewHolder> {

    public AdapterBpDeviceScanner(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BleDevice bleDevice = getListItem(position);

        if(bleDevice.getDeviceName().equalsIgnoreCase("SFBPBLE")){

        holder.tvDeviceName.setText("Blood Pressure Machine");
        holder.tvDeviceAddress.setText(bleDevice.getMacAddress());

        }else {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDeviceName;
        TextView tvDeviceAddress;
        LinearLayout llParent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            tvDeviceAddress = itemView.findViewById(R.id.tvDeviceAddress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getListItem(getAdapterPosition()), getAdapterPosition());
            }
        }
    }
}
