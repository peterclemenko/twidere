package org.mariotaku.twidere.util.net;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntityHC4;
import org.apache.http.message.BasicNameValuePair;

import twitter4j.http.HttpParameter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HttpParameterFormEntity extends UrlEncodedFormEntityHC4 {

	public HttpParameterFormEntity(final HttpParameter[] params) throws UnsupportedEncodingException {
		super(generateKeyValuePairs(params), Consts.UTF_8);
	}

	private static List<NameValuePair> generateKeyValuePairs(final HttpParameter[] params) {
		final List<NameValuePair> result = new ArrayList<NameValuePair>();
		for (final HttpParameter param : params) {
			if (!param.isFile()) {
				result.add(new BasicNameValuePair(param.getName(), param.getValue()));
			}
		}
		return result;
	}

}
