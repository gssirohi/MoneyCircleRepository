package company.greatapp.moneycircle.model;

/**
 * Created by gyanendra on 23/9/15.
 */
public class Slot {
    int start;
    int end;
    int maxIndex;
    int slotCounter;
    boolean isNextSlotAvailable;


    public Slot(int size){
        slotCounter = 1;
        if(size <= 50){
            start = 0;
            end = size - 1;
            maxIndex = size - 1;
            isNextSlotAvailable = false;
        } else {
            if((size-50) > 10) {
                start = 0;
                end = 50;
                maxIndex = size - 1;
                isNextSlotAvailable = true;
            } else {
                start = 0;
                end = size - 1;
                maxIndex = size -1;
                isNextSlotAvailable = false;
            }
        }
    }

    public boolean isNextAvailable(){
        return isNextSlotAvailable;
    }
    public void nextSlot() {
        if (isNextSlotAvailable) {
            slotCounter++;
            if ((maxIndex - end) <= 50) {
                start = end + 1;
                end = maxIndex;
                isNextSlotAvailable = false;
            } else {
                if (((maxIndex - end) - 50) > 10) {
                    start = end + 1;
                    end = end + 50;
                    isNextSlotAvailable = true;
                } else {
                    start = end;
                    end = maxIndex;
                    isNextSlotAvailable = false;
                }
            }
        }
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }

    public int getSlotCounter() {
        return slotCounter;
    }

    public void setSlotCounter(int slotCounter) {
        this.slotCounter = slotCounter;
    }

    public boolean isNextSlotAvailable() {
        return isNextSlotAvailable;
    }

    public void setIsNextSlotAvailable(boolean isNextSlotAvailable) {
        this.isNextSlotAvailable = isNextSlotAvailable;
    }
}
