package priv.linjb.thread.callback;

import java.util.List;

public class HandleListImpl implements HandleList{

	private List list;
	
	
	public HandleListImpl(List list) {
		super();
		this.list = list;
	}


	@Override
	public void handle(String name) {
		// TODO Auto-generated method stub
		
		synchronized (list) {
			list.add(name);
		}
		
	}


	public List getList() {
		return list;
	}


	public void setList(List list) {
		this.list = list;
	}
	
	

}
