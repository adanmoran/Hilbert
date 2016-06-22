package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

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
		TimeData td;
		double period;
		//Negative period
		for(int i = -10000; i < 0; i++)
		{
			period = i * Math.random();
			try
			{
				td = new TimeData(period);
				//Exception should be thrown here, so fail if it wasn't
				fail("Exception was NOT thrown for constructor with parameter " + period);
			} catch(Exception e)
			{
				assertThat(e.getClass().getName(), is(DataException.class.getName()));
			}
		}
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
		TimeData td;
		//Attempt to add data when period is zero
		try
		{
			td = new TimeData(0);
			td.add(0, 1);
			fail("TimeData.add() did not throw an exception when adding data to an object with zero period.");
		}
		catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
		//Attempt to add time that is not a one period's distance away
		try
		{
			td = new TimeData(1);
			td.add(0, 1);
			td.add(1, 1);
			td.add(1.5, 1);
			fail("TimeData.add() did not throw an exception for a time that is not one period distance away.");
		}
		catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
		//Attempt to add time prior to already added times
		try
		{
			td = new TimeData(1);
			td.add(0, 1);
			td.add(-1, 1);
			fail("TimeData.add() did not throw an exception when adding time prior to added times..");
		}
		catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
	}
	
	@Test
	public void testSetPeriodValidInput()
	{
		try
		{
			TimeData td = new TimeData();
			for(int i = 0; i < 1000000000; i+= 100)
				td.setPeriod(i * Math.random());
			
		} catch(DataException e)
		{
			fail("setPeriod() testing threw an exception for a valid input.");
		}
	}
	
	@Test
	public void testSetPeriodInvalidInputThrowsDataException()
	{
		TimeData td;
		//Try to set period with negative values
		try
		{
			td = new TimeData();
			td.setPeriod(-1);
			fail("Exception was NOT thrown for a setPeriod() value of -1");
		} catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
		//Try to set a period when TimeData size() is non-zero
		try
		{
			td = new TimeData();
			double period = Math.random();
			td.setPeriod(period);
			td.add(0, Math.random());
			double anotherPeriod = Math.random();
			td.setPeriod(anotherPeriod);
			fail("Exception was NOT thrown for a setPeriod() call when TimeData() already contained values.");
		} catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
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
		//Now test that getValues() is empty when used on a new object.
		tData = new TimeData();
		assertThat("getTimes() returned a null value on a valid empty object.", tData.getTimes(), is(notNullValue()));
		assertThat("getTimes() is not empty for an empty object.", tData.getTimes().length, is(0));
	}

	@Test
	public void testGetValues() {
		TimeData tData;
		double period = 0;
		double value, time;
		Double[] values;
		try
		{
			for(int i = 0; i < 100; i++)
			{
				period = Math.random();
				tData = new TimeData(period);
				time = 0;
				values = new Double[10];
				for(int j = 0; j < 10; j++)
				{
					value = Math.random() * 100000;
					try 
					{
						tData.add(time, value);
					} catch (DataException e) 
					{
						e.printStackTrace();
						fail("Failed getValues() on period of " + period + " with value " +  value + "; " + e.getMessage());
					}
					values[j] = value;
					time += period;
				}
				assertThat("getValues() returned a null value.", tData.getValues(), is(notNullValue()));
				assertThat("getValues() returned array of length 0", tData.getValues().length, is(10));
				assertThat("getValues() returned value did not match actual times array.", tData.getValues(), is(values));
			}
			//Now test that getValues() is empty when used on a new object.
			tData = new TimeData();
			assertThat("getValues() returned a null value on an empty object.", tData.getValues(), is(notNullValue()));
			assertThat("getValues() is not empty for an empty object.", tData.getValues().length, is(0));
		}
		catch(DataException e)
		{
			fail("getValues() threw a data exception when it shouldn't have.");
		}
	}

	@Test
	public void testAddCollectionArrayListValidInput() {
		TimeData tData;
		double period;
		ArrayList<Double> times, values;
		try
		{
			tData = new TimeData();
			for(int i = 0; i < 100; i++)
			{
				period = Math.random();
				tData.clear();
				tData.setPeriod(period);
				times = new ArrayList<>();
				values = new ArrayList<>();
				for(int j = 0; j < 100; ++j)
				{
					times.add(j * period);
					values.add(Math.random() * j * 10000);
				}
				assertThat(times.size(), is(values.size()));
				assertThat(times, is(notNullValue()));
				assertThat(values,is(notNullValue()));
				tData.addCollection(times, values);
				assertThat(tData.size(), is(times.size()));
			}
		}
		catch(DataException e)
		{
			fail("addCollection(ArrayList<>) threw a data exception when it shouldn't have:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testAddCollectionArrayListInvalidInput()
	{
		ArrayList<Double> times = new ArrayList<Double>(10);
		for(int i = 0; i < 10; i++)
		{
			times.add((double)i);
		}
		ArrayList<Double> values;
		TimeData td;
		//Attempt to add arrays of different sizes
		try
		{
			td = new TimeData(1);
			values = new ArrayList<>();
			td.addCollection(times, values);
			fail("addCollection(Double[]) did not throw an Exception when times and values dimensions don't match.");
		}
		catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
		// Attempt to add null array
		try
		{
			td = new TimeData(1);
			values = null;
			td.addCollection(times, values);
			fail("addCollection(Double[]) did not throw an Exception when times and values dimensions don't match.");
		}
		catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
	}
	
	@Test
	public void testAddCollectionDoubleArrayValidInput() {
		TimeData tData;
		double period;
		Double[] times = new Double[100];
		Double[] values = new Double[100];
		try
		{
			tData = new TimeData();
			for(int i = 0; i < 100; i++)
			{
				period = Math.random();
				tData.clear();
				tData.setPeriod(period);
				for(int j = 0; j < 100; ++j)
				{
					times[j] = j * period;
					values[j] = Math.random() * j * 10000;
				}
				assertThat(times.length, is(values.length));
				assertThat(times, is(notNullValue()));
				assertThat(values,is(notNullValue()));
				tData.addCollection(times, values);
				assertThat(tData.size(), is(times.length));
			}
		}
		catch(DataException e)
		{
			fail("addCollection(Double[]) threw a data exception when it shouldn't have:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testAddCollectionDoubleArrayInvalidInput()
	{
		Double[] times = new Double[10];
		for(int i = 0; i < 10; i++)
		{
			times[i] = (double)i;
		}
		Double[] values;
		TimeData td;
		//Attempt to add arrays of different sizes
		try
		{
			td = new TimeData(1);
			values = new Double[9];
			td.addCollection(times, values);
			fail("addCollection(Double[]) did not throw an Exception when times and values dimensions don't match.");
		}
		catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
		// Attempt to add null array
		try
		{
			td = new TimeData(1);
			values = null;
			td.addCollection(times, values);
			fail("addCollection(Double[]) did not throw an Exception when times and values dimensions don't match.");
		}
		catch(Exception e)
		{
			assertThat(e.getClass().getName(), is(DataException.class.getName()));
		}
	}

	@Test
	public void testGetSamplingFrequency() 
	{
		double period = Math.random();
		double expectedFrequency = 1/period;
		TimeData td;
		//Show that the sampling frequency is 1/period for non-zero period, and 0 when period is 0
		try
		{
			//Valid non-zero period
			td = new TimeData(period);
			assertThat(td.getSamplingFrequency(), is(expectedFrequency));
			//Zero period
			td = new TimeData(0);
			assertThat(td.getSamplingFrequency(), is(0.0));
			//Default constructor should have 0 period
			td = new TimeData();
			assertThat(td.getSamplingFrequency(), is(0.0));
		} catch(DataException e)
		{
			fail("getSamplingFrequency() threw an exception when it shouldn't have:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testSize()
	{
		TimeData td;
		try
		{
			double period = Math.random();
			td = new TimeData(period);
			double time, value;
			int expectedSize = 100;
			for(int i = 0; i < 100; i++)
			{
				time = i * period;
				value = Math.random();
				td.add(time, value);
			}
			assertThat(td.size(), is(expectedSize));
		} catch(DataException e)
		{
			fail("Testing for size gave an exception when it shouldn't have:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testGetPeriod()
	{
		double period = Math.random();
		TimeData td;
		//Show that the sampling frequency is 1/period for non-zero period, and 0 when period is 0
		try
		{
			//Valid non-zero period
			td = new TimeData(period);
			assertThat(td.getPeriod(), is(period));
			//Zero period
			td = new TimeData(0);
			assertThat(td.getPeriod(), is(0.0));
			//Default constructor should have 0 period
			td = new TimeData();
			assertThat(td.getPeriod(), is(0.0));
		} catch(DataException e)
		{
			fail("getPeriod() threw an exception when it shouldn't have:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testClear()
	{
		TimeData td;
		try
		{
			double period = Math.random();
			td = new TimeData(period);
			double time, value;
			int expectedSize = 100;
			for(int i = 0; i < 100; i++)
			{
				time = i * period;
				value = Math.random();
				td.add(time, value);
			}
			assertThat("Size before clearing was not correct.",td.size(), is(expectedSize));
			td.clear();
			assertThat("Size after clearing was not correct.", td.size(), is(0));
			assertThat("getTimes() was not empty after clearing.", td.getTimes().length, is(0));
			assertThat("getValues() was not empty after clearing.", td.getValues().length, is(0));
		} catch(DataException e)
		{
			fail("Testing clear() gave an exception when it shouldn't have:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testEqualsValidInput()
	{
		try
		{
			double period = Math.random(), time, value;
			TimeData td1 = new TimeData(period), td2 = new TimeData(period);
			for(int i = 0; i < 10; i++)
			{
				time = period * i;
				value = Math.random();
				td1.add(time, value);
				td2.add(time, value);
				assertThat(td1.equals(td2),is(true));
			}
		}
		catch(DataException e)
		{
			fail("Testing equals() gave an exception when it shouldn't have:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testEqualsInvalidInput()
	{
		//Equality should fail with non-TimeData object
		LinkedHashMap<Double, Double> map = new LinkedHashMap<>();
		map.put(0.0, 0.0);
		try
		{
			TimeData td = new TimeData(1);
			td.add(0, 0);
			assertThat(td.equals(map), is(not(true)));
		}
		catch(Exception e)
		{
			fail("Testing equals() for invalid input gave an exception:\n" + e.getMessage());
		}
		
		//Equality should fail with TimeData objects with different periods, or objects that are not identical
		try
		{
			TimeData td1 = new TimeData(1), td2 = new TimeData(2);
			td1.add(0, 0);
			td2.add(0, 0);
			assertThat(td1.equals(td2),is(not(true)));
			td2.clear();
			td2.setPeriod(td1.getPeriod());
			td2.add(td1.getPeriod(), 1);
			assertThat(td1.equals(td2),is(not(true)));
		}
		catch(Exception e)
		{
			fail("Testing equals() for invalid input gave an exception:\n" + e.getMessage());
		}
	}
	
	@Test
	public void testClone()
	{
		TimeData td1, cloned;
		try
		{
			td1 = new TimeData(1);
			for(int i = 0; i < 10; i++)
			{
				td1.add(i, Math.random());
			}
			cloned = td1.clone();
			assertThat(cloned.getClass().getName(), is(TimeData.class.getName()));
			assertThat(cloned.size(), is(td1.size()));
			assertThat(cloned.getValues(), is(td1.getValues()));
			assertThat(cloned.getTimes(), is(td1.getTimes()));
			assertThat(cloned.getPeriod(), is(td1.getPeriod()));
			assertThat(cloned.equals(td1), is(true));
		}
		catch(DataException e)
		{
			fail("Testing equals() for invalid input gave an exception:\n" + e.getMessage());
		}
	}

}
