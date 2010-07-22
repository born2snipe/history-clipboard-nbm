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
    public void removeLeadingWhitespaceButNotSpacesInTheMiddleOfWords() {
        assertCellText("hello world", "hello world");
    }

    @Test
    public void removeLeadingWhitespace() {
        assertCellText("text", "    text");
    }
    
    @Test
    public void hasNewLineWithFollowingText() {
        assertCellText("text...", "text\nmore text");
    }


    @Test
    public void hasNewLine_Windows() {
        assertCellText("text...", "text\r\n");
    }

    @Test
    public void hasNewLine_Unix() {
        assertCellText("text...", "text\n");
    }

    @Test
    public void simpleText() {
        assertCellText("text", "text");
    }

    private void assertCellText(String expectedRenderedText, String originalText) {
        JLabel label = (JLabel) renderer.getTableCellRendererComponent(jtable, originalText, true, true, 0, 0);
        assertEquals(expectedRenderedText, label.getText());
    }

}