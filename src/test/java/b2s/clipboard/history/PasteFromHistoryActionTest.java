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

import org.junit.Before;
import java.awt.datatransfer.StringSelection;
import org.mockito.InOrder;
import org.openide.util.datatransfer.ExClipboard;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class PasteFromHistoryActionTest {
    HistoryDialogDisplayer dialogDisplayer;
    ClipboardHistory clipboardHistory;
    ExClipboard clipboard;
    PasteFromHistoryAction action;
    TextPaster paster;

    @Before
    public void init() {
        dialogDisplayer = mock(HistoryDialogDisplayer.class);
        clipboardHistory = mock(ClipboardHistory.class);
        clipboard = mock(ExClipboard.class);
        paster = mock(TextPaster.class);

        action = new PasteFromHistoryAction(null);
        action.setHistoryDialogDisplayer(dialogDisplayer);
        action.setClipboardHistory(clipboardHistory);
        action.setClipboard(clipboard);
        action.setTextPaster(paster);
    }

        @Test
    public void hasNoClipboardContents() {
        when(clipboardHistory.hasContents()).thenReturn(false);

        action.actionPerformed(null);

        verifyZeroInteractions(clipboard, dialogDisplayer);
    }


    @Test
    public void hasClipboardContentsAndUserCancels() {
        when(clipboardHistory.hasContents()).thenReturn(true);
        when(dialogDisplayer.display(clipboardHistory)).thenReturn(-1);

        action.actionPerformed(null);

        verifyZeroInteractions(clipboard, paster);
    }

    @Test
    public void hasClipboardContentsAndUserSelectsSomethingToPaste() {
        InOrder inOrder = inOrder(clipboardHistory, clipboard, paster);

        when(clipboardHistory.hasContents()).thenReturn(true);
        when(dialogDisplayer.display(clipboardHistory)).thenReturn(1);
        when(clipboardHistory.top()).thenReturn("value");

        action.actionPerformed(null);

        StringSelection stringSource = new StringSelection("value");

        inOrder.verify(clipboardHistory).moveToTop(1);
        inOrder.verify(clipboard).setContents(isA(StringSelection.class), isA(StringSelection.class));
        inOrder.verify(paster).paste("value");

    }
}
