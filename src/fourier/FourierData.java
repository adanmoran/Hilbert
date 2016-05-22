package fourier;

import java.util.ArrayList;

public class FourierData 
{
	//Data for the fourier transform
	private ArrayList<Double> frequencies;
	private ArrayList<Complex> values;
	/* maxFrequency is the value that gives the frequency range:
	 * normally, a Fourier transform has frequencies between 0 and 1 in steps of 1/(size(data)),
	 * but this will allow us to represent it as frequencies between -F and F.
	 */
	private double maxFrequency, minFrequency;
	
	public FourierData() throws FourierException
	{
		
	}
	
	public FourierData(ArrayList<Double> frequencies, ArrayList<Complex> values) throws FourierException
	{
		
	}
	
	public FourierData(ArrayList<Double> frequencies, ArrayList<Complex> values, double maxFrequency) throws FourierException
	{
		
	}
	
	public FourierData(ArrayList<Double> frequencies, ArrayList<Complex> values, double maxFrequency, double minFrequency) throws FourierException
	{
		
		//TODO: instantiate all the variables
		this.maxFrequency = maxFrequency;
		this.minFrequency = minFrequency;
		// Check if the frequencies list is a power of two and extend it with zeros if it is not.
		
		//If it is, instantiate the frequencies list and add all the elements
		this.frequencies = new ArrayList<Double>();
		for(Double freq : frequencies)
			addFrequency(freq);
		// Check to see if the values list is the same size as the frequency list
		
		// Instantiate the values list
		this.values = new ArrayList<Complex>();
		for(Complex val : values)
			addValue(val);
		//TODO: check for consistency in the given variables (frequencies[] and values[] should be of size power of 2...
		//    max/minFrequency should be realistic given the knowledge of Fourier transforms, etc.
	}
	
	private void addFrequency(double frequency) throws FourierException
	{
		//TODO: check that the given frequency is in the correct range and add it to the list
		if(frequency > maxFrequency || frequency < minFrequency)
			throw new FourierException("Input frequency out of range.");
		//TODO: add it to the list
	}
	
	private void addValue(Complex value)
	{
		values.add(value);
	}
	
	private void addData(double frequency, Complex value) throws FourierException
	{
		addFrequency(frequency);
		addValue(value);
	}
}
