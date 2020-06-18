package com.lwp.blog.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.config.SysConfig;
import com.lwp.blog.utils.StringUtil;
import com.lwp.blog.utils.TaleUtils;
import com.lwp.blog.utils.UniqueUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/06/14/15:14
 * @Description:
 */
@Component
public class ValidSn {
    @Autowired
    private SysConfig sysConfig;
    private Logger LOOGER = LoggerFactory.getLogger(ValidSn.class);
    public final static String UTF8 = "utf-8";
    private static final String KEY_ALGORITHM = "RSA";
    private String formatString(String source) {
        if (source == null) {
            return null;
        }
        return source.replaceAll("\\r", "").replaceAll("\\n", "");
    }

    private String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
    /**
     * 公钥解密
     *
     * @param data      解密数据
     * @param publicKey 公钥
     * @return
     */
    private String decryptByPublicKey(String data, String publicKey) {
        try {
            publicKey = formatString(publicKey);
            byte[] kb = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey.getBytes(UTF8));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(kb);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] b = data.getBytes(UTF8);
            byte[] decrypt = cipher.doFinal(Base64.decodeBase64(b));
            return new String(decrypt, UTF8);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Bean
    public void Sn(){
        LOOGER.info("检查授权文件......");
        String pubKey = sysConfig.getPubKey();
        String path = this.getUploadFilePath();
        try {
            String data = this.readFileContent(path);
            String s = this.decryptByPublicKey(data,pubKey);
            JSONObject jsonObject = JSONObject.parseObject(s);
            System.out.println(jsonObject);
            String mac = jsonObject.getString("mac");
            String ex = jsonObject.getString("ex");
            String name = jsonObject.getString("name");
            String concurrent = jsonObject.getString("concurrent");
            Map<String,String> map = UniqueUtil.getAllSn();
            //本机mac地址
            String localMac = map.get("mac");
            if(!MD5encode(localMac).equals(mac)){
                throw new Exception();
            }
            Date date=null;
            Date nowDate = new Date();
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date=formatter.parse(ex);
            LOOGER.info("到期时间为："+ex);
            if(nowDate.after(date)){
                LOOGER.error("授权文件已到期！");
                throw new Exception();
            }
            if(StringUtil.isNull(concurrent)){
                throw new Exception();
            }else {
                LOOGER.info("并发许可:" + concurrent);
            }
        }catch (Exception e){
            LOOGER.error("授权文件校验失败！请联系获取最新授权文件");
            throw new RuntimeException();
        }
    }

    /**
     * 获取classes的绝对路径
     *
     * @return
     */
    private String getUploadFilePath() {
        String path = TaleUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File(path);
        return file.getAbsolutePath() + "\\"+"sn";
    }
    /**
     * md5加密
     *
     * @param source 数据源
     * @return 加密字符串
     */
    public static String MD5encode(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        byte[] encode = messageDigest.digest(source.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte anEncode : encode) {
            String hex = Integer.toHexString(0xff & anEncode);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
