package fourier;

import java.util.ArrayList;

public class FourierData 
{
	//Data for the fourier transform
	//TODO: change this to a HashMap instead of double arrayLists
	private ArrayList<Double> frequencies;
	private ArrayList<Complex> values;
	private int size;
	
	public FourierData()
	{
		frequencies = new ArrayList<>();
		values = new ArrayList<>();
	}
	
	public FourierData(ArrayList<Double> frequencies, ArrayList<Complex> values) throws FourierException
	{
		if(frequencies.size() != values.size())
			throw new FourierException("Frequencies and Compex-value vectors must have the same dimensions.");
		
		//Initialize the arrays
		frequencies = new ArrayList<>();
		values = new ArrayList<>();
		size = 0;
		
		//Add the elements from the given array, so that we are not cloning the elements.
		for(int i = 0; i < frequencies.size(); i++)
		{
			add(frequencies.get(i), values.get(i).clone());
		}
	}
	
	public ArrayList<Double> getFrequencies()
	{
		ArrayList<Double> freqs = new ArrayList<Double>(frequencies.size());
		for(double d : frequencies)
			freqs.add(d);
		return freqs;
	}
	
	public ArrayList<Complex> getValues()
	{
		ArrayList<Complex> vals = new ArrayList<>(values.size());
		for(Complex c : values)
			vals.add(c.clone());
		return vals;
	}
	
	private void addFrequency(double frequency)
	{
		frequencies.add(frequency);
	}
	
	private void addValue(Complex value) throws FourierException
	{
		if(value == null)
			throw new FourierException("Complex values added to Fourier Array cannot be null.");
		values.add(value);
	}
	
	public void add(double frequency, Complex value) throws FourierException
	{
		addFrequency(frequency);
		addValue(value);
		size++;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof FourierData)
		{
			FourierData d = (FourierData) o;
			ArrayList<Double> freqs = d.getFrequencies();
			ArrayList<Complex> vals = d.getValues();
			if(size() != d.size())
				return false;
			else
			{
				for(int i = 0; i < frequencies.size(); i++)
				{
					if(freqs.get(i) != frequencies.get(i) || !vals.get(i).equals(values.get(i)))
						return false;
				}
				return true;
			}
		}
		return false;
	}
}
