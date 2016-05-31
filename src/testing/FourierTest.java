package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fourier.Complex;
import fourier.DataException;
import fourier.FourierData;
import fourier.FourierTransform;
import fourier.TimeData;

public class FourierTest {

	@Test
	public void testDdftSuccess() throws DataException {
		//Test set 1: Unitary impulse function (1 at 0, 0 elsewhere)
		TimeData data = new TimeData();
		FourierData actualData = new FourierData();
		for(int i = 0; i < 8; i++)
		{
			if (i == 0)
				data.add(i,1);
			else
			{
				data.add(i, 0);
			}
			actualData.add(i, new Complex(0.125,0));
		}
		FourierData results = FourierTransform.ddft(data);
		assertTrue(results.equals(actualData));
		//Test set 2: Shifted Unitary Impulse Function (1 at 1, 0 elsewhere)
		data = new TimeData();
		for(int i = 0; i < 8; i++)
		{
			if(i == 1)
				data.add(i, 1);
			else
				data.add(i, 0);
		}
		int sign = 1;
		for(int i = 0; i < 8; i++)
		{
			if(i < 4)
				sign = -1;
			if(i % 4 == 0)
				actualData.add(i, new Complex(sign * 0.125,0));
			else if(i % 4 == 1)
				actualData.add(i, new Complex(sign * 0.088, -sign* 0.088));
			else if(i % 4 == 2)
				actualData.add(i, new Complex(0, -sign * 0.125));
			else
				actualData.add(i, new Complex(-sign*0.088, -sign*0.088));
		}
		results = FourierTransform.ddft(data);
		assertTrue(results.equals(actualData));
	}
	
	@Test
	public void testDdftFail()
	{
		fail("Not yet implemented.");
	}

}
