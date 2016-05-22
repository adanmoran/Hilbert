package fourier;

import java.util.ArrayList;

public class FourierTransform
{
	private FourierData data;
	public static FourierData ddft(Complex[] timeDomainData) throws FourierException
	{
		// Get the next power of 2
		int N = timeDomainData.length;
		if(N == 0)
			throw new FourierException("Cannot transform empty arrays.");
		int nextPow2 = 32 - Integer.numberOfLeadingZeros(N - 1);
		// Create arrayLists of the correct size (powers of 2)
		ArrayList<Double> frequencies = new ArrayList<>(nextPow2);
		ArrayList<Complex> values = new ArrayList<>(nextPow2);
		
		//TODO: all of the fourier transform stuff
		return null;
	}
	
	public static FourierData ddft(double[] timeDomainData) throws FourierException
	{
		//Convert from double[] to Complex[] 
		Complex[] data = new Complex[timeDomainData.length];
		for(int i = 0; i < data.length; i++)
		{
			data[i] = new Complex(timeDomainData[i],0);
		}
		return ddft((Complex[]) data);
	}
}
