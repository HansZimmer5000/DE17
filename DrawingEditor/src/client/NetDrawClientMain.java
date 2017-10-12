/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: NetDrawClientMain.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package client;

import java.awt.event.*;
import java.awt.*;
import java.applet.*;

import java.util.Vector;

public class NetDrawClientMain {

    public static void main(String[] args) {
        
	NetDrawClient client = new NetDrawClient("Jibble NetDraw Client", 640, 480, 1000, 500);

    }

}
