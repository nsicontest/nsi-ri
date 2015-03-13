/**
 * 
 */
package net.geant.nsicontest.topology.path;

import java.util.List;

import net.geant.nsicontest.core.Range;
import net.geant.nsicontest.topology.BidirectionalPort;

/**
 * Represents result of path computation
 */
public class PathResult {
	
	private PathType pathType;
	private BidirectionalPort source, dest;
	private Range sourceVlans, destVlans;
	private List<List<BidirectionalPort>> paths;
	private String errorMessage; 
	
	public PathResult() { }
	
	public PathResult(String errorMessage) {
		
		this.pathType = PathType.INVALID_PATH;
		this.errorMessage = errorMessage;		
	}
	
	public PathResult(PathType pathType, BidirectionalPort source, Range sourceVlans, BidirectionalPort dest, Range destVlans) {
		
		this(pathType, source, sourceVlans, dest, destVlans, null);
	}
	
	public PathResult(PathType pathType, BidirectionalPort source, Range sourceVlans, BidirectionalPort dest, Range destVlans, 
			List<List<BidirectionalPort>> paths) {
		
		this.pathType = pathType;
		this.source = source;
		this.sourceVlans = sourceVlans;
		this.dest = dest;
		this.destVlans = destVlans;
		this.paths = paths;
	}
	
	public boolean isLocal() {
		
		return pathType == PathType.LOCAL_DOMAIN;
	}

	public boolean isInvalid() {
		
		return pathType == PathType.INVALID_PATH;
	}		
	
	public PathType getPathType() {
		return pathType;
	}
	
	public void setPathType(PathType pathType) {
		this.pathType = pathType;
	}
	
	public BidirectionalPort getSource() {
		return source;
	}
	
	public void setSource(BidirectionalPort source) {
		this.source = source;
	}
	
	public BidirectionalPort getDest() {
		return dest;
	}
	
	public void setDest(BidirectionalPort dest) {
		this.dest = dest;
	}
	
	public Range getSourceVlans() {
		return sourceVlans;
	}

	public void setSourceVlans(Range sourceVlans) {
		this.sourceVlans = sourceVlans;
	}

	public Range getDestVlans() {
		return destVlans;
	}

	public void setDestVlans(Range destVlans) {
		this.destVlans = destVlans;
	}

	public List<List<BidirectionalPort>> getPaths() {
		return paths;
	}

	public void setPaths(List<List<BidirectionalPort>> paths) {
		this.paths = paths;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
