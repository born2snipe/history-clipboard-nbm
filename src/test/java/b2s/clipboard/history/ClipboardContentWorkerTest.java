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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import org.openide.util.datatransfer.ExClipboard;
import org.junit.Test;
import org.openide.util.Lookup;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ClipboardContentWorkerTest {
    private Lookup lookup;
    private ExClipboard clipboard;
    private Transferable transferable;
    private ClipboardContentWorker worker;
    
    @Before
    public void init() {
        lookup = mock(Lookup.class);
        clipboard = mock(ExClipboard.class);
        transferable = mock(Transferable.class);
        
        worker = new ClipboardContentWorker(lookup);
        
        when(lookup.lookup(ExClipboard.class)).thenReturn(clipboard);
        when(clipboard.getContents(null)).thenReturn(transferable);
    }
    
    @Test
    public void noTransferableData() throws Exception {
        when(clipboard.getContents(null)).thenReturn(null);
        
        assertNull(worker.doInBackground());
    }
    
    @Test
    public void unsupportedTransferableData() throws Exception {
        when(transferable.isDataFlavorSupported(DataFlavor.stringFlavor)).thenReturn(false);
        
        assertNull(worker.doInBackground());
    }
    
    @Test
    public void transferableData() throws Exception {
        when(transferable.isDataFlavorSupported(DataFlavor.stringFlavor)).thenReturn(true);
        when(transferable.getTransferData(DataFlavor.stringFlavor)).thenReturn("clipboard-contents");
        
        assertEquals("clipboard-contents", worker.doInBackground());
    }

}
