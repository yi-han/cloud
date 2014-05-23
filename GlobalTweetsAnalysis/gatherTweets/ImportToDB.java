package gatherTweets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.JSONException;
import twitter4j.JSONObject;


public class ImportToDB {
	
	private static File Tweets_Boston = new File("/mnt/Boston_nodup.txt");
	private static File Import_Tweets_Boston = new File("/mnt/Import_Tweets_Boston.sh");
	private static FileReader fr_tweets;
	private static FileWriter fw_tweets;
	private static BufferedReader br_tweets;
	private static BufferedWriter bw_tweets;
	
	public static boolean isJSONValid(String test)
	{
	    try {
	        new JSONObject(test);
	        return true;
	    } catch(JSONException ex) { 
	        return false;
	    }
	}
	
	public static void main(String[] args) {
	
		try
		{
			fr_tweets = new FileReader(Tweets_Boston.getAbsoluteFile());
			br_tweets = new BufferedReader(fr_tweets);
			fw_tweets = new FileWriter(Import_Tweets_Boston.getAbsoluteFile());
			bw_tweets = new BufferedWriter(fw_tweets);
			
			String sCurrentLine;
			String command;
			int i=0;
			//long minId = 0;
			
			while((sCurrentLine = br_tweets.readLine()) != null)
			{
				if(!isJSONValid(sCurrentLine))
				{
					continue;
				}
				
				i++;
				command = "curl -X PUT -d '" + sCurrentLine + "' http://127.0.0.1:5984/tweets_boston/tweets_boston_" + i;

				try
				{
					bw_tweets.append(command+"\n");	 
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			br_tweets.close();
			bw_tweets.close();
		}
		catch(IOException ex)
		{
			
		}
	}
}
