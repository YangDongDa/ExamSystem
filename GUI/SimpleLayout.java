package GUI;

import LayoutAdapter.MyLayout;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleLayout extends MyLayout {

    List<Component> componentList = new ArrayList<>();
    @Override
    public void addLayoutComponent(String name, Component comp) {
        componentList.add(comp);
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        componentList.remove(comp);
    }

    @Override
    public void layoutContainer(Container parent) {
        int Width = parent.getWidth();
        int Height = parent.getHeight();
        Component[] component = parent.getComponents();

        int x = 20;
        int y = 20;

        for (int i = 0; i < component.length; i++) {

            component[i].setBounds(x,y,Width,20);
            y = y + 20;
        }
    }
}
