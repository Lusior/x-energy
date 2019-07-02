package com.xhkj.server.energy.utils;

import com.xhkj.server.energy.exception.MyException;
import com.xhkj.server.energy.exception.MyExceptionBuilder;
import com.xhkj.server.energy.exception.MyExceptionEnum;
import org.apache.logging.log4j.util.Strings;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.net.Proxy.Type;
import java.util.Map;
import java.util.Map.Entry;

public class HttpService {

    private int read_time_out = 5000;
    private int connect_time_out = 1000;
    private int retiesTimes = 0;
    private Proxy proxy;

    public HttpService() {
        super();
    }

    public HttpService(int time_out) {
        read_time_out = time_out;
    }

    public HttpService(int time_out, int connect_time_out) {
        this.read_time_out = time_out;
        this.connect_time_out = connect_time_out;
    }

    public HttpService(int time_out, int connect_time_out, int retiesTimes) {
        this.read_time_out = time_out;
        this.connect_time_out = connect_time_out;
        this.retiesTimes = retiesTimes;
    }

    public void setProxy(Type proxyType, String ip, Integer port) {
        proxy = new Proxy(proxyType, new InetSocketAddress(ip, port));
    }

    public String doPost(String url, Map<String, String> params) {
        return this.doPost(url, params, null);
    }

