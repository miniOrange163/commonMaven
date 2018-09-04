package priv.linjb.common.parse.dom4j;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("results")
public class PicInfo {

	@XStreamAlias("JGSJ")
	private String JGSJ;
	@XStreamAlias("ABCD")
	private String abcd;

	public String getAbcd() {
		return abcd;
	}

	public void setAbcd(String abcd) {
		this.abcd = abcd;
	}

	public String getJGSJ() {
		return JGSJ;
	}

	public void setJGSJ(String jGSJ) {
		JGSJ = jGSJ;
	}
	
	
}
