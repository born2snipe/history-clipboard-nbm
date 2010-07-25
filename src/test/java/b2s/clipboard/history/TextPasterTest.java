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

package b2s.clipboard.history;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.junit.Before;
import javax.swing.text.JTextComponent;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TextPasterTest {
    JTextComponent textComponent;
    TextPaster paster;
    Document document;

    @Before
    public void setUp() {
        textComponent = mock(JTextComponent.class);
        document = mock(Document.class);

        paster = new TextPaster();

        when(textComponent.getDocument()).thenReturn(document);
        when(textComponent.isEditable()).thenReturn(true);
    }

    @Test
    public void hasSelectedText() {
        when(textComponent.getSelectionStart()).thenReturn(0);
        
        paster.paste("text", textComponent);

        verify(textComponent).replaceSelection("text");
    }

    @Test
    public void noSelectedText() throws BadLocationException {
        when(textComponent.getSelectionStart()).thenReturn(-1);
        when(textComponent.getCaretPosition()).thenReturn(100);

        paster.paste("text", textComponent);

        verify(document).insertString(100, "text", null);
    }
}
