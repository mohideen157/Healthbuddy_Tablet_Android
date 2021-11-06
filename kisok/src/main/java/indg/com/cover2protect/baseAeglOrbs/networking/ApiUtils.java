package indg.com.cover2protect.baseAeglOrbs.networking;

import android.content.Context;
import indg.com.cover2protect.BuildConfig;

import static indg.com.cover2protect.util.ConstKt.BASE_URL;


/**
 * Created by kural on 10/10/17.
 */

public class ApiUtils {


        private ApiUtils() {}


    public static ApiInterface getAPIServiceFitbit(Context context) {

        return RetroFitBuilderReactive.getRxClient(BuildConfig.FitBitApiUrl,context)
                .create(ApiInterface.class);
    }

        public static ApiInterface getAPIServiceRx(Context context) {

            return RetroFitBuilderReactive.getRxClient(BuildConfig.Base_URL,context)
                    .create(ApiInterface.class);
        }

    public static ApiInterface getAPIServiceDevice(Context context) {

        return RetroFitBuilderReactive.getRxClient(BASE_URL,context)
                .create(ApiInterface.class);
    }

}
