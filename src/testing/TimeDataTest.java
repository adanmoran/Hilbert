package testing;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import fourier.DataException;
import fourier.TimeData;

public class TimeDataTest {

	@Test
	public void testTimeData()
	{
		TimeData td0 = new TimeData();
		TimeData td1 = new TimeData();
		assertThat(td0,notNullValue());
		assertThat(td1,notNullValue());
		assertThat(td0,is(not(sameInstance(td1))));
	}
	@Test
	public void testAddValidInput() throws DataException {
		TimeData tData = new TimeData();
		for(int i = 0; i < 10; i++)
		{
			if(i == 0)
				tData.add(i, 1);
			else
				tData.add(i, 0);
		}
		tData = new TimeData();
		double period = Math.random();
		double time = 0;
		for(int i = 0; i < 1000; i++)
		{
			tData.add(time, Math.random());
			time += period;
		}
	}

	@Test
	public void testGetTimes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValues() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSamplingFrequency() {
		fail("Not yet implemented");
	}

}
