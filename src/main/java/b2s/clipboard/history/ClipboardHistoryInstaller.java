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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ClipboardEvent;
import org.openide.util.datatransfer.ClipboardListener;
import org.openide.util.datatransfer.ExClipboard;

public class ClipboardHistoryInstaller extends ModuleInstall implements ClipboardListener {
    public static ClipboardHistory CLIPBOARD_HISTORY;

    @Override
    public void restored() {
        CLIPBOARD_HISTORY = new ClipboardHistory();
        ExClipboard clipboard = Lookup.getDefault().lookup(ExClipboard.class);
        clipboard.addClipboardListener(this);
        addClipboardContentsToHistory(clipboard);
    }

    @Override
    public void clipboardChanged(ClipboardEvent ce) {
        addClipboardContentsToHistory(ce.getClipboard());
    }

    private void addClipboardContentsToHistory(ExClipboard clipboard) {
        try {
            Transferable contents = clipboard.getContents(null);
            CLIPBOARD_HISTORY.add((String) contents.getTransferData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException ex) {
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
