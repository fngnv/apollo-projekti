package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
	
	private final double DELTA = 0.001;
	
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
    @DisplayName("Lasketaanko jononpituus oikein")
    public void testGetJononPituus() {
	    try {
		    palvelupiste.lisaaJonoon(asiakas1);
		    palvelupiste.lisaaJonoon(asiakas2);
		    assertEquals(2, palvelupiste.getJononPituus(), "Jononpituus laskettu väärin");
	    } catch (Exception e) {
	    	fail("Virhe testatessa testOnJonossa(): " + e.getMessage());
	    }
    }

    @Test
    @DisplayName("Lasketaanko service time oikein")
    public void testGetServiceTime() {
    	// Create a Palvelupiste with a Normal distribution
	    Palvelupiste palvelupiste = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3);
	    palvelupiste.lisaaJonoon(asiakas1);
	    palvelupiste.aloitaPalvelu();
	    palvelupiste.otaJonosta();
	    double busyT = asiakas1.getPalvelunLopetus() - asiakas1.getPalvelunAloitus();
	    double expectedServiceTime = busyT / 1;
	    try {
	    	assertEquals(expectedServiceTime, palvelupiste.getServiceTime(), DELTA, "Service time laskettu väärin");
	    	System.out.println("Palveluaika: " + palvelupiste.getServiceTime());
	    } catch (Exception e) {
	    	fail("Virhe testatessa testGetServiceTime(): " + e.getMessage());
	    }
    }
	
	@Test
	@DisplayName("Lasketaanko response time oikein")
	public void testGetResponseTime() {
		Palvelupiste palvelupiste = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3);
	    palvelupiste.lisaaJonoon(asiakas1);
	    palvelupiste.aloitaPalvelu();
	    palvelupiste.otaJonosta();
	    double wTime = asiakas1.getPalvelunLopetus() - asiakas1.getJonottamisenAloitus();
	    double expectedResponseTime = wTime / 1;
	    assertEquals(expectedResponseTime, palvelupiste.getResponseTime(), DELTA, "Response time laskettu väärin");
	}

	@Test
	@DisplayName("Lasketaanko waiting time oikein")
	public void testGetWaitingTime() {
	    Palvelupiste palvelupiste = new Palvelupiste(new Normal(60, 600), tapahtumalista, TapahtumanTyyppi.DEP3);
	    palvelupiste.lisaaJonoon(asiakas1);
	    palvelupiste.aloitaPalvelu();
	    palvelupiste.otaJonosta();
	    double expectedWaitingTime1 = asiakas1.getPalvelunLopetus() - asiakas1.getJonottamisenAloitus();
		/*
		 * try { Thread.sleep((long) (expectedWaitingTime1 * 1000)); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 * palvelupiste.lisaaJonoon(asiakas2); double expectedWaitingTime2 =
		 * palvelupiste.getWaitingTime(); try { Thread.sleep((long)
		 * (expectedWaitingTime2 * 1000)); } catch (InterruptedException e) {
		 * e.printStackTrace(); }
		 */
	    assertEquals(expectedWaitingTime1, palvelupiste.getWaitingTime(), DELTA, "Waiting time laskettu väärin");
		/*
		 * assertEquals(expectedWaitingTime2, palvelupiste.getWaitingTime(), 0.0);
		 * System.out.println("Odotusaika: " + palvelupiste.getWaitingTime());
		 */
	}
}
