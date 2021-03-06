package app.kenavo.billapplication.utils;

import app.kenavo.billapplication.model.Bill;
import app.kenavo.billapplication.model.Mission;
import app.kenavo.billapplication.services.BillService;
import app.kenavo.billapplication.services.BillServiceImpl;
import app.kenavo.billapplication.services.MissionService;
import app.kenavo.billapplication.services.MissionsServiceImpl;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class AddColumn {

    private static final Navigation nav = new Navigation();
    private static final MissionService missionService = new MissionsServiceImpl();
    private static final BillService billService = new BillServiceImpl();

    public static Callback<TableColumn<Mission, Void>, TableCell<Mission, Void>> addDeleteMissionBill(TableView<Mission> table, List<Mission> missions) {

        return new Callback<>() {
            @Override
            public TableCell call(final TableColumn<Mission, Void> param) {
                return new TableCell<Mission, String>() {

                    final Button btn = new Button("Delete");

                    {
                        btn.setOnAction(event -> {
                            Mission mission = table.getItems().get(getIndex());
                            List<Mission> missionsToUpdate = new ArrayList<>();
                            List<Bill> bills = billService.getAllBills();
                            Bill bill = billService.getBillById(bills, mission.getBillId());
                            missionsToUpdate.add(mission);
                            mission.setBillId("None");
                            missionService.update(missions, missionsToUpdate);
                            Bill updatedBill = billService.getAmountBill(bill, missions);
                            billService.update(bills, updatedBill);
                            table.getItems().remove(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                        setText(null);
                    }
                };
            }
        };
    }
}
