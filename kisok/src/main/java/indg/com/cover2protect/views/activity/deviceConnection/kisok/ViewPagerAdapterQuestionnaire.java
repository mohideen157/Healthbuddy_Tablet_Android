package indg.com.cover2protect.views.activity.deviceConnection.kisok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import indg.com.cover2protect.R;
import indg.com.cover2protect.baseAeglOrbs.ObjQuestionnaire;

public class ViewPagerAdapterQuestionnaire
        extends RecyclerView.Adapter<ViewPagerAdapterQuestionnaire.ViewHolder> {

    private List<ObjQuestionnaire> mData;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private Context context;
    ViewPagerInterface viewPagerInterface;



    ViewPagerAdapterQuestionnaire(ViewPagerInterface viewPagerInterface,
                                  Context context,
                                  ArrayList<ObjQuestionnaire> data,
                                  ViewPager2 viewPager2) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.viewPagerInterface = viewPagerInterface;
        this.mData = data;
        this.viewPager2 = viewPager2;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(indg.com.cover2protect.R.layout.adapter_vp_questionnaire, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       holder.tv_question.setText(mData.get(position).getQuestion());

        switch (position){
            case 0:

                holder.rb3.setVisibility(View.GONE);
                holder.rb4.setVisibility(View.GONE);
                holder.rb1.setText("YES");
                holder.rb3.setText("NO");

                break;
            case 1:

                holder.rb1.setText("NEVER");
                holder.rb3.setText("SOMETIMES");
                holder.rb3.setText("OFTEN");
                holder.rb4.setText("EVERYDAY");

                break;

        }


       holder.ibNext.setOnClickListener(v -> {

           switch (position){
               case 0:

                   if(holder.rb1.isChecked()){
                       mData.get(position).setResponseString("YES");
                   }else {
                       mData.get(position).setResponseString("NO");
                   }

                   break;
               case 1:

                   if(holder.rb1.isChecked()){
                       mData.get(position).setResponseString(holder.rb1.getText().toString());
                   }else if(holder.rb2.isChecked()){
                       mData.get(position).setResponseString(holder.rb2.getText().toString());
                   }else if(holder.rb3.isChecked()){
                       mData.get(position).setResponseString(holder.rb3.getText().toString());
                   }else if(holder.rb4.isChecked()){
                       mData.get(position).setResponseString(holder.rb4.getText().toString());
                   }
                   break;

           }

            viewPagerInterface.onNext(position);


       });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_question;
        ImageButton ibNext;
        RadioButton rb1,rb2,rb3,rb4;


        ViewHolder(View itemView) {
            super(itemView);

            tv_question = itemView.findViewById(R.id.tv_vp_adp_question);
            ibNext = itemView.findViewById(R.id.ib_vp_next_question);
            rb1 = itemView.findViewById(R.id.radio1);
            rb2 = itemView.findViewById(R.id.radio2);
            rb3 = itemView.findViewById(R.id.radio3);
            rb4 = itemView.findViewById(R.id.radio4);

        }
    }

    public interface ViewPagerInterface{
        void onNext(int pagerPosition);
    }
}
