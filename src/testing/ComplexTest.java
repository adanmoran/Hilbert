package testing;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Assert;
import org.junit.Test;

import fourier.Complex;

public class ComplexTest {
	

	@Test
	public void testComplex() {
		Complex complex;
		for(int i = 0; i < 10000; i+= 100)
		{
			int real_neg = Math.random() > 0.5 ? 1 : -1;
			int im_neg = Math.random() > 0.5 ? 1 : -1;
			complex = new Complex(Math.random()*i*real_neg,Math.random()*i*im_neg);
			Assert.assertNotNull(complex);
		}
	}

	@Test
	public void testToStringComplex() {
		assertEquals("0.0 + 0.0i", Complex.toString(new Complex(0, 0)));
		assertEquals("0.0 - 1.0i", Complex.toString(new Complex(0, -1)));
		assertEquals("0.0 - 1.0E-7i", Complex.toString(new Complex(0, -0.0000001)));
		assertEquals("0.0 - 1000000.0i", Complex.toString(new Complex(0, -1000000)));
		assertEquals("0.0 + 1.0i", Complex.toString(new Complex(0, 1)));
		assertEquals("2.0 + 1.0E-8i", Complex.toString(new Complex(2, 0.00000001)));
		assertEquals("99.0 + 1000000.0i", Complex.toString(new Complex(99, 1000000)));
		assertEquals("1000000.0 + 1.0i", Complex.toString(new Complex(1000000, 1)));
	}

	@Test
	public void testToString() {
		assertEquals("0.0 + 0.0i", new Complex(0, 0).toString());
		assertEquals("0.0 - 1.0i", new Complex(0, -1).toString());
		assertEquals("0.0 - 1.0E-7i", new Complex(0, -0.0000001).toString());
		assertEquals("0.0 - 1000000.0i", new Complex(0, -1000000).toString());
		assertEquals("0.0 + 1.0i", new Complex(0, 1).toString());
		assertEquals("2.0 + 1.0E-8i", new Complex(2, 0.00000001).toString());
		assertEquals("99.0 + 1000000.0i", new Complex(99, 1000000).toString());
		assertEquals("1000000.0 + 1.0i", new Complex(1000000, 1).toString());
	}

	@Test
	public void testGetReal() {
		Complex complex;
		for(int i = 0; i < 10000; i+= 100)
		{
			double real = Math.random();
			int realNeg = real > 0.5 ? 1 : -1;
			double imaginary = Math.random();
			int imNeg = imaginary > 0.5 ? 1 : -1;
			complex = new Complex(real*i*realNeg,imaginary*i*imNeg);
			Assert.assertThat(real*i*realNeg, is(complex.getReal()));
		}
	}

	@Test
	public void testGetImaginary() {
		Complex complex;
		for(int i = 0; i < 10000; i+= 100)
		{
			double real = Math.random();
			int realNeg = real > 0.5 ? 1 : -1;
			double imaginary = Math.random();
			int imNeg = imaginary > 0.5 ? 1 : -1;
			complex = new Complex(real*i*realNeg,imaginary*i*imNeg);
			assertThat(imaginary*i*imNeg, is(complex.getImaginary()));
		}
	}

	@Test
	public void testIsZeroComplex() {
		assertFalse(Complex.isZero(new Complex(0,0.000000001)));
		assertFalse(Complex.isZero(new Complex(0.0000000000001,0)));
		assertTrue(Complex.isZero(new Complex(0,0)));
		assertFalse(Complex.isZero(new Complex(1,1)));
	}

	@Test
	public void testIsZero() {
		assertFalse(new Complex(0,0.0000001).isZero());
		assertFalse(new Complex(0.0000000000001,0).isZero());
		assertTrue(new Complex(0,0).isZero());
		assertFalse(new Complex(1,1).isZero());
	}

	@Test
	public void testMagnitude() 
	{
		Complex complex;
		for(int i = 0; i < 10000; i+= 100)
		{
			double real = Math.random();
			int realNeg = real > 0.5 ? 1 : -1;
			real = real * i * realNeg;
			double imaginary = Math.random();
			int imNeg = imaginary > 0.5 ? 1 : -1;
			imaginary = imaginary * i * imNeg;
			complex = new Complex(real,imaginary);
			
			assertThat(Math.hypot(real, imaginary), is(complex.magnitude()));
		}
	}

	@Test
	public void testPhase() {
		Complex complex;
		for(int i = 0; i < 10000; i+= 100)
		{
			double real = Math.random();
			int realNeg = real > 0.5 ? 1 : -1;
			real = real * i * realNeg;
			double imaginary = Math.random();
			int imNeg = imaginary > 0.5 ? 1 : -1;
			imaginary = imaginary * i * imNeg;
			complex = new Complex(real,imaginary);
			
			assertThat(Math.atan2(imaginary,real), is(complex.phase()));
		}
	}

	@Test
	public void testConjugate() {
		Complex complex;
		for(int i = 0; i < 10000; i+= 100)
		{
			double real = Math.random();
			int realNeg = real > 0.5 ? 1 : -1;
			real = real * i * realNeg;
			double imaginary = Math.random();
			int imNeg = imaginary > 0.5 ? 1 : -1;
			imaginary = imaginary * i * imNeg;
			complex = new Complex(real,imaginary);
			
			assertTrue(new Complex(real, -imaginary).equals(complex.conjugate()));
		}
	}

