package fourier;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class Data<T extends Number,S> 
{
	private LinkedHashMap<T,S> data;
	private double period;
	private double previousElementAsDouble;
	
	public Data() throws DataException
	{
		this(0);
	}
	
	public Data(double period) throws DataException
	{
		data = new LinkedHashMap<>();
		if(period == 0)
			this.period = 0;
		else
			setPeriod(period);
	}
	
	/**
	 * Get a copy of the underlying LinkedHashMap.
	 * @return data a LinkedHashMap<T,S> structure
	 */
	public LinkedHashMap<T,S> getMap()
	{
		return new LinkedHashMap<T,S>(data);
	}
	
	/**
	 * Set the period for the Data object. Note that a period must be non-zero positive. The Data object must be empty to set a period.
	 * @param period
	 * @throws DataException
	 * 
	 * O(1)
	 */
	public void setPeriod(double period) throws DataException
	{
		if(!data.isEmpty())
			throw new DataException("Data period cannot be changed when it already contains entrys.");
		if(period <= 0)
			throw new DataException("Data period cannot be negative.");
		this.period = period;
	}
	
	/**
	 * Add a new value to the Data object with a non-zero period.
	 * Added x-value must satisfy the period of this instance.
	 * @param x An x-axis value of Number type
	 * @param y A y-axis value of type S
	 * @throws DataException 
	 */
	public void add(T x, S y) throws DataException
	{
		if(period == 0)
			throw new DataException("Period must be set before adding elements to Data objects.");
		if(x == null || y == null)
			throw new DataException("Cannot add null pointers to Data object.");
		if(data.size() >= 1)
		{
			double potentialPeriod = x.doubleValue() - previousElementAsDouble;

			if(potentialPeriod < 0)
				throw new DataException("Added x-value must be greater than previous x-value data.");
			else if(Math.abs(period - potentialPeriod) > 0)
				throw new DataException("Elements added to Data must be given at regular intervals.");
		}
		previousElementAsDouble = x.doubleValue();
		data.put(x, y);
	}
	
	/**
	 * Add a collection of values to a Data object with non-zero period.
	 * Each element must satisfy the period of this instance.
	 * @param xVals ArrayList<Double> representing x-axis data, of the same size as the yVals parameter
	 * @param yVals ArrayList<Double> representing y-axis data, of the same size as the xVals parameter
	 * @throws DataException when the xVals and yVals vectors are not of the same size
	 * 
	 * For n = size of parameters, this operates in O(n)
	 */
	public void addCollection(ArrayList<T> xVals, ArrayList<S> yVals) throws DataException
	{
		if(xVals == null || yVals == null)
			throw new DataException("Cannot add null collection to Data object.");
		else if(xVals.size() != yVals.size())
			throw new DataException("X and Y vector dimensions do not agree.");
		else if(xVals.size() == 0)
			return;
		
		for(int i = 0; i < xVals.size(); i++)
			add(xVals.get(i), yVals.get(i));
	}
	
	/**
	 * Add a collection of values to this object
	 * @param xVals Double[] representing x-axis data, of the same size as the yVals parameter
	 * @param yVals Double[] representing y-axis data, of the same size as the xVals parameter
	 * @throws DataException when the xVals and yVals arrays are not of the same size
	 * 
	 * For n = size of parameters, this operates in O(n)
	 */
	public void addCollection(T[] xVals, S[] yVals) throws DataException
	{
		if(xVals.length != yVals.length)
			throw new DataException("X and Y vector dimensions do not agree.");
		for(int i = 0; i < xVals.length; i++)
			add(xVals[i], yVals[i]);
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
	 * Get the number of entries.
	 * @return The number of T-S pairs.
	 * 
	 * O(1)
	 */
	public int size()
	{
		return data.size();
	}
	
	/**
	 * Remove all entries of T-S pairs in this instance.
	 * 
	 * O(1)
	 */
	public void clear()
	{
		data.clear();
	}
}
