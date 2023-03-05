package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eduni.distributions.Normal;
import simu.framework.Tapahtumalista;
import simu.model.Asiakas;
import simu.model.Palvelupiste;
import simu.model.TapahtumanTyyppi;

class PalvelupisteTest {

	private Palvelupiste palvelupiste;
	private Normal generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;
	private Asiakas asiakas1;
	private Asiakas asiakas2;
	
	@BeforeEach
	public void setUp() {
		generator = new Normal(2, 1);
		tapahtumalista = new Tapahtumalista();
		tyyppi = TapahtumanTyyppi.DEP3;
		palvelupiste = new Palvelupiste(generator, tapahtumalista, tyyppi);
		simu.framework.Trace.setTraceLevel(simu.framework.Trace.Level.INFO);
		asiakas1 = new Asiakas();
		asiakas2 = new Asiakas();
	}
	
    @Test
    public void testLisaaJonoon() {
        try {
            palvelupiste.lisaaJonoon(asiakas1);
            assertTrue(palvelupiste.onJonossa());
            System.out.println("Asiakas lisätty jonoon");
        } catch (Exception e) {
            fail("Virhe testatessa testLisaaJonoon(): " + e.getMessage());
        }
    }

    @Test
    public void testOtaJonosta() {
        try {
            palvelupiste.lisaaJonoon(asiakas1);
            Asiakas palvellut = palvelupiste.otaJonosta();
            assertEquals(asiakas1, palvellut);
            System.out.println("Palvellut asiakkaat tähän mennessä: " + palvellut);
        } catch (Exception e) {
            fail("Virhe testatessa testOtaJonosta(): " + e.getMessage());
        }
    }

    @Test
    public void testAloitaPalvelu() {
        try {
            palvelupiste.lisaaJonoon(asiakas1);
            palvelupiste.aloitaPalvelu();
            assertTrue(palvelupiste.onVarattu());
            System.out.println("Palvelu on aloitettu, joten palvelupiste on varattu.");
        } catch (Exception e) {
            fail("Virhe testatessa testAloitaPalvelu(): " + e.getMessage());
        }
    }
	
    @Test
    public void testOnVarattu() {
        try {
            palvelupiste.lisaaJonoon(asiakas1);
            palvelupiste.aloitaPalvelu();
            assertTrue(palvelupiste.onVarattu());
        } catch (Exception e) {
            fail("Virhe testatessa testOnVarattu(): " + e.getMessage());
        }
    }
	
    @Test
    public void testOnJonossa() {
    try {
    assertFalse(palvelupiste.onJonossa());
    palvelupiste.lisaaJonoon(asiakas1);
    assertTrue(palvelupiste.onJonossa());
    } catch (Exception e) {
    fail("Virhe testatessa testOnJonossa(): " + e.getMessage());
    }
    }

    @Test
    public void testGetJononPituus() {
    try {
    assertEquals(0, palvelupiste.getJononPituus());
    System.out.println("Jonossa on nyt: " + palvelupiste.getJononPituus() + " hlöä.");
    palvelupiste.lisaaJonoon(asiakas1);
    palvelupiste.lisaaJonoon(asiakas2);
    assertEquals(2, palvelupiste.getJononPituus());
    System.out.println("Jonossa on nyt: " + palvelupiste.getJononPituus() + " hlöä.");
    } catch (Exception e) {
    fail("Virhe testatessa testOnJonossa(): " + e.getMessage());
    }
    }

    @Test
    public void testGetServiceTime() {
    // Create a Palvelupiste with a Normal distribution
    Palvelupiste palvelupiste = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3);
    palvelupiste.lisaaJonoon(asiakas1);
    palvelupiste.aloitaPalvelu();
    palvelupiste.otaJonosta();
 // Wait for a specific amount of time according to the distribution
    double expectedServiceTime = palvelupiste.getServiceTime();
    try {
    	Thread.sleep((long) (expectedServiceTime * 1000));
    	assertEquals(expectedServiceTime, palvelupiste.getServiceTime(), 0.0);
    	System.out.println("Palveluaika: " + palvelupiste.getServiceTime());
    } catch (Exception e) {
    	fail("Virhe testatessa testGetServiceTime(): " + e.getMessage());
    }
    }
	
	@Test
	public void testGetResponseTime() {
		Palvelupiste palvelupiste = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3);
	    assertEquals(0, palvelupiste.getResponseTime(), 0.0);
	    palvelupiste.lisaaJonoon(asiakas1);
	    palvelupiste.lisaaJonoon(asiakas2);
	    palvelupiste.aloitaPalvelu();
	    palvelupiste.otaJonosta();
	    palvelupiste.otaJonosta();
	    double expectedResponseTime = palvelupiste.getServiceTime() + palvelupiste.getJononPituus();
	    assertEquals(expectedResponseTime, palvelupiste.getResponseTime(), 0.0);
	    System.out.println("Response time: " + palvelupiste.getResponseTime());

	    palvelupiste.lisaaJonoon(asiakas1);
	    palvelupiste.aloitaPalvelu();
	    palvelupiste.otaJonosta();
	    expectedResponseTime = palvelupiste.getServiceTime() + palvelupiste.getJononPituus();
	    assertEquals(expectedResponseTime, palvelupiste.getResponseTime(), 0.0);
	    System.out.println("Response time: " + palvelupiste.getResponseTime());
	}

	@Test
	public void testGetWaitingTime() {
	    Palvelupiste palvelupiste = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3);
	    palvelupiste.lisaaJonoon(asiakas1);
	    double expectedWaitingTime1 = palvelupiste.getWaitingTime();
	    try {
	        Thread.sleep((long) (expectedWaitingTime1 * 1000));
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    palvelupiste.lisaaJonoon(asiakas2);
	    double expectedWaitingTime2 = palvelupiste.getWaitingTime();
	    try {
	        Thread.sleep((long) (expectedWaitingTime2 * 1000));
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    assertEquals(expectedWaitingTime1, palvelupiste.getWaitingTime(), 0.0);
	    System.out.println("Odotusaika: " + palvelupiste.getWaitingTime());
	    assertEquals(expectedWaitingTime2, palvelupiste.getWaitingTime(), 0.0);
	    System.out.println("Odotusaika: " + palvelupiste.getWaitingTime());
	}
}
