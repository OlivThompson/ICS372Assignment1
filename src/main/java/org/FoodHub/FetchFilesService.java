package org.FoodHub;

import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Threading Service
 *
 *
 * */

public class FetchFilesService implements Runnable{

    private final TableView<String> tableToUpdate;

    public FetchFilesService(TableView<String> table){
        this.tableToUpdate = table;
    }

    @Override
    public void run(){


    }
}
