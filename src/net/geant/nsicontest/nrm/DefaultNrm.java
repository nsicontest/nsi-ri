/**
 * 
 */
package net.geant.nsicontest.nrm;

import java.util.Calendar;

/**
 * Default NRM implementation that does not do any checking.
 */
public class DefaultNrm implements Nrm {
	
	private NrmParameters parameters = new NrmParameters();
	
	private void waiting(int seconds) {
		
		if (seconds > 0) {			
			try {
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException e) { }
		}
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#getTopology()
	 */
	@Override
	public Topology getTopology() {

		return new Topology("fake.domain", Calendar.getInstance(), "localhost", "admin contact", "fake.topology", 0.0f, 0.0f);
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#configure(net.geant.nsicontest.nrm.NrmParameters)
	 */
	@Override
	public void configure(NrmParameters parameters) throws NrmException {

		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#reserve(java.lang.String, net.geant.nsicontest.nrm.ReserveParameters)
	 */
	@Override
	public ReserveOutcome reserve(String connectionId, ReserveParameters params) throws NrmException {
		
		waiting(parameters.getReserveDelay());
		if (parameters.isReserveFail())
			throw new NrmException("nrm reserve set to fail");		
		
		return new ReserveOutcome(params.getSourceVlans().min(), params.getDestVlans().min());
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#modify(java.lang.String, net.geant.nsicontest.nrm.ReserveParameters)
	 */
	@Override
	public ReserveOutcome modify(String connectionId, ReserveParameters params)	throws NrmException {

		waiting(parameters.getModifyDelay());
		if (parameters.isModifyFail())
			throw new NrmException("nrm modify set to fail");
		
		return new ReserveOutcome(params.getSourceVlans().min(), params.getDestVlans().min());
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#commit(java.lang.String)
	 */
	@Override
	public void commit(String connectionId) throws NrmException {
		
		waiting(parameters.getCommitDelay());
		if (parameters.isCommitFail())
			throw new NrmException("nrm commit set to fail");
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#abort(java.lang.String)
	 */
	@Override
	public void abort(String connectionId) {

	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#activate(java.lang.String)
	 */
	@Override
	public void activate(String connectionId) throws NrmException {

		waiting(parameters.getActivateDelay());
		if (parameters.isActivateFail())
			throw new NrmException("nrm activate set to fail");
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#deactivate(java.lang.String)
	 */
	@Override
	public void deactivate(String connectionId) throws NrmException {
		
		waiting(parameters.getDeactivateDelay());
		if (parameters.isDeactivateFail())
			throw new NrmException("nrm deactivate set to fail");
	}

	/* (non-Javadoc)
	 * @see net.geant.nsicontest.nrm.Nrm#terminate(java.lang.String)
	 */
	@Override
	public void terminate(String connectionId) {

	}
}
