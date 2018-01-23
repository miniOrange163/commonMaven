package priv.linjb.common.parse.dom4j;

import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class RowConverter extends AbstractCollectionConverter {

	public RowConverter(Mapper mapper) {
		super(mapper);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canConvert(Class arg0) {
		// TODO Auto-generated method stub
		return Row.class.equals(arg0);
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter writer,
			MarshallingContext arg2) {
		// TODO Auto-generated method stub
		Row map = (Row) arg0;
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            writer.addAttribute(entry.getKey().toString(), entry.getValue().toString());
        }
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		// TODO Auto-generated method stub
		 Row map = new Row();
        populateMap(reader, context, map);
        return map;
	}

	 protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Row map) {
	        Iterator<String> iterator = reader.getAttributeNames();
	        while (iterator.hasNext()) {
	            Object key = iterator.next();
	            String value = reader.getAttribute((String) key);
	            map.put(key.toString(), value.toString());
	        }
	    }
}
