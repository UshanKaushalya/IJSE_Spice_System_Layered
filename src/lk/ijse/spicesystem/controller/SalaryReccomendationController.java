package lk.ijse.spicesystem.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.spicesystem.bo.BOFactory;
import lk.ijse.spicesystem.bo.custom.EmployeeBO;
import lk.ijse.spicesystem.bo.custom.PaymentMethodBO;
import lk.ijse.spicesystem.db.DBConnection;
import org.controlsfx.control.Notifications;
//import lk.ijse.spicesystem.modelBefore.SalaryRecomendationModel;

import java.sql.SQLException;

public class SalaryReccomendationController {
    public JFXComboBox cmbEmployeeId;
    public Label lblEmployee;
    public Label lblSalaryPerDay;
    public JFXComboBox cmbMonth;
    public JFXComboBox cmbPaymentMethod;
    public JFXTextField txtDays;
    public Label lblCost;
    public AnchorPane dashboardPane;

    ObservableList month = FXCollections.observableArrayList();
    PaymentMethodBO paymentMethodBO = (PaymentMethodBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENTMETHOD);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize(){
        month.addAll("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        try {
            cmbEmployeeId.setItems(employeeBO.getAllId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbEmployeeIdOnAction(ActionEvent actionEvent) {

        try {
            lblEmployee.setText(employeeBO.getEmployeeName(String.valueOf(cmbEmployeeId.getValue())));
            lblSalaryPerDay.setText(String.valueOf(employeeBO.getEmployeeSalary(String.valueOf(cmbEmployeeId.getValue()))));
            cmbMonth.setItems(month);
            cmbPaymentMethod.setItems(paymentMethodBO.paymentmethod());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnsubmitOnAction(ActionEvent actionEvent) {

        try {
            boolean isUpdate = paymentMethodBO.updatePaymentMethod(String.valueOf(cmbPaymentMethod.getValue()), Double.valueOf(lblCost.getText()));

            if(isUpdate){

                Image image = new Image("/lk/ijse/spicesystem/asset/correct.png");
                Notifications notifications = Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Added Suuccesful");
                notifications.title("Spice System");
                notifications.hideAfter(Duration.seconds(3));
                notifications.show();

                initialize();

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                DBConnection.getInstance().getConnection().setAutoCommit(true);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void cmbPaymentMethodOnClicked(MouseEvent mouseEvent) {
        lblCost.setText(String.valueOf(Double.valueOf(txtDays.getText())*Double.valueOf(lblSalaryPerDay.getText())));
    }
}
