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
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JEditorPane;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.util.datatransfer.ExClipboard;

public class PasteFromHistoryAction extends CookieAction   {
    private ClipboardHistory clipboardHistory;
    private ExClipboard clipboard;
    private HistoryDialogDisplayer dialogDisplayer;
    private EditorCookieUtil editorCookieUtil;

    public PasteFromHistoryAction() {
        dialogDisplayer = new HistoryDialogDisplayer();
        clipboardHistory = ClipboardHistoryInstaller.CLIPBOARD_HISTORY;
        clipboard = Lookup.getDefault().lookup(ExClipboard.class);
        editorCookieUtil = new EditorCookieUtil();
    }

    @Override
    protected boolean enable(Node[] activatedNodes) {
        if (editorCookieUtil.hasEditor(activatedNodes)) {
            final JEditorPane editor = editorCookieUtil.editor(activatedNodes);
            ActionMap actions = editor.getActionMap();
            return actions.get("paste").isEnabled();
        }
        return false;
    }

    @Override
    protected int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected Class<?>[] cookieClasses() {
        return new Class[]{EditorCookie.class};
    }

    @Override
    protected void performAction(Node[] nodes) {
        if (clipboardHistory.hasContents()) {
            int row = dialogDisplayer.display(clipboardHistory);
            if (row >= 0) {
                clipboardHistory.moveToTop(row);
                StringSelection string = new StringSelection(clipboardHistory.top());
                clipboard.setContents(string, string);

                JEditorPane editor = editorCookieUtil.editor(nodes);
                ActionMap actions = editor.getActionMap();
                Action paste = actions.get("paste");
                paste.actionPerformed(new ActionEvent(editor, editor.hashCode(), ""));
            }
        }
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(PasteFromHistoryAction.class, "CTL_PasteFromHistoryAction");
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
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

    void setEditorCookieUtil(EditorCookieUtil editorCookieUtil) {
        this.editorCookieUtil = editorCookieUtil;
    }


}
