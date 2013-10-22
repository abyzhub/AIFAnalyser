package com.crmimpl.tool;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private ResourceDesc rd = null;

	private ClassDetails[] elementArray = null;
	
	

	

	@Override
	protected void executeImpl(BoContext arg0) {

	}

	@Override
	protected void preExecute(BoContext arg0) {
		try {
			if (item.contains("Type -> ")) {
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
					if (methodName.startsWith("get")) {
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
			} else if (item.contains("ResourceName")) {

				if (getBindingType() != -1) {
					String str = item.replace("ResourceName -> ", "");
					rd = ConsumerUtil.getResourceDesc(str,
							BindingType.valueOf(getBindingType()));

				}

			}
		} catch (Exception e) {
			throw new UifDisplayableException("Error: "
					+ e.getLocalizedMessage());
		}

	}

	private int getBindingType() {
		for (int i = 0; i < vm.length; i++) {
			String str = item.replace("ResourceName -> ", "");
			if (vm[i].getParentFldVal().equals(str))
			{
				return Integer.parseInt(vm[i].getChildFldVal());
			}
		}
		return -1;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setVm(ValueMapVo[] vmap) {
		this.vm = vmap;
	}

	public ResourceDesc getRd() {
		return rd;
	}
	public ClassDetails[] getElementArray() {
		return elementArray;
	}
}
