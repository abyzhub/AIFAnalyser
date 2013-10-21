/* AIFresourceInfo:com.crmimpl.aiftool.ResourceDetails.type
 * This file is generated by the Amdocs data type generation utility on
 * Wed, 16 Oct 2013 18:49:14 IST. Do not edit this file by hand.
 *
 * ResourceDetails.java
 *
 * Copyright (c) 1996 to 2009 Amdocs.  All rights reserved.
 *
 * These coded instructions, statements, and computer programs contain
 * unpublished trade secrets and proprietary information of Amdocs
 * and are protected by Federal copyright law and trade secret law.
 * They may not be disclosed to third parties or used or copied or
 * duplicated in any form, in whole or in part, without the prior
 * written consent of Amdocs.
 */

package com.crmimpl.aiftool;

  public class ResourceDetails  implements com.amdocs.datatypes.DataType, java.io.Serializable, java.lang.Cloneable {

    private static final long serialVersionUID = 117944730614081L;
    public static final String JDK_VERSION = "5.0";


 

      private java.lang.String ProxyUserPoolSize;
      private java.lang.String ConnectionHost;

    /**
     * Default constructor.
     * Constructs a <tt> com.crmimpl.aiftool.ResourceDetails </tt> object.
     */
    public ResourceDetails(){
         super();
    }

    /**
     * @return value of "ProxyUserPoolSize" field.
     */
      public java.lang.String getProxyUserPoolSize() {
        return ProxyUserPoolSize;
    }

    /**
     * @param value of "ProxyUserPoolSize".
     */
      public void setProxyUserPoolSize(java.lang.String value) {
        ProxyUserPoolSize=value;
    }

    /**
     * @return value of "ConnectionHost" field.
     */
      public java.lang.String getConnectionHost() {
        return ConnectionHost;
    }

    /**
     * @param value of "ConnectionHost".
     */
      public void setConnectionHost(java.lang.String value) {
        ConnectionHost=value;
    }

        /**
         * Clone the object.
         * @return returns cloned object.
         */
          public Object clone()
    {
                try {
                        return super.clone();
                } catch (RuntimeException e) {
                        throw e;
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }


}
