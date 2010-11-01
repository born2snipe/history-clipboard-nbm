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

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ClipboardEvent;
import org.openide.util.datatransfer.ClipboardListener;
import org.openide.util.datatransfer.ExClipboard;
import org.openide.windows.WindowManager;

public class ClipboardHistoryInstaller extends ModuleInstall implements ClipboardListener, WindowFocusListener {
    private static final ClipboardContentGrabber CLIPBOARD_CONTENT_GRABBER = new ClipboardContentGrabber();
    public static ClipboardHistory CLIPBOARD_HISTORY;

    @Override
    public void restored() {
        CLIPBOARD_HISTORY = new ClipboardHistory();
        ExClipboard clipboard = Lookup.getDefault().lookup(ExClipboard.class);
        clipboard.addClipboardListener(this);
        addClipboardContentsToHistory();

        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
            @Override
            public void run() {
                Frame window = WindowManager.getDefault().getMainWindow();
                window.addWindowFocusListener(ClipboardHistoryInstaller.this);
            }
        });
    }

    @Override
    public void clipboardChanged(ClipboardEvent ce) {
        addClipboardContentsToHistory();
    }

    private void addClipboardContentsToHistory() {
        CLIPBOARD_HISTORY.add(CLIPBOARD_CONTENT_GRABBER.grab());
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        addClipboardContentsToHistory();
    }

    @Override
    public void windowLostFocus(WindowEvent e) {

    }
}
