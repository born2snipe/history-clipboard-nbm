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

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.openide.actions.PasteAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.ExClipboard;

public class PasteFromHistoryAction implements ActionListener   {
    private ClipboardHistory clipboardHistory;
    private ExClipboard clipboard;
    private PasteAction pasteAction;
    private HistoryDialogDisplayer dialogDisplayer;

    public PasteFromHistoryAction() {
        dialogDisplayer = new HistoryDialogDisplayer();
        clipboardHistory = ClipboardHistoryInstaller.CLIPBOARD_HISTORY;
        clipboard = Lookup.getDefault().lookup(ExClipboard.class);
//        pasteAction = Lookup.getDefault().lookup(PasteAction.class);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (clipboardHistory.hasContents()) {
            int row = dialogDisplayer.display(clipboardHistory);
            if (row >= 0) {
                clipboardHistory.moveToTop(row);
                StringSelection string = new StringSelection(clipboardHistory.top());
                clipboard.setContents(string, string);
            }
        }
    }

    void setHistoryDialogDisplayer(HistoryDialogDisplayer dialogDisplayer) {
        this.dialogDisplayer = dialogDisplayer;
    }

    void setClipboardHistory(ClipboardHistory clipboardHistory) {
        this.clipboardHistory = clipboardHistory;
    }

    void setClipboard(ExClipboard clipboard) {
        this.clipboard = clipboard;
    }
}
