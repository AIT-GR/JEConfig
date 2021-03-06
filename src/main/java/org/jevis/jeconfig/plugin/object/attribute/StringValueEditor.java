/**
 * Copyright (C) 2009 - 2014 Envidatec GmbH <info@envidatec.com>
 *
 * This file is part of JEConfig.
 *
 * JEConfig is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation in version 3.
 *
 * JEConfig is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * JEConfig. If not, see <http://www.gnu.org/licenses/>.
 *
 * JEConfig is part of the OpenJEVis project, further project information are
 * published at <http://www.OpenJEVis.org/>.
 */
package org.jevis.jeconfig.plugin.object.attribute;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
//import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.jevis.api.JEVisAttribute;
import org.jevis.api.JEVisConstants;
import org.jevis.api.JEVisException;
import org.jevis.api.JEVisSample;
import org.jevis.jeconfig.JEConfig;
import org.joda.time.DateTime;

/**
 *
 * @author Florian Simon <florian.simon@envidatec.com>
 */
public class StringValueEditor implements AttributeEditor {

    HBox box = new HBox();
    public JEVisAttribute _attribute;
    private TextField _field;
    private JEVisSample _newSample;
    private JEVisSample _lastSample;
    private final BooleanProperty _changed = new SimpleBooleanProperty(false);

    public StringValueEditor(JEVisAttribute att) {
        _attribute = att;
    }

    @Override
    public boolean hasChanged() {
//        System.out.println(_attribute.getName() + " changed: " + _hasChanged);
        return _changed.getValue();
    }

    @Override
    public BooleanProperty getValueChangedProperty() {
        return _changed;
    }

//    @Override
//    public void setAttribute(JEVisAttribute att) {
//        _attribute = att;
//    }
    @Override
    public void commit() throws JEVisException {
        if (hasChanged() && _newSample != null) {

            //TODO: check if tpye is ok, maybe better at imput time
            _newSample.commit();
        }
    }

    @Override
    public Node getEditor() {
        try {
            buildTextFild();
        } catch (Exception ex) {

        }

        return box;
//        return _field;
    }

    private void buildTextFild() throws JEVisException {
        if (_field == null) {
            _field = new TextField();
            _field.setPrefWidth(500);//TODO: remove this workaround

            if (_attribute.getLatestSample() != null) {
                _field.setText(_attribute.getLatestSample().getValueAsString());
                _lastSample = _attribute.getLatestSample();
            } else {
                _field.setText("");
            }

            _field.setOnKeyReleased(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    try {
                        if (_lastSample == null) {
                            System.out.println("new Value");
//                            _lastSample = _attribute.buildSample(new DateTime(), _field.getText());
                            _newSample = _attribute.buildSample(new DateTime(), _field.getText());
                            _changed.setValue(true);
                        } else if (!_lastSample.getValueAsString().equals(_field.getText())) {
                            _changed.setValue(true);
                            _newSample = _attribute.buildSample(new DateTime(), _field.getText());
                            System.out.println("value changed");
                        } else if (_lastSample.getValueAsString().equals(_field.getText())) {
                            _changed.setValue(false);
                        }
                    } catch (JEVisException ex) {
                        Logger.getLogger(NumberWithUnit.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

//            _field.focusedProperty().addListener(new ChangeListener<Boolean>() {
//                @Override
//                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
//                    try {
//                        if (newPropertyValue) {
//                        } else {
//                            if (_lastSample != null) {
//                                if (!_lastSample.getValueAsString().equals(_field.getText())) {
//                                    _hasChanged = true;
//                                } else {
//                                    _hasChanged = false;
//                                }
//                            } else {
//                                if (!_field.getText().equals("")) {
//                                    _hasChanged = true;
//                                }
//                            }
//
//                            if (_hasChanged) {
//                                try {
//                                    _newSample = _attribute.buildSample(new DateTime(), _field.getText());
//                                } catch (JEVisException ex) {
//                                    Logger.getLogger(StringValueEditor.class.getName()).log(Level.SEVERE, null, ex);
//
//                                    ExceptionDialog dia = new ExceptionDialog();
//                                    dia.show(JEConfig.getStage(), "Error", "Could commit changes to Server", ex, PROGRAMM_INFO);
//                                }
//                            }
//                        }
//                    } catch (Exception ex) {
//
//                    }
//
//                }
//            });
            _field.setPrefWidth(500);
            _field.setId("attributelabel");

            if (_attribute.getType().getDescription() != null && !_attribute.getType().getDescription().isEmpty()) {
                Tooltip tooltip = new Tooltip();
                try {
                    tooltip.setText(_attribute.getType().getDescription());
                    tooltip.setGraphic(JEConfig.getImage("1393862576_info_blue.png", 30, 30));
                    _field.setTooltip(tooltip);
                } catch (JEVisException ex) {
                    Logger.getLogger(StringValueEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            box.getChildren().add(_field);
            HBox.setHgrow(_field, Priority.ALWAYS);

            try {
                if (_attribute.getType().getValidity() == JEVisConstants.Validity.AT_DATE) {
                    Button chartView = new Button();
                    chartView.setGraphic(JEConfig.getImage("1394566386_Graph.png", 20, 20));
                    chartView.setStyle("-fx-padding: 0 2 0 2;-fx-background-insets: 0;-fx-background-radius: 0;-fx-background-color: transparent;");

                    chartView.setMaxHeight(_field.getHeight());
                    chartView.setMaxWidth(20);

                    box.getChildren().add(chartView);
                    HBox.setHgrow(chartView, Priority.NEVER);

//                    chartView.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent t) {
//                            Stage dialogStage = new Stage();
//                            dialogStage.setTitle("Sample Editor");
//                            HBox root = new HBox();
//
//                            root.getChildren().add(new SampleTable(_attribute));
//
//                            Scene scene = new Scene(root);
//                            scene.getStylesheets().add("/styles/Styles.css");
//                            dialogStage.setScene(scene);
//                            dialogStage.show();
//
//                        }
//                    });
                }
            } catch (Exception ex) {
                Logger.getLogger(StringValueEditor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
