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

import java.util.Arrays;
import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ClipboardHistoryTest {
    private ClipboardHistory history;

    @Before
    public void init() {
        history = new ClipboardHistory(5);
    }

    @Test
    public void trimOffExtraValues() {
        history.add("text1");
        history.add("text2");
        history.add("text3");
        history.add("text4");
        history.add("text5");
        history.add("text6");

        assertEquals(Arrays.asList("text6", "text5", "text4", "text3", "text2"), history.toList());
    }

    @Test
    public void duplicateFoundMoveToTheTop() {
        history.add("text");
        history.add("text2");
        history.add("text");

        assertEquals(Arrays.asList("text", "text2"), history.toList());
    }

    @Test
    public void doNotAllowDuplicateEntries() {
        history.add("text");
        history.add("text");

        assertEquals(Arrays.asList("text"), history.toList());
    }

    @Test
    public void moveToTop() {
        history.add("text");
        history.add("text2");

        history.moveToTop(1);

        assertEquals(Arrays.asList("text", "text2"), history.toList());
    }

    @Test
    public void toList_ensureOrderIsSaved() {
        history.add("text");
        history.add("text2");

        assertEquals(Arrays.asList("text2", "text"), history.toList());
    }
}
