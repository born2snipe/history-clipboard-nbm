/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package b2s.clipboard.multi;

import org.junit.Before;
import javax.swing.JLabel;
import javax.swing.JTable;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClipboardContentsCellRendererTest {
    private ClipboardContentsCellRenderer renderer;
    private JTable jtable;

    @Before
    public void init() {
        jtable = mock(JTable.class);

        renderer = new ClipboardContentsCellRenderer();
    }

    @Test
    public void hasNewLineWithFollowingText() {
        JLabel label = (JLabel) renderer.getTableCellRendererComponent(jtable, "text\nmore text", true, true, 0, 0);
        assertEquals("text...", label.getText());
    }


    @Test
    public void hasNewLine_Windows() {
        JLabel label = (JLabel) renderer.getTableCellRendererComponent(jtable, "text\r\n", true, true, 0, 0);
        assertEquals("text...", label.getText());
    }

    @Test
    public void hasNewLine_Unix() {
        JLabel label = (JLabel) renderer.getTableCellRendererComponent(jtable, "text\n", true, true, 0, 0);
        assertEquals("text...", label.getText());
    }

    @Test
    public void simpleText() {
        JLabel label = (JLabel) renderer.getTableCellRendererComponent(jtable, "text", true, true, 0, 0);
        assertEquals("text", label.getText());
    }

}