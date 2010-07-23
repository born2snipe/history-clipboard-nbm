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
import javax.swing.text.JTextComponent;

public class TextPaster {
    private CurrentEditorRetriever editorRetriever;

    public TextPaster() {
        editorRetriever = new CurrentEditorRetriever();
    }

    public void paste(String text) {
        JTextComponent textComponent = editorRetriever.retreive();
        if (textComponent.getSelectionStart() >= 0) {
            textComponent.replaceSelection(text);
        } else {
            try {
                textComponent.getDocument().insertString(textComponent.getCaretPosition(), text, null);
            } catch (BadLocationException ex) {
                
            }
        }
    }

    void setCurrentEditorRetriever(CurrentEditorRetriever editorRetriever) {
        this.editorRetriever = editorRetriever;
    }
}
