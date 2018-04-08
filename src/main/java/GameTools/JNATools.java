package GameTools;

import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.util.List;

public interface JNATools {
    JNATools instanceDll = (JNATools) Native.loadLibrary("user32", JNATools.class);

    public boolean GetCursorPos(POINT pt);

    public int GetSystemMetrics(int nIndex);
}

class POINT extends Structure {

    @Override
    protected List<String> getFieldOrder() {
        return null;
    }

}