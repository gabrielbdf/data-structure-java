import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomArray {

    private int length = 0;
    private Object[] data = new Object[0];
    private List<CustomArray> shadows = new ArrayList<>();

    public CustomArray() {

    }

    public CustomArray(Object[] initialData) {
        this.data = initialData;
        this.length = initialData.length;
    }

    public int push(Object value) {
        int newCapacity = this.length + 1;
        Object[] newArr = new Object[newCapacity];
        System.arraycopy(this.data, 0, newArr, 0, this.length);
        this.data = newArr;
        data[this.length++] = value;
        Method method = new Object() {
        }.getClass().getEnclosingMethod();
        this.shadows.forEach(item -> {
            try {
                method.invoke(item, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return this.length;
    }

    public Object get(int index) {
        return this.data[index];
    }

    public void remove(int index) {
        if (index <= this.length - 1) {
            int newCapacity = this.length - 1;
            Object[] newArray = new Object[newCapacity];
            for (int i = index; i < this.data.length - 1; i++) {
                this.data[i] = this.data[i + 1];
            }
            for (int i = 0; i < newCapacity; i++) {
                newArray[i] = this.data[i];
            }
            this.data = newArray;
            this.length--;
        }
    }

    public Object pop() {
        Object last = data[length - 1];
        this.remove(length - 1);
        return last;
    }

    public Object shift() {
        Object first = data[0];
        this.remove(0);
        return first;
    }

    public int length() {
        return this.length;
    }

    public CustomArray concat(CustomArray toMerge) {
        int newCapacity = this.length + toMerge.length();
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(this.data, 0, newArray, 0, this.length);
        for (int i = this.length; i < newCapacity; i++) {
            newArray[i] = toMerge.get(i - this.length);
        }
        this.data = newArray;
        this.length = newCapacity;
        return this;
    }

    public int indexOf(Object value) {
        int index = -1;
        for (int i = 0; i < this.length; i++) {
            if (data[i].equals(value)) {
                return i;
            }
        }
        return index;
    }

    public void forEach(Consumer<Object> item) {
        for (int i = 0; i < this.length; i++) {
            item.accept(this.data[i]);
        }
    }

    public CustomArray map(Function<Object, Object> mappingFunction) {
        CustomArray resultArray = new CustomArray();
        for (int i = 0; i < this.length; i++) {
            Object mappedValue = mappingFunction.apply(this.data[i]);
            resultArray.push(mappedValue);
        }
        return resultArray;
    }

    public CustomArray filter(Function<Object, Boolean> filterFunction) {
        CustomArray resultArray = new CustomArray();
        for (int i = 0; i < this.length; i++) {
            Boolean shouldInclude = filterFunction.apply(this.data[i]);
            if (shouldInclude) {
                resultArray.push(this.data[i]);
            }
        }
        return resultArray;
    }

    public boolean includes(Object value) {
        for (int i = 0; i < length; i++) {
            if (data[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public String join(String separator) {
        StringBuilder joined = new StringBuilder();
        for (int i = 0; i < length; i++) {
            joined.append(data[i]).append(separator);
        }
        return joined.substring(0, joined.length() - 1);
    }

    public CustomArray shadow() {
        CustomArray clone = new CustomArray(data);
        clone.addShadow(this);
        this.shadows.add(clone);
        return clone;
    }

    public void addShadow(CustomArray shadow) {
        this.shadows.add(shadow);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.join(","));
        sb.append("]");
        return sb.toString();
    }

}
