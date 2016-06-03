package testing;

import static org.junit.Assert.*;

import org.junit.Rule;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import fourier.DataException;
import fourier.TimeData;

public class TimeDataTest {
	
	@Test
	public void testTimeDataValidInput()
	{
		//Zero Period
		try
		{
			TimeData td0 = new TimeData();
			TimeData td1 = new TimeData();
			assertThat(td0,notNullValue());
			assertThat(td1,notNullValue());
			assertThat(td0,is(not(sameInstance(td1))));
		}
		catch(DataException e)
		{
			fail(e.getMessage());
		}
		
		//Non-zero, positive period
		try
		{
			TimeData td0, td1;
			for(int i = 0; i < 1000; i += 100)
			{
				td0 = new TimeData(i * Math.random());
				td1 = new TimeData(i * Math.random());
				assertThat(td0,notNullValue());
				assertThat(td1,notNullValue());
				assertThat(td0,is(not(sameInstance(td1))));
			}
		} catch(DataException e)
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testTimeDataInvalidInputThrowsDataException()
	{
		fail("Not yet implemented.");
	}
	
	@Test
	public void testAddValidInput() throws DataException
	{
		TimeData tData = new TimeData(1);
		for(int i = 0; i < 10; i++)
		{
			if(i == 0)
				try
				{
					tData.add(i, 1);
				}catch(DataException e)
				{fail("DataException thrown on adding valid input; " + e.getMessage());}
			else
				try
				{
					tData.add(i, 0);
				}catch(DataException e)
				{fail("DataException thrown on adding valid input; " + e.getMessage());}
		}
		tData = new TimeData();
		double period = Math.random();
		tData.setPeriod(period);
		double time = 0;
		for(int i = 0; i < 1000; i++)
		{
			try
			{
				tData.add(time, Math.random());
			}catch(DataException e)
			{ fail("DataException thrown on adding valid input with period " + period + "; " + e.getMessage());}
			time += period;
		}
	}
	
	@Test
	public void testAddInvalidInputThrowsDataException()
	{
		fail("Not yet implemented.");
	}

	@Test
	public void testGetTimes() throws DataException
	{
		TimeData tData;
		double period = 0;
		double time;
		Double[] times;
		for(int i = 0; i < 100; i++)
		{
			period = Math.random();
			tData = new TimeData(period);
			time = 0;
			times = new Double[10];
			for(int j = 0; j < 10; j++)
			{
				try 
				{
					tData.add(time, Math.random() * 100);
				} catch (DataException e) 
				{
					e.printStackTrace();
					fail("Failed getTimes() on period of " + period + " at time value of " + time + "; " + e.getMessage());
				}
				times[j] = time;
				time += period;
			}
			assertThat("getTimes() returned a null value.", tData.getTimes(), is(notNullValue()));
			assertThat("getTimes() returned array of length 0", tData.getTimes().length, is(10));
			assertThat("getTimes() returned value did not match actual times array.", tData.getTimes(), is(times));
		}
	}

	@Test
	public void testGetValues() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddCollectionArrayList() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddCollectionDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSamplingFrequencyValid() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetSamplingFrequencyInvalidAddedInputThrowsDataException() throws DataException
	{
		TimeData tData = new TimeData();
		fail("Not yet implemented.");
		/*
		   try
		   {
				//Insert testing method here
				fail("Inserting samp");
				}
				catch(DataException e){}*/
	}

}
