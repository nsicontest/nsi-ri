package net.geant.nsicontest.topology;

import org.junit.Assert;
import org.junit.Test;

public class TopologyTest {
	
	final String arubaXml = "docs\\aruba.xml";
	final String arubaNsaId = "urn:ogf:network:aruba.example:2013:nsa";
	
	
	@Test
	public void createTest() {
	
		/*
		GlobalTopology.getInstance().create(arubaXml);
		Assert.assertTrue(GlobalTopology.getInstance().getAll().length == 1);
		Assert.assertTrue(GlobalTopology.getInstance().get(arubaNsaId) != null);
		*/
	}
	
	@Test
	public void deleteTest() {
		
		/*
		GlobalTopology.getInstance().create(arubaXml);
		Assert.assertTrue(GlobalTopology.getInstance().getAll().length == 1);
		GlobalTopology.getInstance().delete(arubaNsaId);
		Assert.assertTrue(GlobalTopology.getInstance().getAll().length == 0);
		Assert.assertTrue(GlobalTopology.getInstance().get(arubaNsaId) == null);
		*/
	}
}
