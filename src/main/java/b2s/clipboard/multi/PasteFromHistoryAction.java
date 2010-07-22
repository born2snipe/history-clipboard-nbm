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

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.PasteAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.ExClipboard;

public class PasteFromHistoryAction implements ActionListener   {
    private static final ClipboardHistory CLIPBOARD_HISTORY = ClipboardHistoryInstaller.CLIPBOARD_HISTORY;
    private ExClipboard clipboard;
    private PasteAction pasteAction;

    public PasteFromHistoryAction() {
        clipboard = Lookup.getDefault().lookup(ExClipboard.class);
//        pasteAction = Lookup.getDefault().lookup(PasteAction.class);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (CLIPBOARD_HISTORY.hasContents()) {
            ClipboardHistoryPanel clipboardHistoryPanel = new ClipboardHistoryPanel(CLIPBOARD_HISTORY);

            DialogDescriptor descriptor = new DialogDescriptor(
                    clipboardHistoryPanel,
                    "Choose Content to Paste",
                    true,
                    DialogDescriptor.OK_CANCEL_OPTION,
                    DialogDescriptor.OK_OPTION,
                    null
            );
            if (DialogDescriptor.OK_OPTION == DialogDisplayer.getDefault().notify(descriptor)) {
                int row = clipboardHistoryPanel.getSelectedRow();
                updateClipboardContent(row);
            }
        }
    }

    private void updateClipboardContent(int row) {
        CLIPBOARD_HISTORY.moveToTop(row);
        StringSelection string = new StringSelection(CLIPBOARD_HISTORY.toList().get(0));
        clipboard.setContents(string, string);
    }

}
