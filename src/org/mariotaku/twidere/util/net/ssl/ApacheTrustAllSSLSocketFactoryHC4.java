package org.mariotaku.twidere.util.net.ssl;

import org.apache.http.HttpHost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public final class ApacheTrustAllSSLSocketFactoryHC4 implements LayeredConnectionSocketFactory {

	private final SSLConnectionSocketFactory delegated;

	private ApacheTrustAllSSLSocketFactoryHC4() throws KeyManagementException, NoSuchAlgorithmException {
		final TrustManager[] tm = { new TrustAllX509TrustManager() };
		final SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, tm, null);
		delegated = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	@Override
	public Socket connectSocket(final int connectTimeout, final Socket socket, final HttpHost host,
			final InetSocketAddress remoteAddress, final InetSocketAddress localAddress, final HttpContext context)
			throws IOException {
		return delegated.connectSocket(connectTimeout, socket, host, remoteAddress, localAddress, context);
	}

	@Override
	public Socket createLayeredSocket(final Socket socket, final String target, final int port,
			final HttpContext context) throws IOException {
		return delegated.createLayeredSocket(socket, target, port, context);
	}

	@Override
	public Socket createSocket(final HttpContext context) throws IOException {
		return delegated.createSocket(context);
	}

	public static LayeredConnectionSocketFactory getSocketFactory() throws SSLInitializationException {
		try {
			return new ApacheTrustAllSSLSocketFactoryHC4();
		} catch (final GeneralSecurityException e) {
			throw new SSLInitializationException("Cannot create socket factory", e);
		}
	}

}