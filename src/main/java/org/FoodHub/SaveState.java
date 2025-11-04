package org.FoodHub;

import java.io.File;
/**
 * Interface for saving and loading order manager state.
 */
public interface SaveState {
    //Saves to specified file.
    void save(OrderManager om, File filePath);
    //Loads from specified file.
    void load(File filePath, OrderManager om);
}
