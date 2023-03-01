package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.Assert;

import simu.model.Asiakas;

class AsiakasTest {

	  @Test
	  public void testSettersAndGetters() {
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
	  }
	  
	  @Test
	  public void testJonossaVietettyAika() {
		simu.framework.Trace.setTraceLevel(simu.framework.Trace.Level.INFO);
	    Asiakas asiakas = new Asiakas();
	    asiakas.setPalvelunLopetus(10.0);
	    asiakas.setJonottamisenAloitus(5.0);
	    Assert.assertEquals(5.0, asiakas.jonossaVietettyAika(), 0.0);
	    System.out.println("Jonossa vietetty aika: " + asiakas.jonossaVietettyAika());
	  }
	  
	  @Test
	  public void testPalvelupisteessaVietettyAika() {
	    Asiakas asiakas = new Asiakas();
	    asiakas.setPalvelunAloitus(5.0);
	    asiakas.setPalvelunLopetus(10.0);
	    Assert.assertEquals(5.0, asiakas.palvelupisteessaVietettyAika(), 0.0);
	    System.out.println("Palvelupisteessä vietetty aika: " + asiakas.palvelupisteessaVietettyAika());
	  }
	  
	  @Test
	  public void testGetId() {
	    Asiakas asiakas1 = new Asiakas();
	    Asiakas asiakas2 = new Asiakas();
	    Assert.assertNotEquals(asiakas1.getId(), asiakas2.getId());
	    System.out.println("Asiakkaan id: " + asiakas1.getId()+ " Toisen asikkaan id: " + asiakas2.getId());
	  }
	  
	  @Test
	  public void testGetKokonaismaara() {
	    int alussa = Asiakas.getKokonaismaara();
	    Asiakas asiakas1 = new Asiakas();
	    Asiakas asiakas2 = new Asiakas();
	    int lopussa = Asiakas.getKokonaismaara();
	    Assert.assertEquals(alussa + 2, lopussa);
	    System.out.println("Asiakkaita yht. tähän mennessä: " + Asiakas.getKokonaismaara());
	  }
	  
	  @Test
	  public void testRaportti() {
	    Asiakas asiakas = new Asiakas();
	    asiakas.setSaapumisaika(5.0);
	    asiakas.setPoistumisaika(10.0);
	    asiakas.raportti();
	    assertEquals(1, Asiakas.getAsiakasLkm());
	    assertEquals(5.0, Asiakas.getKeskiarvo(), 0.001);
	    }
}
