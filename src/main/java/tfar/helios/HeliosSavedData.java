package tfar.helios;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class HeliosSavedData extends WorldSavedData {

    private HeliosEvent heliosEvent = HeliosEvent.NOTHING;
    public HeliosSavedData() {
        this("current_event");
    }
    public HeliosSavedData(String name) {
        super(name);
    }

    public void setHeliosEvent(HeliosEvent event) {
        heliosEvent = event;
        markDirty();
    }

    public HeliosEvent getHeliosEvent() {
        return heliosEvent;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        heliosEvent = HeliosEvent.EVENTS.get(nbt.getInteger("event"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("event",HeliosEvent.EVENTS.indexOf(heliosEvent));
        return compound;
    }
}
