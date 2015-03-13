package net.geant.nsicontest.reservation;

import net.geant.nsicontest.topology.DecomposedStp;

import org.junit.Assert;
import org.junit.Test;

public class DecomposedStpTest {

	@Test
	public void testParseStp() throws Exception {
		
		String exampleStp = "urn:ogf:network:example.net:2013:bi-ps";	
		String networkId = "urn:ogf:network:example.net:2013";
		String localId = "bi-ps";
		String label = "4-4096";
				
		DecomposedStp stp = DecomposedStp.parse(exampleStp);
			
		Assert.assertTrue(networkId.equals(stp.getNetworkId()));
		Assert.assertTrue(localId.equals(stp.getLocalId()));
		Assert.assertTrue(label.equals(stp.getLabel()));
	}
	
	@Test
	public void testParseStpWithLabel() throws Exception {
		
		String exampleStp = "urn:ogf:network:example.net:2013:bi-ps?vlan=1780-1800";
		String networkId = "urn:ogf:network:example.net:2013";
		String localId = "bi-ps";
		String label = "1780-1800";
				
		DecomposedStp stp = DecomposedStp.parse(exampleStp);
			
		Assert.assertTrue(networkId.equals(stp.getNetworkId()));
		Assert.assertTrue(localId.equals(stp.getLocalId()));
		Assert.assertTrue(label.equals(stp.getLabel()));
	}

}
