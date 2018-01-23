package priv.linjb.common.parse.dom4j;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamConverter(RowConverter.class)
public class Row extends HashMap<String, String> {
    private static final long serialVersionUID = 5619951409573339302L;
}
