package priv.linjb.common.parse.dom4j;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("results")
public class PicInfo {

	@XStreamAlias("JGSJ")
	private String JGSJ;

	public String getJGSJ() {
		return JGSJ;
	}

	public void setJGSJ(String jGSJ) {
		JGSJ = jGSJ;
	}
	
	
}
