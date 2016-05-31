package fourier;

import java.util.ArrayList;

public class TimeData 
{
	//TODO: change this to a HashMap
	private ArrayList<Double> times;
	private ArrayList<Double> values;
	private double samplingFrequency;
	private int size;
	private final double tolerance = 1.0E-12;
	
	/**
	 * Create a TimeData object.
	 */
	public TimeData()
	{
		times = new ArrayList<>();
		values = new ArrayList<>();
		samplingFrequency = 0;
		size = 0;
	}
	
	/**
	 * Add a new value to the TimeData object. Note that time is sequential - if any added time comes before the latest time-point,
	 * it will throw an error.
	 */
	public void add(double time, double value) throws DataException
	{
		if(!times.isEmpty() && time < times.get(times.size() - 1))
			throw new DataException("Time added to TimeData must be sequential.");
		times.add(time);
		values.add(value);
		size++;
		if(size >= 2)
		{
			double potentialSamplingFrequency = 1/(times.get(size-1) - times.get(size - 2));
			/*System.out.println("Size is " + size + "; t(size-1) = " + times.get(size-1) + "; t(size-2) = " + times.get(size-2)); 
			System.out.println("Potential: " + potentialSamplingFrequency + " || Actual: " + samplingFrequency + 
					" || Difference: " + (potentialSamplingFrequency - samplingFrequency));*/
			if(potentialSamplingFrequency < 0)
				throw new DataException("Added time must be greater than previous time data.");
			else if(samplingFrequency == 0)
				samplingFrequency = potentialSamplingFrequency;
			else if(Math.abs(potentialSamplingFrequency - samplingFrequency) > tolerance)
				throw new DataException("TimeData must be taken at standard intervals.");
		}
	}
	
	/**
	 * Get the underlying list of time values (doubles)
	 * @return ArrayList<Double> representing time
	 */
	public ArrayList<Double> getTimes()
	{
		ArrayList<Double> returnable = new ArrayList<Double>(times.size());
		for(double d : times)
			returnable.add(d);
		return returnable;
	}
	
	/**
	 * Get the underlying list of values (doubles)
	 * @return ArrayList<Double> representing y-axis data
	 */
	public ArrayList<Double> getValues()
	{
		ArrayList<Double> returnable = new ArrayList<Double>(values.size());
		for(double d : values)
			returnable.add(d);
		return returnable;
	}
	
	/**
	 * Reset the time and data arrays to something different
	 * @param time ArrayList<Double> representing time, of the same size as the data parameter
	 * @param data ArrayList<Double> representing y-axis data, of the same size as the time parameter
	 * @throws DataException
	 */
	public void setData(ArrayList<Double> time, ArrayList<Double> data) throws DataException
	{
		if(time.size() != data.size())
			throw new DataException("Time and Data vector dimensions do not agree.");
		setTimes(time);
		setValues(data);
	}
	
	private void setTimes(ArrayList<Double> time)
	{
		values = new ArrayList<Double>(time.size());
		for(double d : time)
			values.add(d);
	}
	
	private void setValues(ArrayList<Double> data)
	{
		values = new ArrayList<Double>(data.size());
		for(double d : data)
			values.add(d);
	}
	
	public double getSamplingFrequency()
	{
		return samplingFrequency;
	}
}
