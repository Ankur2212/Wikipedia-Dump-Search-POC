import java.util.Random;



public class ExecuteBenchmark {

private String generateRandomString(String valid, int length)
{

	StringBuffer randStr = new StringBuffer();
	for(int i=0; i<length; i++)
	{
		Random randomGenerator = new Random();
		int number = randomGenerator.nextInt(valid.length());
		char ch = valid.charAt(number);
		randStr.append(ch);
	}
return randStr.toString();
}

private void stringBenchmark()
{
	for ( int i=0; i<1000; i++)
	{
		String validchar = "abcdefghijklmnopqrstuvwxyz\\s0123456789-\"\'";
		String testString = generateRandomString(validchar, 1000);
		testString = testString.replace("\\w\\s", " ");
		testString = testString + generateRandomString(validchar, 500);
	}
}

public static void main(String [] args)
{
	ExecuteBenchmark eb = new ExecuteBenchmark();
	long sum=0;
	for (int i=0; i<50; i++){
	long start = System.currentTimeMillis();
	eb.stringBenchmark();
	long end = System.currentTimeMillis();
	sum+=end-start;
}
System.out.println("Java Benchmark Score : "+sum/(float)1000);
}

}
