/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Jibble NetDraw (JNetDraw).

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ComboBoxCellRenderer.java,v 1.2 2004/02/01 13:27:08 pjm2 Exp $

*/

package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

class ComboBoxCellRenderer extends JLabel implements ListCellRenderer {
    
    public ComboBoxCellRenderer(JComboBox comboBox) {
        this.comboBox = comboBox;
        setOpaque(true);
    }
    
    public Component getListCellRendererComponent(JList listbox, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if(UIManager.getLookAndFeel().getName().equals("CDE/Motif")) {
            if (index == -1) {
                setOpaque(false);
            }
            else {
                setOpaque(true);
            }
        }
        else {
            setOpaque(true);
        }

        if (value == null) {
            setText("");
            setIcon(null);
        }
        
        setIcon(((ComboBoxItem)value).getImageIcon());
        //setIcon((ImageIcon)h.get("image"));
        setText(value.toString());
        setBackground(UIManager.getColor("ComboBox.background"));
        setForeground(UIManager.getColor("ComboBox.foreground"));
        
        //}
        return this;
      }
    
    JComboBox comboBox = null;
    
}