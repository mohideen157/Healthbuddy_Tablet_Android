package indg.com.cover2protect.views.activity.deviceConnection.kisok;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import indg.com.cover2protect.R;
import indg.com.cover2protect.baseAeglOrbs.Base2Activity;
import indg.com.cover2protect.baseAeglOrbs.ObjQuestionnaire;

public class QuestionnaireActivityJava extends Base2Activity
implements ViewPagerAdapterQuestionnaire.ViewPagerInterface{

    ArrayList<ObjQuestionnaire> listQuestionnaire = new ArrayList<>();
    ViewPagerAdapterQuestionnaire viewPagerAdapterQuestionnaire;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisok_questionnaire);
        viewPager2 = findViewById(R.id.vp_questionnaire);

        for (int i = 0; i < 1 ; i++) {

            switch (i){

                case 0:{
                    listQuestionnaire.add(new ObjQuestionnaire("Do you have family history of cardiovascular disease ?",""));
                }
                case 1:{
                    listQuestionnaire.add(new ObjQuestionnaire("Do you exercise ?",""));
                }
            }

        }



    }

    @Override
    protected void onStart() {
        super.onStart();

        viewPagerAdapterQuestionnaire = new ViewPagerAdapterQuestionnaire(this,getApplicationContext(), listQuestionnaire, viewPager2);
        viewPager2.setAdapter(viewPagerAdapterQuestionnaire);


    }

    @Override
    public void onNext(int pagerPosition) {

        if(pagerPosition == listQuestionnaire.size()-1){

            finish();
            showToast("Your details have been saved");

        }else{
            viewPager2.setCurrentItem(pagerPosition+1);
        }

    }





}
