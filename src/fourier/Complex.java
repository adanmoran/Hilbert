package fourier;

/**
 * This class describes a Complex number, and includes
 * all the basic operations that can be performed on 
 * complex numbers.
 * @author Adan Moran-MacDonald
 * @since 13-05-2016
 *
 */
public class Complex 
{
	private final double real, imaginary;
	
	public Complex(double real, double imaginary)
	{
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Convert a complex number into a string
	 * @param c Any Complex number
	 * @return the String representation of parameter
	 */
	public static String toString(Complex c)
	{
		if(c.getImaginary() < 0)
			return Double.toString(c.getReal()) + " - " + Double.toString(-c.getImaginary()) + "i";
		return Double.toString(c.getReal()) + " + " + Double.toString(c.getImaginary()) + "i";
	}
	
	/**
	 * @return The String representation of a Complex number
	 */
	public String toString()
	{
		if(imaginary < 0)
			return Double.toString(real) + " - " + Double.toString(-imaginary) + "i";
		return Double.toString(real) + " + " + Double.toString(imaginary) + "i";
			
	}
	
	/**
	 * Get the real part of the complex number
	 * @return Re{this}
	 */
	public double getReal()
	{
		return real;
	}
	
	/**
	 * Get the imaginary part of the complex number
	 * @return Im{this}
	 */
	public double getImaginary()
	{
		return imaginary;
	}
	
	/**
	 * Determine if a complex number is zero
	 * @param c Any Complex number
	 * @return true if both real and imaginary parts are zero
	 */
	public static boolean isZero(Complex c)
	{
		return (c.getReal() == 0 && c.getImaginary() == 0);
	}
	
	/**
	 * Determine if the complex number is zero
	 * @return true if both real and imaginary parts are zero
	 */
	public boolean isZero()
	{
		return (real == 0 && imaginary == 0);
	}
	
	/**
	 * Get the absolute value - or magnitude - of the complex number
	 * @return norm{this}
	 */
	public double abs()
	{
		return Math.hypot(real, imaginary);
	}
	
	/**
	 * Get the angle the complex number forms with the Real axis
	 * @return angle (in radians) in the range (-pi,pi]
	 */
	public double phase()
	{
		return Math.atan2(real, imaginary);
	}
	
	/**
	 * Get the complex conjugate of the complex number
	 * @return A new Complex object that is the conjugate of this
	 */
	public Complex conjugate()
	{
		return new Complex(real, -imaginary);
	}
	
	/**
	 * Get the reciprocal of the complex number
	 * @return A new Complex object whose value is 1/this
	 */
	public Complex reciprocal()
	{
		if(real == 0 && imaginary == 0)
			return this;
		double scale = real*real + imaginary*imaginary;
		return new Complex(real / scale, imaginary / scale);
	}
	
	/**
	 * Complex addition
	 * @param c Any Complex number
	 * @return A new Complex object whose value is (this + parameter)
	 */
	public Complex plus(Complex c)
	{
		double re = this.real + c.getReal();
		double im = this.imaginary + c.getImaginary();
		return new Complex(re,im);
	}
	
	/**
	 * Complex subtraction
	 * @param c Any Complex number
	 * @return A new Complex object whose value is (this - parameter)
	 */
	public Complex minus(Complex c)
	{
		double re = this.real - c.getReal();
		double im = this.imaginary - c.getImaginary();
		return new Complex(re, im);
	}
	
	/**
	 * Complex multiplication
	 * @param c Any Complex number
	 * @return A new Complex object whose value is (this * parameter)
	 */
	public Complex times(Complex c)
	{
		double re = this.real * c.getReal() - this.imaginary * c.getImaginary();
		double im = this.real * c.getImaginary() + this.imaginary * c.getReal();
		return new Complex(re,im);
	}
	
	/**
	 * Scalar multiplication
	 * @param alpha Any real scalar
	 * @return A new Complex object whose value is (this * alpha)
	 */
	public Complex times(double alpha)
	{
		return new Complex(alpha * real, alpha * imaginary);
	}
	
	/**
	 * Complex division
	 * @param c Any Complex number
	 * @return A new Complex object whose value is (this / parameter)
	 */
	public Complex divide(Complex c) throws ArithmeticException
	{
		if(c.isZero())
			throw new ArithmeticException("Cannot divide by zero.");
		return this.times(c.reciprocal());
	}
	
	/**
	 * Complex exponentiation
	 * @param c Any Complex number
	 * @return A new Complex object whose value is exp(parameter)
	 */
	public static Complex exp(Complex c)
	{
		return new Complex(Math.exp(c.getReal()) * Math.cos(c.getImaginary()), Math.exp(c.getReal()) * Math.sin(c.getImaginary()));
	}
	
	/**
	 * Complex exponentiation
	 * @return A new Complex object whose value is exp(this)
	 */
	public Complex exp()
	{
		return Complex.exp(this);
	}
	
	/**
	 * Complex cosine
	 * @param c Any Complex number
	 * @return A new Complex object whose value is cos(parameter)
	 */
	public static Complex cos(Complex c)
	{
		return new Complex(Math.cos(c.getReal()) * Math.cosh(c.getImaginary()), -Math.sin(c.getReal()) * Math.sinh(c.getImaginary()));
	}
	
	/**
	 * Complex cosine
	 * @return A new Complex object whose value is cos(this)
	 */
	public Complex cos()
	{
		return Complex.cos(this);
	}
	
	/**
	 * Complex sine
	 * @param c Any Complex number
	 * @return A new Complex object whose value is sin(parameter)
	 */
	public static Complex sin(Complex c)
	{
		return new Complex(Math.sin(c.getReal()) * Math.cosh(c.getImaginary()), Math.cos(c.getReal()) * Math.sinh(c.getImaginary()));
	}
	
	/**
	 * Complex sine
	 * @return A new Complex object whose value is sin(this)
	 */
	public Complex sin()
	{
		return Complex.sin(this);
	}
	
	/**
	 * Complex tan
	 * @param c Any Complex number
	 * @return A new Complex object whose value is tan(parameter)
	 */
	public static Complex tan(Complex c)
	{
		return sin(c).divide(cos(c));
	}
	
	/**
	 * Complex tan
	 * @return A new Complex object whose value is tan(this)
	 */
	public Complex tan()
	{
		return sin().divide(cos());
	}

}
