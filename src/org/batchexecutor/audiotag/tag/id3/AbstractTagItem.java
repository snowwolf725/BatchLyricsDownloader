/**
 *  @author : Paul Taylor
 *  @author : Eric Farng
 *
 *  Version @version:$Id: AbstractTagItem.java,v 1.3 2007/11/09 13:41:14 paultaylor Exp $
 *
 *  MusicTag Copyright (C)2003,2004
 *
 *  This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser
 *  General Public  License as published by the Free Software Foundation; either version 2.1 of the License,
 *  or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License along with this library; if not,
 *  you can get a copy from http://www.opensource.org/licenses/lgpl-license.php or write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *  Description:
 *   This class is a facade for all classes that can write to an MP3 file. This includes
 *   fragments and fragment body . It has abstract methods that needs to be implemented,
 *   and a few default implementations of other methods.
 */

package org.batchexecutor.audiotag.tag.id3;


import java.nio.ByteBuffer;
import java.util.logging.Logger;

import org.batchexecutor.audiotag.tag.TagException;


/** This specifies a series of methods that have to be implemented by all structural subclasses,
 *  required to support all copy constructors,iterative methods and so on.
 *
 *  TODO Not sure if this is really correct, if really needed should probably be an interface
 */
public abstract class AbstractTagItem
{

    //Logger
    public static Logger logger = Logger.getLogger("org.batchexecutor.jaudiotagger.tag.id3");


    public AbstractTagItem()
    {
    }

    public AbstractTagItem(AbstractTagItem copyObject)
    {
        // no copy constructor in super class
    }

    /**
     * ID string that usually corresponds to the class name, but can be
     * displayed to the user. It is not indended to identify each individual
     * instance.
     *
     * @return ID string
     */
    abstract public String getIdentifier();

    /**
     * Return size of this item
     *
     * @return size of this item
     */
    abstract public int getSize();

    /**
     * @param byteBuffer file to read from
     * @throws TagException on any exception generated by this library.
     */
    abstract public void read(ByteBuffer byteBuffer)
        throws TagException;

    /**
     * Returns true if this datatype is a subset of the argument. This instance
     * is a subset if it is the same class as the argument.
     *
     * @param obj datatype to determine subset of
     * @return true if this instance and its entire datatype array list is a
     *         subset of the argument.
     */
    public boolean isSubsetOf(Object obj)
    {
        if ((obj instanceof AbstractTagItem) == false)
        {
            return false;
        }
        return true;
    }

    /**
     * Returns true if this datatype and its body equals the argument and its
     * body. this datatype is equal if and only if they are the same class
     *
     * @param obj datatype to determine equality of
     * @return true if this datatype and its body are equal
     */
    public boolean equals(Object obj)
    {
        return obj instanceof AbstractTagItem;
    }
}
