package priv.linjb.common.parse.dom4j;


import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("results")
public class Results {
	@XStreamAlias("name")
    @XStreamAsAttribute
    private String name;

    @XStreamImplicit(itemFieldName = "row")
    private List<Row> rows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}


}
