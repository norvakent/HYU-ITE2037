package lab_9;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ServiceManagement {
  public static <T extends Hub> int findIndexByNum(ArrayList<T> tList, int num) {
    for (int i = 0; i < tList.size(); i++) {
      if (tList.get(i).getNumber() == num) return i;
    }
    return -1;
  }

  public static <T extends Hub> T raisePerBox(T t, double rate) {
    t.setPricePerBox(rate*t.getPricePerBox());
    return t;
  }

  public static <T extends Hub> ArrayList<T> raiseAll(Class<T> c, ArrayList<T> tList, double rate) {
    for (T elem:tList) {
      elem.setPricePerBox(rate*elem.getPricePerBox());
    }

    try {
      Field f = c.getField("init_price_per_box");
      double value = f.getDouble(null);
      f.setDouble(null, value*rate);
    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return tList;
  }

  public static <T extends Hub> void packageBoxes(String[] descriptions, Class<T> classtype, ArrayList<T> tList) {
    for (String s:descriptions) {
      try {
        tList.add(classtype.getConstructor(String.class).newInstance(s));
      } catch (NoSuchMethodException | SecurityException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return;
  }

  public static <T extends Hub, U extends Hub> void changeHub(ArrayList<T> tList, int index, Class<U> classtype, ArrayList<U> uList) {
    try {
      uList.add(classtype.getConstructor(String.class).newInstance(tList.get(index).getDescription()));
      tList.set(index, null);
    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return;
  }
}
