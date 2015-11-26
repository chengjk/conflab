package com.jk.conflab.client.utils;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;

/**
 * Created by jacky.cheng on 2015/11/16.
 */
public class ZkUtils {
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
