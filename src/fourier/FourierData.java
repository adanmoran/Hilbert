package fourier;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FourierData 
{
	//Data for the fourier transform
	private LinkedHashMap<Double, Complex> data;
	
	public FourierData()
	{
		data = new LinkedHashMap<>();
	}
	
	public FourierData(Double[] frequencies, Complex[] values) throws FourierException
	{
		if(frequencies == null || values == null)
			throw new FourierException("Frequencies and Complex-value vectors are null.");
		else if(frequencies.length != values.length)
			throw new FourierException("Frequencies and Compex-value vectors must have the same dimensions.");
		
		//Add the elements from the given array, so that we are not cloning the elements.
		for(int i = 0; i < frequencies.length; i++)
		{
			add(frequencies[i], values[i].clone());
		}
	}
	
	public Double[] getFrequencies()
	{
		return data.keySet().toArray(new Double[data.size()]);
	}
	
	public Complex[] getValues()
	{
		return data.values().toArray(new Complex[data.size()]);
	}
	
	public LinkedHashMap<Double, Complex> getMap()
	{
		return new LinkedHashMap<Double, Complex>(data);
	}
	
	public void add(double frequency, Complex value) throws FourierException
	{
		//TODO: check for validity of frequency, check for validity of complex value, and add them to the hashmap
	}
	
	public int size()
	{
		return data.size();
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof FourierData)
		{
			FourierData d = (FourierData) o;
			return data.equals(d.getMap());
		}
		return false;
	}
}
