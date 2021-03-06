/* AIFresourceInfo:com.amdocs.bss.tool.AifAnalyser.AnalyseList.oper
 *
 * @AifAnalyserAnalyseListResults.java        
 *
 * Copyright (c) 1996 to 2008 Amdocs.  All rights reserved.
 *
 * These coded instructions, statements and computer programs contain
 * unpublished trade secrets and proprietary information of Amdocs
 * and are protected by Federal copyright law and trade secret law.
 * They may not be disclosed to third parties or
 * used or copied or duplicated in any form, in whole or in part, without
 * the prior written consent of Amdocs.
 */

/*
 * This file is generated by the Amdocs AIF ParamGen utility on
 * $DateTime. Do not edit this file by hand.
 */
package com.amdocs.bss.tool.AifAnalyser;

/**
 * AifAnalyserAnalyseListResults class. 
 * This is the operation result parameter block class for the com.amdocs.bss.tool.AifAnalyser.AnalyseList operation.
 */
// rename the type with JAXB annotation - intended to prevent type name conflict 
// when importing WSDL with this type on consumer - the imported type name might 
// be same as the ParamBlock type artifact in the imported AIF model 
@javax.xml.bind.annotation.XmlType(name="AifAnalyserAnalyseListResultsWs")
public final class AifAnalyserAnalyseListResults implements com.amdocs.aif.consumer.OperationResults, amdocs.core.mapping.Mappable, java.io.Serializable {

    private static final long serialVersionUID = 117945876683265L;

    private com.amdocs.bss.tool.AifAnalyser.OperationDetails[] m_OperationArray;

    /**
     * Default constructor.
     */
    public AifAnalyserAnalyseListResults() {
        /* no-op */
    }

    /**
     * Assign the fields of the parameter block from <tt>map</tt>. The keys of <tt>map</tt> should be the
     * the parameter names and the values must be of the correct type (Object boxes for primitives). Unknown
     * keys or invalid types are ignored (no exceptions are thrown).
     * 
     * @param map Map of parameter names and values
     */ 
    public void fromMap(java.util.Map map){

        if(map != null)
        {
            Object value = null;

            value = map.get("OperationArray");
            if(value != null && value instanceof com.amdocs.bss.tool.AifAnalyser.OperationDetails[])
                m_OperationArray = (com.amdocs.bss.tool.AifAnalyser.OperationDetails[])value;
        }
    }

    /**
     * Construct a <tt>Map</tt> from the fields of the parameter block. The keys of <tt>map</tt> are the
     * the parameter names and the values are their values. Primitives are wrapped in their Object box.
     *
     * @return Map of parameter names and values
     */
    public java.util.Map toMap() {
        java.util.HashMap retMap = new java.util.HashMap();
        retMap.put("OperationArray", m_OperationArray);
        return retMap;
    }

    /**
     * Get the value of the OperationArray field. 
     * @return OperationArray field value.
     */
    public com.amdocs.bss.tool.AifAnalyser.OperationDetails[] getOperationArray() {
        return this.m_OperationArray;
    }
    
    /**
     * Set the value of the OperationArray field.
     * @param value
     */
    public void setOperationArray(com.amdocs.bss.tool.AifAnalyser.OperationDetails[] value) {
        this.m_OperationArray = value;
    }

    /**
     * Return a String representation of this param block object.
     * @return A String representing this parameter block object.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer( "com.amdocs.bss.tool.AifAnalyser.AifAnalyserAnalyseListResults@" );
        buf.append( Integer.toHexString(hashCode())+" {" );
        buf.append( "\nOperationArray=" );
        buf.append( m_OperationArray==null ? "null" : m_OperationArray.toString() );

        buf.append("\n}");
        return buf.toString();
    }
}
