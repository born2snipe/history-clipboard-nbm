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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.datatransfer.ClipboardEvent;
import org.openide.util.datatransfer.ClipboardListener;
import org.openide.util.datatransfer.ExClipboard;

public class PasteFromHistoryAction implements ActionListener, ClipboardListener  {
    private static final ClipboardHistory CLIPBOARD_HISTORY = new ClipboardHistory();

    public PasteFromHistoryAction() {
        ExClipboard clipboard = Lookup.getDefault().lookup(ExClipboard.class);
        clipboard.addClipboardListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DialogDescriptor descriptor = new DialogDescriptor(
                new ClipboardHistoryPanel(CLIPBOARD_HISTORY),
                "Choose Content to Paste",
                true,
                new Object[]{DialogDescriptor.OK_OPTION, DialogDescriptor.CANCEL_OPTION},
                DialogDescriptor.OK_OPTION,
                DialogDescriptor.DEFAULT_ALIGN,
                null,
                null
        );
        DialogDisplayer.getDefault().notify(descriptor);
    }

    @Override
    public void clipboardChanged(ClipboardEvent ce) {
        try {
            ExClipboard clipboard = ce.getClipboard();
            Transferable contents = clipboard.getContents(null);
            CLIPBOARD_HISTORY.add((String) contents.getTransferData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException ex) {
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
    }
}