    public String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder postData = new StringBuilder();
        for (Entry<String, String> entry : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append("&");
            }
            postData.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return service(false, url, postData.toString(), "POST", headers);
    }

    public String doPost(String url, String postData, Map<String, String> headers) {
        return service(false, url, postData, "POST", headers);
    }

    public String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder postData = new StringBuilder();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append("&");
                }
                postData.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return service(false, url, postData.toString(), "GET", headers);
    }


    public String doGet(String url, Map<String, String> params) {
        return this.doGet(url, params, null);
    }

    public String doGet(String url) {
        return service(false, url, null, "GET", null);
    }

    public String doGet(String url, String postData, Map<String, String> headers) {
        return service(false, url, postData.toString(), "GET", headers);
    }

    public String doHttpsPost(String url, String postData) {
        return service(true, url, postData, "POST", null);
    }

    public String doHttpsPost(String url, String postData, Map<String, String> headers) {
        return service(true, url, postData, "POST", headers);
    }

    public String doPost(String url, String postData) {
        return service(false, url, postData, "POST", null);
    }

    public String doHttpsPost(String url, Map<String, String> params) {
        return doHttpsPost(url, params, null);
    }

    public String doHttpsPost(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder postData = new StringBuilder();
        for (Entry<String, String> entry : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append("&");
            }
            postData.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return service(true, url, postData.toString(), "POST", headers);
    }


    public String doHttpsGet(String url) {
        return service(true, url, null, "GET", null);
    }

    public String doHttpsGet(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder queryString = new StringBuilder();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                if (queryString.length() != 0) {
                    queryString.append("&");
                }
                queryString.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return service(true, url, queryString.toString(), "GET", headers);
    }

    private String service(boolean isHttps, String url, String postData, String method, Map<String, String> headers) {
        int currentRetriesTimes = retiesTimes;
        while (currentRetriesTimes >= 0) {
            try {
                return service0(isHttps, url, postData, method, headers);
            } catch (MyException hException) {
                currentRetriesTimes--;
                if (currentRetriesTimes < 0) {
//                    hException.setAttribute("retriesTimes", String.valueOf(retiesTimes));
                    throw hException;
                }
            }
        }
        throw MyExceptionBuilder.newBuilder(MyExceptionEnum.HTTP_CONNECT_FAIELD)/*.addAttribute("url", url).addAttribute("msg", "请求失败")*/.build();
    }

    private String service0(boolean isHttps, String url, String postData, String method, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            boolean doOutput = !Strings.isBlank(postData);
            conn = isHttps ? createHttpsConn(url, method, doOutput) : createHttpConn(url, method, doOutput);

            fillProperties(conn, headers);

            if (doOutput) writeMsg(conn, postData, method);

            return readMsg(conn);
        } catch (SocketTimeoutException e) {
            throw MyExceptionBuilder.newBuilder(MyExceptionEnum.HTTP_READ_FAIELD)/*.addAttribute("url", url).addAttribute("timeout", read_time_out + "").addAttribute("msg", e.getMessage())*/.build();
        } catch (ConnectException e) {
            throw MyExceptionBuilder.newBuilder(MyExceptionEnum.HTTP_CONNECT_FAIELD)/*.addAttribute("url", url).addAttribute("timeout", connect_time_out + "").addAttribute("msg", e.getMessage())*/.build();
        } catch (Exception ex) {
            throw new MyException(ex);
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }

    private HttpURLConnection createHttpConn(String url, String method, boolean doOutput) throws IOException {
        URL dataUrl = new URL(url);
        HttpURLConnection conn = openConnection(dataUrl);
        conn.setReadTimeout(read_time_out);
        conn.setConnectTimeout(connect_time_out);
        conn.setDoOutput(doOutput);
        conn.setDoInput(true);
        conn.setRequestMethod(method);
        return conn;
    }

    private HttpURLConnection openConnection(URL dataUrl) throws IOException {
        if (proxy != null) {
            return (HttpURLConnection) dataUrl.openConnection(proxy);
        }
        return (HttpURLConnection) dataUrl.openConnection();
    }

    private String readMsg(HttpURLConnection conn) throws IOException {
        return readMsg(conn, "UTF-8");
    }

    private String readMsg(HttpURLConnection conn, String charSet) throws IOException {
        BufferedReader reader = null;
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode < 400) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), charSet));
            }
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
            }
        }
    }

    private void writeMsg(HttpURLConnection conn, String postData, String method) throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.write(postData.getBytes());
        dos.flush();
        dos.close();
    }

    private void fillProperties(HttpURLConnection conn, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return;
        }

        for (Entry<String, String> entry : params.entrySet()) {
            conn.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }


    /*public String httpsPost(String url, String postData) {
        HttpURLConnection conn = null;
        try {
            boolean doOutput = !Strings.isNullOrEmpty(postData);
            conn = createHttpsConn(url, "POST", doOutput);
            if (doOutput)
                writeMsg(conn, postData, "POST");

            return readMsg(conn);
        } catch (SocketTimeoutException e) {
            throw HExceptionBuilder.newBuilder(HUtilReturnCode.HTTP_READ_FAIELD).addAttribute("url", url).addAttribute("msg", e.getMessage()).build();
        } catch (ConnectException e) {
            throw HExceptionBuilder.newBuilder(HUtilReturnCode.HTTP_READ_FAIELD).addAttribute("url", url).addAttribute("msg", e.getMessage()).build();
        } catch (Exception ex) {
            throw new HException(ex);
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
    }*/

    private HttpURLConnection createHttpsConn(String url, String method, boolean doOutput) throws Exception {
        HostnameVerifier hv = (urlHostName, session) -> true;

        HttpsURLConnection.setDefaultHostnameVerifier(hv);
        trustAllHttpsCertificates();

        URL dataUrl = new URL(url);

        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1",1080));

        HttpURLConnection conn = openConnection(dataUrl);
        conn.setReadTimeout(read_time_out);
        conn.setConnectTimeout(connect_time_out);
        conn.setRequestMethod(method);
        conn.setDoOutput(doOutput);
        conn.setDoInput(true);

        return conn;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public final static String SSL = "ssl";

    public final static String TLSv1_2 = "TLSv1.2";

    public final static String TLSv1_1 = "TLSv1.1";

    private String ssl = "SSL";


    private void trustAllHttpsCertificates() throws Exception {

        //  Create a trust manager that does not validate certificate chains:

        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];

        javax.net.ssl.TrustManager tm = new miTM();

        trustAllCerts[0] = tm;

        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance(ssl);

        sc.init(null, trustAllCerts, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(
                sc.getSocketFactory());

    }

    public static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) throws
                java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) throws
                java.security.cert.CertificateException {
            return;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        HttpService httpService = new HttpService(5000, 10000, 10000);
////	    httpService.setProxy(Type.HTTP, "192.168.150.15",8888);
//	    Map<String, String> header = new HashMap<String, String>(){
//	        {
//	            put("sign","sign");
//	        }
//	    };
//	    httpService.setSsl("TLSv1.2");
//	    
//	    String doGet = httpService.doHttpsGet("https://account.app.acfun.cn/api/account/detector/info/login");
        //String s = "{\"aid\":\"aaaaa\",\"msg_time\":\"1507799551\",\"open_months\":\"1\",\"open_type\":\"vip\",\"openid\":\"xjfdaldfjrefjfljljgfjg\",\"order_id\":\"20171012-1712128958\"}";
        String s = "{\"aid\":\"mvip.p.a.yxff_101470439svip\",\"msg_time\":\"1525910284\",\"open_months\":\"1\",\"open_type\":\"svip\",\"openid\":\"5FDE83293CF6E0208210E819C53C993A\",\"order_id\":\"20180510-7vu0BZX56TkO\"}";
        String key = "d8bf43ba25f521014773fc16f75cd275";
        System.out.println(s + "\n" + key);
    }


}
