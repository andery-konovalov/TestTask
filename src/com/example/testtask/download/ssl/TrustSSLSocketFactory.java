package com.example.testtask.download.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


public class TrustSSLSocketFactory extends SSLSocketFactory {
        private javax.net.ssl.SSLSocketFactory factory;

        protected TrustSSLSocketFactory()  {
                try {
                        SSLContext sslcontext = SSLContext.getInstance("TLS");
                        sslcontext.init(null, new TrustManager[] { new com.example.testtask.download.ssl.TrustManager() },
                                        null);
                        factory = sslcontext.getSocketFactory();
                        
                } catch (Exception ex) {
                }
        }

        public static SSLSocketFactory getDefault() {
                return new TrustSSLSocketFactory();
        }

        @Override
        public Socket createSocket() throws IOException {
                return factory.createSocket();
        }

        @Override
        public Socket createSocket(Socket socket, String s, int i, boolean flag)
                        throws IOException {
                return factory.createSocket(socket, s, i, flag);
        }

        public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1,
                        int j) throws IOException {
                return factory.createSocket(inaddr, i, inaddr1, j);
        }

        public Socket createSocket(InetAddress inaddr, int i) throws IOException {
                return factory.createSocket(inaddr, i);
        }

        public Socket createSocket(String s, int i, InetAddress inaddr, int j)
                        throws IOException {
                return factory.createSocket(s, i, inaddr, j);
        }

        public Socket createSocket(String s, int i) throws IOException {
                return factory.createSocket(s, i);
        }

        public String[] getDefaultCipherSuites() {
                return factory.getDefaultCipherSuites();
        }

        public String[] getSupportedCipherSuites() {
                return factory.getSupportedCipherSuites();
        }
}