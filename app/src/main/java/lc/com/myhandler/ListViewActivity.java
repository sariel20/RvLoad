package lc.com.myhandler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lc.com.myhandler.adapter.ListViewAdapter;

/**
 * Created by LiangCheng on 2017/12/4.
 */

public class ListViewActivity extends AppCompatActivity {

    private RecyclerView rlv;
    ListViewAdapter listViewAdapter;
    private String mBaseUrl = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        rlv = (RecyclerView) findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(this));

        new NewsAsyncTask().execute(mBaseUrl);

    }

    /**
     * 实现网络异步访问
     */
    class NewsAsyncTask extends AsyncTask<String, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeen) {
            listViewAdapter = new ListViewAdapter(getApplicationContext(), newsBeen);
            rlv.setAdapter(listViewAdapter);
        }
    }

    /**
     * 将url对应的json格式数据转化为实体
     *
     * @param url
     * @return
     */
    private List<NewsBean> getJsonData(String url) {
        List<NewsBean> list = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            NewsBean newsBean;
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                newsBean = new NewsBean();
                newsBean.setUrl(jsonObject.getString("picSmall"));
                newsBean.setTitle(jsonObject.getString("name"));
                newsBean.setContent(jsonObject.getString("description"));
                list.add(newsBean);
            }
            Log.e("getJsonData", "getJsonData: " + jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过inputstream解析网页返回的数据
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String readStream(InputStream is) throws IOException {
        InputStreamReader isr;
        String result = "";

        isr = new InputStreamReader(is, "UTF-8");
        String line = "";
        BufferedReader br = new BufferedReader(isr);
        while ((line = br.readLine()) != null) {
            result += line;
        }
        return result;
    }
}
