package com.lakala.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpSend {

	public static String post(String url ,Map<String, String> params) {
		
		HttpURLConnection connection = null;
		InputStream in = null;
		InputStreamReader insr = null;
		BufferedReader reader = null;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			String queryString = ParamHandle.toParam(params);
			out.write(queryString);
			out.flush();
			out.close();
			StringBuilder responseString = new StringBuilder();
			String strLine = "";
			in = connection.getInputStream();
			insr = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(insr);
			while ((strLine = reader.readLine()) != null) {
				responseString.append(strLine).append("\n");
			}
			return responseString.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(insr!=null){
						try {
							insr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							if(in!=null){
								try {
									in.close();
								} catch (IOException e) {
									e.printStackTrace();
								}finally{
									
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	
	public static String post(String url ,String params) {
		
		HttpURLConnection connection = null;
		InputStream in = null;
		InputStreamReader insr = null;
		BufferedReader reader = null;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(params);
			out.flush();
			out.close();
			StringBuilder responseString = new StringBuilder();
			String strLine = "";
			in = connection.getInputStream();
			insr = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(insr);
			while ((strLine = reader.readLine()) != null) {
				responseString.append(strLine).append("\n");
			}
			return responseString.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(insr!=null){
						try {
							insr.close();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							if(in!=null){
								try {
									in.close();
								} catch (IOException e) {
									e.printStackTrace();
								}finally{
									
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
