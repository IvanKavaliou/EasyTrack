package com.itibo.tracking;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Users {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //==========================================================
    public static String excutePost(String targetURL, String urlParameters)
    {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if(connection != null) {
                connection.disconnect();
            }
        }
    }
    //==========================================================

    public  static void main(String[] args) throws UnsupportedEncodingException {
       /* SqlSessionFactory sqlSessionFactory;
        UsersMapper usersMapper;
        Reader reader = null;
        Integer userId;
        try {
            reader = Resources.getResourceAsReader("WEB-INF/mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            usersMapper = sqlSessionFactory.openSession().getMapper(UsersMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            userId = usersMapper.getUserIdByEmail("fddfg");
            System.out.println("User id:" + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //"trackingNumber=PK14110806311&index=1&key=7017b4f9-866b-4a92-93eb-8f19e8fdefa0"

        String urlParameters =
                        "trackingNumber=" + URLEncoder.encode("PK14110806311", "UTF-8") +
                        "&index=" + URLEncoder.encode("1", "UTF-8")+
                        "&key=" + URLEncoder.encode("dc9f0e7b-e23c-48ef-8bfb-3b8937cf6023", "UTF-8");
        System.out.println(excutePost("http://track.yw56.com.cn/en-US/Home/GetExpressDetail", urlParameters));
    }

}
