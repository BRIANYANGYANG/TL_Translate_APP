package yang.com.tl;

import android.content.Context;
import android.widget.Toast;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Brian Yang on 2016/8/7 0007.
 */
public class TL_unit {
    private static final String UTF8 = "utf-8";
    private static final String appId = "20160807000026394";
    private static final String token = "coA5YQ9jQDdsYs3nX4Mx";

    private static final String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    //随机数，用于生成md5值，开发者使用时请激活下边第四行代码
    private static final Random random = new Random();

    private Context context;

    public String translate(String q, String from, String to ) throws IOException, JSONException {
        int salt = random.nextInt(10000);

        // 对appId+源文+随机数+token计算md5值
        StringBuffer md5String = new StringBuffer();
        md5String.append(appId).append(q).append(salt).append(token);
        String md5 = DigestUtils.md5Hex(md5String.toString());

        //使用Post方式，组装参数
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("q", q));
        nvps.add(new BasicNameValuePair("from", from));
        nvps.add(new BasicNameValuePair("to", to));
        nvps.add(new BasicNameValuePair("appid", appId));
        nvps.add(new BasicNameValuePair("sign", md5));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

        //创建httpclient链接，并执行
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        //对于返回实体进行解析
        HttpEntity entity = response.getEntity();
        InputStream returnStream = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                returnStream, UTF8));
        StringBuilder result = new StringBuilder();
        String str = null;
        while ((str = reader.readLine()) != null ){
            result.append(str).append("\n");

        }

        //转化为json对象，注：Json解析的jar包可选其它
        JSONObject resultJson = new JSONObject(result.toString());

        //开发者自行处理错误，本示例失败返回为null
        try {
            String error_code = resultJson.getString("error_code");
            if (error_code != null) {
                Toast.makeText(context, resultJson.getString("error_code") +
                        resultJson.getString("error_msg"), Toast.LENGTH_SHORT);
            }

            return null;
        }catch (Exception e){

        }

        //获取返回翻译结果
        JSONArray array = (JSONArray) resultJson.get("trans_result");
        JSONObject dst = (JSONObject) array.get(0);
        String text = dst.getString("dst");
        text = URLDecoder.decode(text, UTF8);
      return text;

    }



}
