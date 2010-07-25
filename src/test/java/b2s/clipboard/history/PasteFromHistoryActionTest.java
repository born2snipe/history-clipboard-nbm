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

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ActionMap;
import org.openide.nodes.Children;
import org.openide.nodes.AbstractNode;
import javax.swing.JEditorPane;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.junit.Before;
import java.awt.datatransfer.StringSelection;
import org.mockito.InOrder;
import org.openide.util.datatransfer.ExClipboard;
import org.junit.Test;
import org.openide.util.Lookup;
import static org.mockito.Mockito.*;

public class PasteFromHistoryActionTest {
    private final Node nodes[] = new Node[0];
    HistoryDialogDisplayer dialogDisplayer;
    ClipboardHistory clipboardHistory;
    ExClipboard clipboard;
    PasteFromHistoryAction action;
    JEditorPane editorPane;
    ActionMap actionMap;
    Action pasteAction;
    EditorCookieUtil editorCookieUtil;

    @Before
    public void init() {
        dialogDisplayer = mock(HistoryDialogDisplayer.class);
        clipboardHistory = mock(ClipboardHistory.class);
        clipboard = mock(ExClipboard.class);
        editorPane = new JEditorPane();
        actionMap = mock(ActionMap.class);
        pasteAction = mock(Action.class);
        editorCookieUtil = mock(EditorCookieUtil.class);

        action = new PasteFromHistoryAction();
        action.setHistoryDialogDisplayer(dialogDisplayer);
        action.setClipboardHistory(clipboardHistory);
        action.setClipboard(clipboard);
        action.setEditorCookieUtil(editorCookieUtil);

        when(editorCookieUtil.editor(nodes)).thenReturn(editorPane);

        editorPane.setActionMap(actionMap);
    }

    @Test
    public void hasNoClipboardContents() {
        when(clipboardHistory.hasContents()).thenReturn(false);

        action.performAction(nodes);

        verifyZeroInteractions(clipboard, dialogDisplayer);
    }


    @Test
    public void hasClipboardContentsAndUserCancels() {
        when(clipboardHistory.hasContents()).thenReturn(true);
        when(dialogDisplayer.display(clipboardHistory)).thenReturn(-1);

        action.performAction(nodes);

        verifyZeroInteractions(clipboard);
    }

    @Test
    public void hasClipboardContentsAndUserSelectsSomethingToPaste() {
        InOrder inOrder = inOrder(clipboardHistory, clipboard, pasteAction);

        when(clipboardHistory.hasContents()).thenReturn(true);
        when(dialogDisplayer.display(clipboardHistory)).thenReturn(1);
        when(clipboardHistory.top()).thenReturn("value");
        when(actionMap.get("paste")).thenReturn(pasteAction);

        action.performAction(nodes);

        inOrder.verify(clipboardHistory).moveToTop(1);
        inOrder.verify(clipboard).setContents(isA(StringSelection.class), isA(StringSelection.class));
        inOrder.verify(pasteAction).actionPerformed(isA(ActionEvent.class));
    }

}
