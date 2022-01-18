package tools.utils;

import java.io.*;
import java.net.URL;

public class Html {

    public static String htmlGet(String linkOfGet, String codeType) {
        BufferedReader br = null;
        StringBuffer codeOfWeb = new StringBuffer();
        URL url = null;

        try {
            url = new URL(linkOfGet);
            SslUtils.ignoreSsl();
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("118.113.204.28", 63342)); 动态代理类
            br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), codeType));

            String lineOfCode;
            while ((lineOfCode = br.readLine()) != null) {
                codeOfWeb.append(lineOfCode + "\n");
            }

            return codeOfWeb.toString();

        } catch (Exception e) {
            return "";
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    return "";
                }
            }
        }
    }
}
