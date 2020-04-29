package low_level_windowing;



import high_level_windowing.IConfigurableWindow;

import java.util.Collection;

public interface IWindowExtractor <T extends IConfigurableWindow>  {

    Collection<Window> getWindows(T element, long timestamp);

}
