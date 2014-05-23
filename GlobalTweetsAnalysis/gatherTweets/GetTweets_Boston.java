package gatherTweets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class GetTweets_Boston{
	
	private static TwitterStream twitterStream;
	private static StatusListener listener;
	
	private static File Tweets_Boston = new File("./Tweets_BostonNE.txt");
	private static FileWriter fw_tweets;
	private static BufferedWriter bw_tweets;
	private static long count = 0 ;
	private static final long LIMIT = 1000000;
	
	public static void main(String[] args) {
		
		try
		{
			fw_tweets = new FileWriter(Tweets_Boston.getAbsoluteFile());
			bw_tweets = new BufferedWriter(fw_tweets);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true);
	    cb.setOAuthConsumerKey("-----");
      cb.setOAuthConsumerSecret("----");
      cb.setOAuthAccessToken("----");
      cb.setOAuthAccessTokenSecret("----");

	    twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

	    listener = new StatusListener() {

	        @Override
	        public void onException(Exception arg0) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onDeletionNotice(StatusDeletionNotice arg0) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	            public void onScrubGeo(long arg0, long arg1) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onStatus(Status status) {
	        	
	        	try 
	        	{
					bw_tweets.append((new Gson()).toJson(status));
					bw_tweets.append("\n");
					count++;
					if(count>=LIMIT)
					{
						bw_tweets.close();
						fw_tweets.close();
						System.exit(0); 
					}
				} 
	        	catch (IOException e) 
	        	{
					e.printStackTrace();
				}
	        	
	            User user = status.getUser();
	            String profileLocation = user.getLocation();
	            System.out.println(profileLocation);
	        }

	        @Override
	        public void onTrackLimitationNotice(int arg0) {
	            // TODO Auto-generated method stub
	            System.out.println("onTrackLimitationNotice" +"\n");

	        }

	        @Override
	        public void onStallWarning(StallWarning arg0) {
	            // TODO Auto-generated method stub
	            System.out.println("onStallWarning" +"\n");
	        }

	    };
	    
	    FilterQuery fq = new FilterQuery();
	    double lat = 42.3581;
	    double longitude = -71.0636;
	    double lat1 = lat;
	    double longitude1 = longitude;
	    double lat2 = lat + 0.15;
	    double longitude2 = longitude + 0.15;
	    twitterStream.addListener(listener);
	    double[][] bb= {{longitude1, lat1}, {longitude2, lat2}};
	    
	    fq.locations(bb);

	    twitterStream.filter(fq);  

	}
}
