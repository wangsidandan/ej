package com.briup.common.util;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

/**
 * 在线实时获取天气
 */
public class WeatherUtils {

    /**
     * 通过城市名称获取该城市的天气信息
     *  另一个空气质量的web-api参考 http://www.pm25.in/api_doc
     * @param cityName
     * @return
     */
    public  static String getWeatherData(String cityName) {
        StringBuilder sb=new StringBuilder();;
        try {
            cityName = URLEncoder.encode(cityName, "UTF-8");
            String weatherRrl = "http://wthrcdn.etouch.cn/weather_mini?city="+cityName;
//            String weatherRrl = "https://free-api.heweather.net/s6/weather/now?location="+cityName+"&key=xxxxxx";

            URL url = new URL(weatherRrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);

            // 设置读取流的编码格式，自定义编码
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while((line=reader.readLine())!=null) {
                sb.append(line).append(" ");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }


    /**
     * 将JSON格式数据进行解析 ，返回一个weather对象
     * @param weatherInfoByJson
     * @return
     */
    public static WeatherInfo getWeather(String weatherInfoByJson){
        JSONObject dataOfJson = JSONObject.fromObject(weatherInfoByJson);
        if(dataOfJson.getInt("status")!=1000) {
            return null;
        }

        //创建WeatherInfo对象，提取所需的天气信息
        WeatherInfo weatherInfo = new WeatherInfo();

        //从json数据中提取数据
        String data = dataOfJson.getString("data");

//        System.out.println(data);
        dataOfJson = JSONObject.fromObject(data);
        weatherInfo.setCityName(dataOfJson.getString("city"));
        weatherInfo.setGanMao(dataOfJson.optString("ganmao"));
        weatherInfo.setTemperature(dataOfJson.optString("wendu")+"℃");

        //获取预测的天气预报信息
        JSONArray forecast = dataOfJson.getJSONArray("forecast");
        //取得当天的
        JSONObject result=forecast.getJSONObject(0);
        weatherInfo.setDate(result.getString("date"));

        String high = result.getString("high").substring(2);
        String low  = result.getString("low").substring(2);

        weatherInfo.setTemperatureRange(low+"~"+high);
        weatherInfo.setWeather(result.getString("type"));

        return weatherInfo;
    }

    public static class WeatherInfo{
        /**
         * 时间
         */
        private String date;

        /**
         * 城市名
         */
        private String cityName;

        /**
         * 天气
         */
        private String weather;
        /**
         * 气温
         */
        private String temperatureRange;

        /**
         * 感冒
         */
        private String ganMao;

        /**
         * 当前温度
         */
        private String temperature;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTemperatureRange() {
            return temperatureRange;
        }

        public void setTemperatureRange(String temperatureRange) {
            this.temperatureRange = temperatureRange;
        }

        public String getGanMao() {
            return ganMao;
        }

        public void setGanMao(String ganMao) {
            this.ganMao = ganMao;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }
    }

}
