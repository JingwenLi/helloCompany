package Company;


public class ljw{

	public static void main(String[] args){ 

		String industry[] = new String[]{"E_COMMERCE",
				"SOCIAL_NETWORK",
				"INTELLIGENT_HARDWARE",
				"MEDIA","SOFTWARE",
				"CONSUMER_LIFESTYLE",
				"FINANCE",
				"MEDICAL_HEALTH",
				"SERVICE_INDUSTRIES",
				"TRAVEL_OUTDOORS",
				"PROPERTY_AND_HOME_FURNISHINGS",
				"CULTURE_SPORTS_ART",
				"EDUCATION_TRAINING",
				"AUTO",
				"OTHER",
				"LOGISTICS"};

		for(int i = 0,lenc = industry.length ; i<lenc ; i++)
		{
			Mythread mythread = new Mythread();
			mythread.sets(industry[i]);
			Thread thread = new Thread(mythread);
			thread.start();
		}
	}
}
