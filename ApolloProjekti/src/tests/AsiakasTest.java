package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.Assert;

import simu.model.Asiakas;

class AsiakasTest {
	@Test
    public void testSettersAndGetters() {
        try {
            Asiakas asiakas = new Asiakas();

            double saapumisaika = 5.0;
            double poistumisaika = 10.0;
            double palvelunAloitus = 6.0;
            double palvelunLopetus = 9.0;
            double jonottamisenAloitus = 5.5;

            asiakas.setSaapumisaika(saapumisaika);
            Assert.assertEquals(saapumisaika, asiakas.getSaapumisaika(), 0.0);

            asiakas.setPoistumisaika(poistumisaika);
            Assert.assertEquals(poistumisaika, asiakas.getPoistumisaika(), 0.0);

            asiakas.setPalvelunAloitus(palvelunAloitus);
            Assert.assertEquals(palvelunAloitus, asiakas.getPalvelunAloitus(), 0.0);

            asiakas.setPalvelunLopetus(palvelunLopetus);
            Assert.assertEquals(palvelunLopetus, asiakas.getPalvelunLopetus(), 0.0);

            asiakas.setJonottamisenAloitus(jonottamisenAloitus);
            Assert.assertEquals(jonottamisenAloitus, asiakas.getJonottamisenAloitus(), 0.0);
        } catch (Exception e) {
            fail("Virhe testatessa testSettersAndGetters(): " + e.getMessage());
        }
    }

    @Test
    public void testJonossaVietettyAika() {
        try {
            simu.framework.Trace.setTraceLevel(simu.framework.Trace.Level.INFO);
            Asiakas asiakas = new Asiakas();
            asiakas.setPalvelunLopetus(10.0);
            asiakas.setJonottamisenAloitus(5.0);
            Assert.assertEquals(5.0, asiakas.jonossaVietettyAika(), 0.0);
            System.out.println("Jonossa vietetty aika: " + asiakas.jonossaVietettyAika());
        } catch (Exception e) {
            fail("Virhe testatessa testJonossaVietettyAika(): " + e.getMessage());
        }
    }

    @Test
    public void testPalvelupisteessaVietettyAika() {
        try {
            Asiakas asiakas = new Asiakas();
            asiakas.setPalvelunAloitus(5.0);
            asiakas.setPalvelunLopetus(10.0);
            Assert.assertEquals(5.0, asiakas.palvelupisteessaVietettyAika(), 0.0);
            System.out.println("Palvelupisteessä vietetty aika: " + asiakas.palvelupisteessaVietettyAika());
        } catch (Exception e) {
            fail("Virhe testatessa testPalvelupisteessaVietettyAika(): " + e.getMessage());
        }
    }

    @Test
    public void testGetId() {
        try {
            Asiakas asiakas1 = new Asiakas();
            Asiakas asiakas2 = new Asiakas();
            assertNotEquals(asiakas1.getId(), asiakas2.getId());
            System.out.println("Asiakkaan id: " + asiakas1.getId()+ " Toisen asiakkaan id: " + asiakas2.getId());
            } catch (Exception e) {
            fail("Virhe testatessa testGetId(): " + e.getMessage());
            }
            }
    
    @Test
    public void testGetKokonaismaara() {
        try {
            int alussa = Asiakas.getKokonaismaara();
            Asiakas asiakas1 = new Asiakas();
            Asiakas asiakas2 = new Asiakas();
            int lopussa = Asiakas.getKokonaismaara();
            assertEquals(alussa + 2, lopussa);
            System.out.println("Asiakkaita yhteensä tähän mennessä: " + Asiakas.getKokonaismaara());
        } catch (Exception e) {
            fail("Virhe testatessa testGetKokonaismaara(): " + e.getMessage());
        }
    }

    @Test
    public void testRaportti() {
        try {
            Asiakas asiakas = new Asiakas();
            asiakas.setSaapumisaika(5.0);
            asiakas.setPoistumisaika(10.0);
            asiakas.raportti();
            assertEquals(1, Asiakas.getAsiakasLkm());
            assertEquals(5.0, Asiakas.getKeskiarvo(), 0.001);
        } catch (Exception e) {
            fail("Virhe testatessa testRaportti(): " + e.getMessage());
        }
    }
}


