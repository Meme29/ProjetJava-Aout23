package userInterface;

import model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeListingTableModel extends AbstractTableModel{
    private ArrayList<String> columnNames;
    private ArrayList<Employee> contents;

    public EmployeeListingTableModel(ArrayList<Employee> contents){
        this.columnNames = new ArrayList<>();
        columnNames.add("matricule");
        columnNames.add("lastName");
        columnNames.add("firstName");
        columnNames.add("supervisor");
        columnNames.add("position");

        columnNames.add("nbChilds");
        columnNames.add("sex");
        columnNames.add("birthday");
        columnNames.add("isMarried");
        columnNames.add("phoneNumber");
        setContents(contents);
    }

    public void setContents(ArrayList<Employee> contents) {
        this.contents = contents;
    }
    public int getRowCount() {
        return contents.size();
    }
    public int getColumnCount() {
        return columnNames.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee query = contents.get(rowIndex);
        switch(columnIndex) {
            case 0 : return query.getMatricule();
            case 1: return query.getLastName();
            case 2 : return query.getFirstName();
            case 3: return query.getSupervisor();
            case 4: return query.getPosition();
            case 5: return query.getNbChilds();
            case 6: return query.getSex();
            case 7: return query.getBirthday();
            case 8: return query.getMArried();
            case 9: return query.getPhoneNumber();
            default:return null;
        }
    }

    public String getColumnName(int column) { return columnNames.get(column); }

    public Class getColumnClass(int columnIndex) {
        Class c;
        switch (columnIndex) {
            case 0: c= String.class; break;
            case 1: c= String.class; break;
            case 2: c = String.class; break;
            case 3: c = String.class; break;
            case 4: c = String.class; break;
            case 5: c = Integer.class; break;
            case 6: c = Gender.class; break;
            case 7: c = Date.class; break;
            case 8: c = Boolean.class; break;
            case 9: c = String.class; break;
            default: c = String.class;
        }
        return c;
    }
}
