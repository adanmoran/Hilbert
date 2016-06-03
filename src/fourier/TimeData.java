package fourier;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TimeData 
{
	//TODO: change this to a HashMap
	private final LinkedHashMap<Double, Double> data;
	private double period;
	private final double tolerance = 1.0E-12;
	
	/**
	 * Create a TimeData object. The period must be set later with the setPeriod() method.
	 * 
	 * O(1)
	 */
	public TimeData() throws DataException
	{
		this(0);
	}
	
	/**
	 * Create a TimeData object with a specified period (in seconds)
	 * @param period
	 * @throws DataException if the period is invalid
	 * 
	 * O(1)
	 */
	public TimeData(double period) throws DataException
	{
		data = new LinkedHashMap<>();
		setPeriod(period);
	}
	
	/**
	 * Set the period for the TimeData object. Note that a period
	 * @param period
	 * @throws DataException
	 * 
	 * O(1)
	 */
	public void setPeriod(double period) throws DataException
	{
		if(!data.isEmpty())
			throw new DataException("TimeData period cannot be changed when it already contains entrys.");
		if(period < 0)
			throw new DataException("TimeData period cannot be negative.");
		this.period = period;
	}
	
	/**
	 * Add a new value to the TimeData object with a non-zero period.
	 * Added time must be sequential (no adding data from the past), and it must satisfy the period of this instance.
	 * 
	 * @param time a real-valued time step
	 * @param value the real value associated with this time step
	 * @throws DataException if the added time is in the past, does not satisfy this instance's period, or if the period has not been set.
	 */
	public void add(double time, double value) throws DataException
	{
		Double[] times = getTimes();
		if(!data.isEmpty() && time < times[times.length - 1])
			throw new DataException("Time added to TimeData must be sequential.");
		
		if(data.size() >= 2)
		{
			double potentialPeriod = time - times[times.length - 1];

			if(potentialPeriod < 0)
				throw new DataException("Added time must be greater than previous time data.");
			else if(period == 0)
				throw new DataException("Period must be set before adding elements to TimeData objects.");
			else if(Math.abs(period - potentialPeriod) > tolerance)
				throw new DataException("Elements added to TimeData must be given at regular intervals.");
		}
		data.put(time, value);
	}
	
	/**
	 * Get the underlying list of time values as an array
	 * @return Double[] representing time
	 * 
	 */
	public Double[] getTimes()
	{
		return data.keySet().toArray(new Double[data.size()]);
	}
	
	/**
	 * Get the underlying list of values as an array
	 * @return Double[] representing y-axis data
	 */
	public Double[] getValues()
	{
		return data.values().toArray(new Double[data.size()]);
	}
	
	/**
	 * Add a collection of values to this object
	 * @param time ArrayList<Double> representing time, of the same size as the data parameter
	 * @param data ArrayList<Double> representing y-axis data, of the same size as the time parameter
	 * @throws DataException when the times and values vectors are not of the same size
	 * 
	 * For n = size of parameters, this operates in O(n)
	 */
	public void addCollection(ArrayList<Double> times, ArrayList<Double> values) throws DataException
	{
		if(times == null || values == null)
			throw new DataException("Cannot add null collection to TimeData object.");
		else if(times.size() != values.size())
			throw new DataException("Time and Value vector dimensions do not agree.");
		else if(times.size() == 0)
			return;
		
		for(int i = 0; i < times.size(); i++)
			add(times.get(i), values.get(i));
	}
	
	/**
	 * Add a collection of values to this object
	 * @param time Double[] representing time, of the same size as the data parameter
	 * @param data Double[] representing y-axis data, of the same size as the time parameter
	 * @throws DataException when the times and values arrays are not of the same size
	 * 
	 * For n = size of parameters, this operates in O(n)
	 */
	public void addCollection(Double[] times, Double[] values) throws DataException
	{
		if(times.length != values.length)
			throw new DataException("Time and Value vector dimensions do not agree.");
		for(int i = 0; i < times.length; i++)
			add(times[i], values[i]);
	}
	
	/**
	 * Get the number of entries.
	 * @return The number of Time-Value pairs.
	 * 
	 * O(1)
	 */
	public int size()
	{
		return data.size();
	}
	
	/**
	 * Get the period in seconds.
	 * @return period
	 * 
	 * O(1)
	 */
	public double getPeriod()
	{
		return period;
	}
	
	/**
	 * Get the sampling frequency in Hz.
	 * @return 1/period
	 * 
	 * O(1)
	 */
	public double getSamplingFrequency()
	{
		return ( (period == 0) ? 0 : 1/period );
	}
	
	/**
	 * Remove all entries of Time-Value pairs in this instance.
	 * 
	 * O(1)
	 */
	public void clear()
	{
		data.clear();
	}
}
