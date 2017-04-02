package department.ui.controller.view;

import department.model.ITeacherModel;
import department.ui.controller.DefaultProgressMessage;
import department.ui.controller.MainController;
import department.ui.controller.edit.EditTeacherController;
import department.ui.controller.model.TeacherViewModel;
import department.ui.utils.UiConstants;
import department.ui.utils.UiUtils;
import department.utils.RxUtils;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import lombok.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.logging.Level;

import static department.ui.utils.UiUtils.DATE_FLD_MAPPER;
import static department.ui.utils.UiUtils.NULLABLE_FLD_MAPPER;

/**
 * Created by Максим on 2/1/2017.
 */
@Log
@Value
@EqualsAndHashCode(callSuper = false)
@Getter(value = AccessLevel.NONE)
@Controller
public final class TeacherTabController extends ListTabController<TeacherViewModel> {

    MainController mainController;
    ITeacherModel model;

    @Autowired
    public TeacherTabController(ITeacherModel model, MainController mainController) {
        this.mainController = mainController;
        this.model = model;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initSubclasses() {

        final TableColumn<TeacherViewModel, String> firstNameCol = new TableColumn<>("Повне ім'я"),
                phoneCol = new TableColumn<>("Телефон"), startDateCol = new TableColumn<>("Початок роботи"),
                positionCol = new TableColumn<>("Посада"), degreeCol = new TableColumn<>("Степінь");

        firstNameCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getFirstNameObs()));
        phoneCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getPhoneObs().map(NULLABLE_FLD_MAPPER)));
        startDateCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getStartDateObs().map(DATE_FLD_MAPPER)));
        positionCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getPositionObs().map(NULLABLE_FLD_MAPPER)));
        degreeCol.setCellValueFactory(param -> RxUtils.fromRx(param.getValue().getDegreeObs().map(NULLABLE_FLD_MAPPER)));
        // setup table columns and content
        tableView.getColumns().addAll(firstNameCol, phoneCol, startDateCol, positionCol, degreeCol);

        tableView.setRowFactory(tv -> {
            TableRow<TeacherViewModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {

                    val stage = new Stage();
                    val loader = UiUtils.newLoader("/view/partials/_formEditTeacher.fxml", EditTeacherController.class);

                    try {
                        stage.setScene(new Scene(loader.load()));

                        EditTeacherController controller = loader.getController();

                        controller.setModel(row.getItem());
                        stage.centerOnScreen();
                        stage.show();
                        stage.sizeToScene();
                    } catch (final IOException e) {
                        log.log(Level.SEVERE, "Failed to open form", e);
                    }
                }
            });
            return row;
        });

        val size = tableView.getColumns().size();

        val progress = new DefaultProgressMessage(mainController);
        loadData(new ProgressCallback() {
                     @Override
                     public void onShow() {
                         progress.showProgress("Завантаження списку викладачів...");
                     }

                     @Override
                     public void onHide() {
                         progress.hideProgress();
                     }

                     @Override
                     public void onFailure(Throwable th) {
                         processError(th);
                     }
                 }, model.fetchTeachers(0, UiConstants.RESULTS_PER_PAGE), model.count()
        );
    }

    @Override
    protected void onNewPageIndexSelected(int oldIndex, int newIndex) {
        doLoad(newIndex);
    }

    @Override
    protected void onRefresh() {
        val indx = pagination.currentPageIndexProperty().get();

        if (indx >= 0) {
            doLoad(indx);
        }
    }

    private void doLoad(int indx) {
        val toastId = mainController.showProgress("Завантаження списку викладачів...");

        model.fetchTeachers(indx * UiConstants.RESULTS_PER_PAGE, UiConstants.RESULTS_PER_PAGE)
                .doOnTerminate(() -> mainController.hideProgress(toastId))
                .subscribe(this::setTableContent, this::processError);
    }

    private void processError(Throwable th) {
        log.log(Level.WARNING, "Failed to fetch teachers", th);
        mainController.showError("Не вдалося завантажити список викладачів...", UiConstants.DURATION_NORMAL);
    }

}
