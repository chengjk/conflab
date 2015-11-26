package com.jk.conflab.client.utils;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;

/**
 * Created by jacky.cheng on 2015/11/16.
 */
public class ZkUtils {
    public static String getPath(String rootNode, String key) {
        if (StringUtils.hasText(rootNode)) {
            if (key.startsWith("/")) {
                key = key.substring(1);
            }
            if (rootNode.endsWith("/")) {
                return rootNode + key;
            }
            return rootNode + "/" + key;
        }
        return key;
    }

    public static void mkPath(ZkClient client, String path) {
        String[] subs = path.split("\\/");
        if (subs.length < 2) {
            return;
        }
        String curPath = "";
        for (int i = 1; i < subs.length; i++) {
            curPath = curPath + "/" + subs[i];
            if (!client.exists(curPath)) {
                client.createPersistent(curPath);
            }
        }
    }

    public static class StringSerializer implements ZkSerializer {
        private String encoding;

        public StringSerializer(String encoding) {
            this.encoding = encoding;
        }

        public Object deserialize(byte[] abyte0) throws ZkMarshallingError {
            try {
                return new String(abyte0, this.encoding);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        public byte[] serialize(Object obj) throws ZkMarshallingError {
            if (obj == null) {
                return null;
            }

            if (!(obj instanceof String)) {
                throw new ZkMarshallingError(
                        "The input obj must be an instance of String.");
            }
            try {
                return ((String) obj).getBytes(this.encoding);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
