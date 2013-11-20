/* AIFresourceInfo:com.amdocs.bss.tool.AifAnalyser.AnalyseItem.oper
 *
 * @AifAnalyserAnalyseItemInputs.java        
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
 * AifAnalyserAnalyseItemInputs class. 
 * This is the operation input parameter block class for the com.amdocs.bss.tool.AifAnalyser.AnalyseItem operation.
 */
// rename the type with JAXB annotation - intended to prevent type name conflict 
// when importing WSDL with this type on consumer - the imported type name might 
// be same as the ParamBlock type artifact in the imported AIF model 
@javax.xml.bind.annotation.XmlType(name="AifAnalyserAnalyseItemInputsWs")
public final class AifAnalyserAnalyseItemInputs implements com.amdocs.aif.consumer.OperationInputs, amdocs.core.mapping.Mappable, java.io.Serializable {

    private static final long serialVersionUID = 117945978800845L;

    private java.lang.String m_Item;
    private com.amdocs.bss.tool.AifAnalyser.OperationDetails[] m_ValueMap;

    /**
     * Default constructor.
     */
    public AifAnalyserAnalyseItemInputs() {
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

            value = map.get("Item");
            if(value != null && value instanceof java.lang.String)
                m_Item = (java.lang.String)value;

            value = map.get("ValueMap");
            if(value != null && value instanceof com.amdocs.bss.tool.AifAnalyser.OperationDetails[])
                m_ValueMap = (com.amdocs.bss.tool.AifAnalyser.OperationDetails[])value;
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
        retMap.put("Item", m_Item);
        retMap.put("ValueMap", m_ValueMap);
        return retMap;
    }

    /**
     * Get the value of the Item field. 
     * @return Item field value.
     */
    public java.lang.String getItem() {
        return this.m_Item;
    }
    
    /**
     * Set the value of the Item field.
     * @param value
     */
    public void setItem(java.lang.String value) {
        this.m_Item = value;
    }

    /**
     * Get the value of the ValueMap field. 
     * @return ValueMap field value.
     */
    public com.amdocs.bss.tool.AifAnalyser.OperationDetails[] getValueMap() {
        return this.m_ValueMap;
    }
    
    /**
     * Set the value of the ValueMap field.
     * @param value
     */
    public void setValueMap(com.amdocs.bss.tool.AifAnalyser.OperationDetails[] value) {
        this.m_ValueMap = value;
    }

    /**
     * Return a String representation of this param block object.
     * @return A String representing this parameter block object.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer( "com.amdocs.bss.tool.AifAnalyser.AifAnalyserAnalyseItemInputs@" );
        buf.append( Integer.toHexString(hashCode())+" {" );
        buf.append( "\nItem=" );
        buf.append( m_Item==null ? "null" : m_Item.toString() );
        buf.append(",");
        buf.append( "\nValueMap=" );
        buf.append( m_ValueMap==null ? "null" : m_ValueMap.toString() );

        buf.append("\n}");
        return buf.toString();
    }
}
