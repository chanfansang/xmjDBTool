package com.xmj.tool.db.data;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Synthesizer;

public class XVO {

	private Object xobject;
	private List<Object> xobjectList = new ArrayList<Object>();

	public List<Object> getXobjectList() {
		return xobjectList;
	}

	public void setXobjectList(List<Object> xobjectList) {
		this.xobjectList = xobjectList;
	}

	public Object getXobject() {
		return xobject;
	}

	public void setValue(Object xobject) {
		this.xobject = xobject;
		xobjectList.add(xobject);
	}
	
	public void setValue(Object ...xobjects) {
		for(int i=0;i<xobjects.length;i++){
			if(xobjects[i] == null){
				xobjectList.add(null);
			}else{
			//System.out.println(xobjects[i]);
				xobjectList.add(xobjects[i]);
			}
		}
		
	}
	
	
	
}
