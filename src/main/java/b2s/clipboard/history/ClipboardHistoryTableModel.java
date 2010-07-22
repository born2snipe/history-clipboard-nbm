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

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ClipboardHistoryTableModel extends AbstractTableModel {

    private List<String> rows = new ArrayList<String>();

    public ClipboardHistoryTableModel(List<String> contents) {
        rows.addAll(contents);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String value = rows.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return value;
        }
        return null;
    }

}
