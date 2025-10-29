package org.FoodHub;

import java.io.File;

public interface SaveState {
    void save(OrderManager om);
    void load(File filePath, OrderManager om);
}
