package testcode;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAddress {

	int a,b;
	Address Address1, Address2, Address3, Address4, Address5;
	
	@Before
	public void setUp() throws Exception {
		a = 1;
		b = 2;
		Address1 = new Address("Empire Grove", "Greely", "k4p");
		Address2 = new Address("Empire Grove", "Greely", "k4p");
		Address3 = new Address("Spartan Grove", "Greely", "k4p");
		Address4 = new Address(a,"Empire Grove", "Metcalfe", "k4p");
		Address5 = new Address(b,"Empire Grove", "Metcalfe", "k9p");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAddressStringStringString() {
		
		assertEquals(Address1, Address2);
		assertNotEquals(Address1, Address3);
		assertNotEquals(Address3, Address2);
	}

	@Test
	public void testAddressIntStringStringString() {
		
		assertNotEquals(Address4, Address5); // TODO
	}

	@Test
	public void testToString() {
		assertEquals("Address: Empire Grove, Greely. k4p",Address1.toString());
		assertNotEquals("Address: 1 Empire Grove, Greely. k4p",Address4.toString());// TODO
	}

	@Test
	public void testEqualsObject() {
		assertTrue(Address1.equals(Address2));// TODO
		assertFalse(Address4.equals(Address5));
	}

}
