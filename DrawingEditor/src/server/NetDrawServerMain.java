/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: NetDrawServerMain.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package server;

public class NetDrawServerMain {
        
    public static void main(String[] args) {
        
        NetDrawServer server = new NetDrawServer(1333);
        server.launch();
        
    }
        
}