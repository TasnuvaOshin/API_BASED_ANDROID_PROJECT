package cricketworldcup.worldcup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import cricketworldcup.worldcup.API.OurClient;
import cricketworldcup.worldcup.API.OurResponse;
import cricketworldcup.worldcup.API.RetrofitSetup;
import cricketworldcup.worldcup.API.dataInfo;
import cricketworldcup.worldcup.API.runInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitTest extends AppCompatActivity {
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);
        t1 = findViewById(R.id.target);
        t2 = findViewById(R.id.fing);
        t3 = findViewById(R.id.fteamname);
        t4 = findViewById(R.id.fteamscore);
        t5 = findViewById(R.id.fteamwicket);
        t6 = findViewById(R.id.fteamover);
        t7 = findViewById(R.id.Sing);
        t8 = findViewById(R.id.Steamname);
        t9 = findViewById(R.id.Steamscore);
        t10 = findViewById(R.id.Steamwicket);
        t11 = findViewById(R.id.Steamover);
        final StringBuilder taget = new StringBuilder();
        OurClient client = RetrofitSetup.GetOurRetrofit(OurClient.class);

        Call<OurResponse> list = client.getOurResponse();
        list.enqueue(new Callback<OurResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<OurResponse> call, Response<OurResponse> response) {
                Log.d("Our Response", "We are getting Response");
                Log.d("Our Response", String.valueOf(response.body().toString()));
                List<dataInfo> dataInfoList = response.body().getData();
                int i;
                int j;
                for (i = 0; i < dataInfoList.size(); i++) {

                    Log.d("Our Response", dataInfoList.get(i).getLive());
                    Log.d("Our Response", dataInfoList.get(i).getStatus());
                    Log.d("Our Response", dataInfoList.get(i).getNote());

                    t2.setText("Now Playing " + dataInfoList.get(0).getStatus());

                    dataInfoList.get(i).getStatus();
                    dataInfoList.get(i).getNote();
                    t1.setText(dataInfoList.get(0).getNote() + "\n");
                    dataInfoList.get(i).getLocalteam().getCode();
                    t3.setText("Team One" + dataInfoList.get(i).getLocalteam().getCode());

                    dataInfoList.get(i).getLocalteam().getId();
                    dataInfoList.get(i).getVisitorteam().getCode();
                    t4.setText("Team Two" + dataInfoList.get(i).getVisitorteam().getCode());
                    dataInfoList.get(i).getVisitorteam().getId();
                    Log.d("Our Response", dataInfoList.get(i).getLocalteam().getCode());
                    Log.d("Our Response", dataInfoList.get(i).getLocalteam().getId());
                    Log.d("Our Response", dataInfoList.get(i).getVisitorteam().getCode());
                    Log.d("Our Response", dataInfoList.get(i).getVisitorteam().getCode());

                    List<runInfo> runInfoList = dataInfoList.get(i).getRuns();

                    for (j = 0; j < runInfoList.size(); j++) {
                        Log.d("Our Response", runInfoList.get(j).getScore());
                        Log.d("Our Response", runInfoList.get(j).getInning());
                        Log.d("Our Response", runInfoList.get(j).getOvers());
                        Log.d("Our Response", runInfoList.get(j).getWickets());

                        t5.setText("1st Innings score " + runInfoList.get(0).getScore());
                        t6.setText("over " + runInfoList.get(0).getOvers());
                        t7.setText("wicket" + runInfoList.get(0).getWickets());

                        t8.setText("2nd Innings score " + runInfoList.get(1).getScore());
                        t9.setText("over " + runInfoList.get(1).getOvers());
                        t10.setText("wicket" + runInfoList.get(1).getWickets());

                    }


                }


            }

            @Override
            public void onFailure(Call<OurResponse> call, Throwable t) {
                Log.d("Our Response", "We are  not getting Response");
            }
        });

        t1.setText(taget.toString());
    }
}
