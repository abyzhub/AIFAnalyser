package com.crmimpl.tool;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.amdocs.aif.consumer.ConsumerUtil;
import com.amdocs.aif.consumer.ResourceDesc;
import com.amdocs.aif.datamodel.BindingType;
import com.amdocs.uif.common.UifDisplayableException;
import com.clarify.cbo.BoContext;
import com.clarify.cbo.XBeanImpl;
import com.clarify.xvo.ValueMapVo;
import com.crmimpl.aiftool.ClassDetails;

public class XAifAnalyzeItemXB extends XBeanImpl {
	String item = null;

	private ValueMapVo[] vm= null;


	private ClassDetails[] elementArray = null;
	
	

	

	@Override
	protected void executeImpl(BoContext arg0) {

	}

	@Override
	protected void preExecute(BoContext arg0) {
		try {
			if (item.contains("Type -> ")) {
				analyzeDatatype();
			} else if (item.contains("Binding -> ")) {
				if (getBindingType() != -1) {
					BindingType bindingType =BindingType.valueOf(getBindingType()); 
					ResourceDesc rd = ConsumerUtil.getResourceDesc(getResourceName(), bindingType);
					if (bindingType == BindingType.EJB) {
						String configJndi = rd.getResourceNode().getString(
								"JndiHome", "");
						elementArray = new ClassDetails[5];
						elementArray[0]=new ClassDetails();
						elementArray[0].setParamName("JNDI");
						elementArray[0].setParamType(configJndi);
						elementArray[1]=new ClassDetails();
						elementArray[1].setParamName("Method");
						elementArray[1].setParamType("");
						elementArray[2]=new ClassDetails();
						elementArray[2].setParamName("Remote Class");
						elementArray[2].setParamType("");
						elementArray[3]=new ClassDetails();
						elementArray[3].setParamName("Home Class");
						elementArray[3].setParamType("");
						elementArray[4]=new ClassDetails();
						elementArray[4].setParamName("Local ");
						elementArray[4].setParamType("");
					}
				}
			}
		} catch (Exception e) {
			throw new UifDisplayableException("Error: "
					+ e.getLocalizedMessage());
		}

	}

	private void analyzeDatatype() throws ClassNotFoundException {
		ArrayList<ClassDetails> arl = new ArrayList<ClassDetails>();
		String clazzName = item.replace("Type -> ", "");
		if(clazzName.endsWith("[]")){
			clazzName = clazzName.substring(0,clazzName.length()-2);
		}
		
		Method[] methods = Class.forName(clazzName).getDeclaredMethods();
		ClassDetails clsdtl = null;
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			String className = methods[i].getReturnType().getName();
			if (methodName.startsWith("get") && methods[i].getParameterTypes().length==0) {
				if(methods[i].getReturnType().isArray())
					className = className.substring(2, className.length()-1) + "[]";
				clsdtl = new  ClassDetails();
				clsdtl.setParamName(methodName.substring(3));
				clsdtl.setParamType(className);
				arl.add(clsdtl);
			}
			if (methods[i].getName().startsWith("is")) {
				clsdtl = new  ClassDetails();
				clsdtl.setParamName(methodName.substring(2));
				clsdtl.setParamType(className);
				arl.add(clsdtl);
			}
		}
		elementArray =(ClassDetails[])arl.toArray( new ClassDetails[arl.size()]);
	}

	private int getBindingType() {
		for (int i = 0; i < vm.length; i++) {
			if (vm[i].getParentFldVal().equals("Binding"))
			{
				return Integer.parseInt(vm[i].getChildFldVal());
			}
		}
		return -1;
	}
	private String getResourceName() {
		for (int i = 0; i < vm.length; i++) {
			if (vm[i].getParentFldVal().equals("Resource"))
			{
				return vm[i].getChildFldVal();
			}
		}
		return "";
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setVm(ValueMapVo[] vmap) {
		this.vm = vmap;
	}

	public ClassDetails[] getElementArray() {
		return elementArray;
	}
}