	@Test
	public void testReciprocal() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testPlus() {
		assertTrue(new Complex(0,0).plus(new Complex(0,1)).equals(new Complex(0,1)));
		//Testing positive numbers
		assertTrue(new Complex(10,10).plus(new Complex(2,7)).equals(new Complex(12,17)));
		assertTrue(new Complex(0,0).plus(new Complex(1,0)).equals(new Complex(1,0)));
		for(int i = 0; i < 10000; i++)
		{
			double real1 = Math.random() * i;
			double real2 = Math.random() * i;
			double im1 = Math.random() * i;
			double im2 = Math.random() * i;
			
			assertTrue(new Complex(real1,im1).plus(new Complex(real2,im2)).equals(new Complex(real1 + real2, im1 + im2)));
		}
		assertTrue(new Complex(-10,-10).plus(new Complex(-2,-7)).equals(new Complex(-12,-17)));
		assertTrue(new Complex(-1,0).plus(new Complex(0,-1)).equals(new Complex(-1,-1)));
		//Testing negative numbers
		for(int i = 0; i < 10000; i++)
		{
			double real1 = Math.random() * -i;
			double real2 = Math.random() * -i;
			double im1 = Math.random() * -i;
			double im2 = Math.random() * -i;
			
			assertTrue(new Complex(real1,im1).plus(new Complex(real2,im2)).equals(new Complex(real1 + real2, im1 + im2)));
		}
		//Testing both
		assertTrue(new Complex(-1,0).plus(new Complex(2,-4)).equals(new Complex(1,-4)));
		assertTrue(new Complex(14.3,-12.3).plus(new Complex(-20,2.3)).equals(new Complex(14.3 - 20,-12.3 + 2.3)));
		for(int i = 0; i < 10000; i++)
		{
			double real1 = Math.random() * i;
			double real2 = Math.random() * i * (real1 > 0.5*i ? 1 : -1);
			real1 = real1 * (Math.abs(real2) > 0.5*i ? 1 : -1);
			double im1 = Math.random() * i;
			double im2 = Math.random() * i * (im1 > 0.5*i ? 1 : -1);
			im1 = im1 * (Math.abs(im2) > 0.5*i ? 1 : -1);
			
			assertTrue(new Complex(real1,im1).plus(new Complex(real2,im2)).equals(new Complex(real1 + real2, im1 + im2)));
		}
	}

	@Test
	public void testMinus() {
		assertTrue(new Complex(0,1).minus(new Complex(0,0)).equals(new Complex(0,1)));
		//Testing positive numbers
		assertTrue(new Complex(10,10).minus(new Complex(2,7)).equals(new Complex(8,3)));
		assertTrue(new Complex(0,0).minus(new Complex(1,0)).equals(new Complex(-1,0)));
		for(int i = 0; i < 10000; i++)
		{
			double real1 = Math.random() * i;
			double real2 = Math.random() * i;
			double im1 = Math.random() * i;
			double im2 = Math.random() * i;
			
			assertTrue(new Complex(real1,im1).minus(new Complex(real2,im2)).equals(new Complex(real1 - real2, im1 - im2)));
		}
		assertTrue(new Complex(-10,-10).minus(new Complex(-2,-7)).equals(new Complex(-8,-3)));
		assertTrue(new Complex(-1,0).minus(new Complex(0,-1)).equals(new Complex(-1,1)));
		//Testing negative numbers
		for(int i = 0; i < 10000; i++)
		{
			double real1 = Math.random() * -i;
			double real2 = Math.random() * -i;
			double im1 = Math.random() * -i;
			double im2 = Math.random() * -i;
			
			assertTrue(new Complex(real1,im1).minus(new Complex(real2,im2)).equals(new Complex(real1 - real2, im1 - im2)));
		}
		//Testing both
		assertTrue(new Complex(-1,0).minus(new Complex(2,-4)).equals(new Complex(-3,4)));
		assertTrue(new Complex(14.3,-12.3).minus(new Complex(-20,2.3)).equals(new Complex(14.3 - -20,-12.3 - 2.3)));
		for(int i = 0; i < 10000; i++)
		{
			double real1 = Math.random() * i;
			double real2 = Math.random() * i * (real1 > 0.5*i ? 1 : -1);
			real1 = real1 * (Math.abs(real2) > 0.5*i ? 1 : -1);
			double im1 = Math.random() * i;
			double im2 = Math.random() * i * (im1 > 0.5*i ? 1 : -1);
			im1 = im1 * (Math.abs(im2) > 0.5*i ? 1 : -1);
			
			assertTrue(new Complex(real1,im1).minus(new Complex(real2,im2)).equals(new Complex(real1 - real2, im1 - im2)));
		}
		//Show that minus() == -plus()
		for(int i = 0; i < 10000; i++)
		{
			double real1 = Math.random() * i;
			double real2 = Math.random() * i * (real1 > 0.5*i ? 1 : -1);
			real1 = real1 * (Math.abs(real2) > 0.5*i ? 1 : -1);
			double im1 = Math.random() * i;
			double im2 = Math.random() * i * (im1 > 0.5*i ? 1 : -1);
			im1 = im1 * (Math.abs(im2) > 0.5*i ? 1 : -1);
			
			assertTrue(new Complex(real1,im1).minus(new Complex(real2,im2)).equals(
					new Complex(real1,im1).plus(new Complex(-real2, -im2))));
		}
	}

	@Test
	public void testTimesComplex() {
		fail("Not yet implemented");
	}

	@Test
	public void testTimesDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivide() {
		fail("Not yet implemented");
	}

	@Test
	public void testExpComplex() {
		fail("Not yet implemented");
	}

	@Test
	public void testExp() {
		fail("Not yet implemented");
	}

	@Test
	public void testCosComplex() {
		fail("Not yet implemented");
	}

	@Test
	public void testCos() {
		fail("Not yet implemented");
	}

	@Test
	public void testSinComplex() {
		fail("Not yet implemented");
	}

	@Test
	public void testSin() {
		fail("Not yet implemented");
	}

	@Test
	public void testTanComplex() {
		fail("Not yet implemented");
	}

	@Test
	public void testTan() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

}
