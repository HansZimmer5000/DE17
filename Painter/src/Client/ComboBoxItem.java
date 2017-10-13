/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ComboBoxItem.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class ComboBoxItem {
    
    public ComboBoxItem(BufferedImage image, String title) {
        this.imageIcon = new ImageIcon(image, title);
        this.title = title;
    }
    
    public String toString() {
        return title;
    }
    
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    ImageIcon imageIcon;
    String title;
    
}