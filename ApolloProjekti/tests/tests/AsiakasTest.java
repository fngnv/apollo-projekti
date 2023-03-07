package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.Assert;

import simu.model.Asiakas;

class AsiakasTest {
	private final double DELTA = 0.001;
	
	@Test
	@DisplayName("Toimiiko getterit ja setterit oikein")
    public void testSettersAndGetters() {
        try {
            Asiakas asiakas = new Asiakas();

            double saapumisaika = 5.0;
            double poistumisaika = 10.0;
            double palvelunAloitus = 6.0;
            double palvelunLopetus = 9.0;
            double jonottamisenAloitus = 5.5;

            asiakas.setSaapumisaika(saapumisaika);
            assertEquals(saapumisaika, asiakas.getSaapumisaika(), DELTA, "getSaapumisaika() väärin");

            asiakas.setPoistumisaika(poistumisaika);
            assertEquals(poistumisaika, asiakas.getPoistumisaika(), DELTA, "getPoistumisaika() väärin");

            asiakas.setPalvelunAloitus(palvelunAloitus);
            assertEquals(palvelunAloitus, asiakas.getPalvelunAloitus(), DELTA, "getPalvelunAloitus() väärin");

            asiakas.setPalvelunLopetus(palvelunLopetus);
            assertEquals(palvelunLopetus, asiakas.getPalvelunLopetus(), DELTA, "getPalvelunLopetus() väärin");

            asiakas.setJonottamisenAloitus(jonottamisenAloitus);
            assertEquals(jonottamisenAloitus, asiakas.getJonottamisenAloitus(), DELTA, "getJonottamisenAloitus() väärin");
        } catch (Exception e) {
            fail("Virhe testatessa testSettersAndGetters(): " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Onko jonossa vietetty aika laskettu oikein")
    public void testJonossaVietettyAika() {
        try {
            simu.framework.Trace.setTraceLevel(simu.framework.Trace.Level.INFO);
            Asiakas asiakas = new Asiakas();
            asiakas.setPalvelunLopetus(10.0);
            asiakas.setJonottamisenAloitus(5.0);
            assertEquals(5.0, asiakas.jonossaVietettyAika(), DELTA, "Jonossa vietetty aika laskettu väärin");
        } catch (Exception e) {
            fail("Virhe testatessa testJonossaVietettyAika(): " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Onko palvelupisteessä vietetty aika laskettu oikein")
    public void testPalvelupisteessaVietettyAika() {
        try {
            Asiakas asiakas = new Asiakas();
            asiakas.setPalvelunAloitus(5.0);
            asiakas.setPalvelunLopetus(10.0);
            assertEquals(5.0, asiakas.palvelupisteessaVietettyAika(), DELTA, "Palvelupisteessä vietetty aika laskettu väärin");
        } catch (Exception e) {
            fail("Virhe testatessa testPalvelupisteessaVietettyAika(): " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Ovatko asiakkaiden Id:t oikeassa järjestyksessä")
    public void testGetId() {
        try {
            Asiakas asiakas1 = new Asiakas();
            Asiakas asiakas2 = new Asiakas();
            assertNotEquals(asiakas1.getId(), asiakas2.getId(), "Kahdella eri asiakkaalla samat Id:t");
            assertEquals(1, asiakas2.getId() - asiakas1.getId(), "Asiakkaiden Id:t ovat väärässä järjestyksessä");
            } catch (Exception e) {
            fail("Virhe testatessa testGetId(): " + e.getMessage());
            }
            }
    
    @Test
    @DisplayName("Onko asiakkaiden kokonaismäärä laskettu oikein")
    public void testGetKokonaismaara() {
        try {
            int alussa = Asiakas.getKokonaismaara();
            Asiakas asiakas1 = new Asiakas();
            Asiakas asiakas2 = new Asiakas();
            int lopussa = Asiakas.getKokonaismaara();
            assertEquals(alussa + 2, lopussa, "Asiakasmäärä laskettu väärin");
        } catch (Exception e) {
            fail("Virhe testatessa testGetKokonaismaara(): " + e.getMessage());
        }
    }
}


