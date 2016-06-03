package fourier;

import java.util.ArrayList;

public class FourierTransform
{
	public static FourierData ddft(TimeData tData) throws FourierException
	{
		// Get the time data so we can use it to compute the fourier data
		Double[] time = tData.getTimes();
		Double[] tVals = tData.getValues();
		
		// Get the next power of 2
		int N = time.length;
		if(N == 0)
			throw new FourierException("Cannot transform empty arrays.");

		int nextPow2 = (N % 2 != 0) ? (32 - Integer.numberOfLeadingZeros(N - 1)) : N;
		
		// Create arrayLists of the correct size (powers of 2) to contain the FourierData elements
		Double[] frequencies = new Double[nextPow2];
		Complex[] values = new Complex[nextPow2];
		
		//Calculate the frequencies using the time array and set frequencies to this frequency data
		double samplingFrequency = tData.getSamplingFrequency();
		for(int k = 0; k < N; k++)
			frequencies[k] = (samplingFrequency * k / N);
		//We initialize an array because the fft function (from Princeton) uses Complex[] as an input.
		
		for(int i = 0; i < N; i++)
		{
			if(i < tVals.length)
				values[i] = new Complex(tVals[i],0);
			else
				values[i] = new Complex(0,0);
		}
			
		// Compute the fourier transform using Princeton's FFT function
		values = fft(values);
		
		// Set the FourierData with the above frequency and Complex-valued data
		FourierData returnableData = new FourierData(frequencies, values);
		return returnableData;
	}
	
	//TODO: Add in the license for the FFT function 
	// compute the FFT of x[], assuming its length is a power of 2
    private static Complex[] fft(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }
    
    //TODO: Create an IDDFT function using the same procedure as with the DDFT one above
}
