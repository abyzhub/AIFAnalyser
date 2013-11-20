package com.amdocs.bss.tool.AifAnalyser;

import java.lang.reflect.Method;
import java.util.ArrayList;

import amdocs.core.CoreSession;

import com.amdocs.aif.consumer.ConsumerUtil;
import com.amdocs.aif.consumer.CustomAdapter;
import com.amdocs.aif.consumer.OperationInputs;
import com.amdocs.aif.consumer.OperationResults;
import com.amdocs.aif.consumer.ResourceDesc;
import com.amdocs.aif.datamodel.BindingType;
import com.amdocs.uif.common.UifDisplayableException;

public class XAnalyseItem extends CustomAdapter {

	private String item = null;
	private OperationDetails[] valueMap = null;
	private ClassDetails[] elementArray = null;

	@Override
	public void execute(CoreSession arg0, OperationInputs input,
			OperationResults output) throws Exception {
		item = ((AifAnalyserAnalyseItemInputs) input).getItem();
		valueMap = ((AifAnalyserAnalyseItemInputs) input).getValueMap();
		try {
			if (item.contains("Type -> ")) {
				analyzeDatatype();
			} else if (item.contains("Binding -> ")) {
				if (getBindingType() != -1) {
					BindingType bindingType = BindingType
							.valueOf(getBindingType());
					ResourceDesc rd = ConsumerUtil.getResourceDesc(
							getAifResourceName(), bindingType);
					if (bindingType == BindingType.EJB) {
						String configJndi = rd.getResourceNode().getString(
								"JndiHome", "");
						elementArray = new ClassDetails[6];
						elementArray[0] = new ClassDetails();
						elementArray[0].setParamName("JNDI");
						elementArray[0].setParamType(configJndi);
						elementArray[1] = new ClassDetails();
						elementArray[1].setParamName("Method");
						elementArray[1].setParamType("");
						elementArray[2] = new ClassDetails();
						elementArray[2].setParamName("Remote Class");
						elementArray[2].setParamType("");
						elementArray[3] = new ClassDetails();
						elementArray[3].setParamName("Home Class");
						elementArray[3].setParamType("");
						elementArray[4] = new ClassDetails();
						elementArray[4].setParamName("Local");
						elementArray[4].setParamType("");
						elementArray[5] = new ClassDetails();
						elementArray[5].setParamName("URL");
						elementArray[5].setParamType(rd.getProviderUrl());
					}
					if (bindingType == BindingType.WEB_SERVICE) {
						String url = rd.getResourceNode().getString(
								"ServiceUrl", "");
						elementArray = new ClassDetails[1];
						elementArray[0] = new ClassDetails();
						elementArray[0].setParamName("WSDL");
						elementArray[0].setParamType(url);
					}
				}
			}
		} catch (Exception e) {
			throw new UifDisplayableException("Error: "
					+ e.getLocalizedMessage());
		}
		((AifAnalyserAnalyseItemResults)output).setElementArray(elementArray);

	}

	private void analyzeDatatype() throws ClassNotFoundException {
		ArrayList<ClassDetails> arl = new ArrayList<ClassDetails>();
		String clazzName = item.replace("Type -> ", "");
		if (clazzName.endsWith("[]")) {
			clazzName = clazzName.substring(0, clazzName.length() - 2);
		}

		Method[] methods = Class.forName(clazzName).getDeclaredMethods();
		ClassDetails clsdtl = null;
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			String className = methods[i].getReturnType().getName();
			if (methodName.startsWith("get")
					&& methods[i].getParameterTypes().length == 0) {
				if (methods[i].getReturnType().isArray())
					className = className.substring(2, className.length() - 1)
							+ "[]";
				clsdtl = new ClassDetails();
				clsdtl.setParamName(methodName.substring(3));
				clsdtl.setParamType(className);
				arl.add(clsdtl);
			}
			if (methods[i].getName().startsWith("is")) {
				clsdtl = new ClassDetails();
				clsdtl.setParamName(methodName.substring(2));
				clsdtl.setParamType(className);
				arl.add(clsdtl);
			}
		}
		elementArray = (ClassDetails[]) arl
				.toArray(new ClassDetails[arl.size()]);
	}

	private int getBindingType() {
		for (int i = 0; i < valueMap.length; i++) {
			if (valueMap[i].getParentFldVal().equals("Binding")) {
				return Integer.parseInt(valueMap[i].getChildFldVal());
			}
		}
		return -1;
	}

	private String getAifResourceName() {
		for (int i = 0; i < valueMap.length; i++) {
			if (valueMap[i].getParentFldVal().equals("Resource")) {
				return valueMap[i].getChildFldVal();
			}
		}
		return "";
	}

}
