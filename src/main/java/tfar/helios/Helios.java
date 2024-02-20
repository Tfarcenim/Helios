package tfar.helios;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tfar.helios.mixin.ChunkMixin;
import tfar.helios.mixin.EnumSkyBlockMixin;
import tfar.helios.net.PacketHandler;
import tfar.helios.net.S2CSetHeliosEventPacket;

import java.util.function.Function;

@Mod(modid = Helios.MODID)
@Mod.EventBusSubscriber
public class Helios {
    public static final String MODID = "helios";
    public static final Logger LOG = LogManager.getLogger();

    public static final boolean REMAP = false;

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        HeliosEvent.setupConfig();
        darken();
    }

    @SubscribeEvent
    public static void login(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayerMP playerMP = (EntityPlayerMP) event.player;
        HeliosEvent heliosEvent = getActiveEvent(playerMP.server);
        PacketHandler.sendPacketToClient(new S2CSetHeliosEventPacket(heliosEvent),playerMP);
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            World world = event.world;
            if (world.provider.getDimension() == 0) {
                if (world instanceof WorldServer) {
                    WorldServer serverWorld = (WorldServer) world;
                    if ((serverWorld.getTotalWorldTime() % 24000) == 0) {
                        triggerEventChange(serverWorld);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void afterSleep(PlayerWakeUpEvent event) {
        World world = event.getEntityPlayer().world;
        if (world instanceof WorldServer) {
            WorldServer serverWorld = (WorldServer) world;
            triggerEventChange(serverWorld);
        }
    }

    public static void triggerEventChange(WorldServer worldServer) {
        HeliosSavedData heliosSavedData = getOrCreate(getOverworld(worldServer.getMinecraftServer()),HeliosSavedData.class, HeliosSavedData::new,"current_event");
        heliosSavedData.setHeliosEvent(HeliosEvent.EVENTS.getWeightedRandomItem(worldServer.rand));
        LOG.debug("Set helios event to "+heliosSavedData.getHeliosEvent().name());
        PacketHandler.INSTANCE.sendToAll(new S2CSetHeliosEventPacket(heliosSavedData.getHeliosEvent()));
    }

    public static void darken() {
        ((EnumSkyBlockMixin)(Object)EnumSkyBlock.SKY).setDefaultLightValue(7);
    }

    public static void revertDarken() {
        ((EnumSkyBlockMixin)(Object)EnumSkyBlock.SKY).setDefaultLightValue(15);
    }

    public static HeliosEvent getActiveEvent(MinecraftServer server) {
        return getOrCreate(server.getWorld(0),HeliosSavedData.class,HeliosSavedData::new,"current_event").getHeliosEvent();
    }

    @SuppressWarnings("unchecked")
    public static <T extends WorldSavedData> T
    getOrCreate(WorldServer worldServer, Class<T> clazz, Function<String,T> create,String id) {
        T worldSavedData = (T)worldServer.loadData(clazz,id);
        if (worldSavedData == null) {
            worldSavedData = create.apply(id);
            worldServer.setData(id,worldSavedData);
        }
        return worldSavedData;
    }

    public static WorldServer getOverworld(MinecraftServer server) {
        return server.getWorld(0);
    }
}
// The idea is simple, each minecraft day, you have a chance for an event to occur (this can be configured through config),
// the day can have only one event at a time. The events would be:
//-Solar eclipse (The texture of the sun would be replaced by obscured sun (I'll most likely draw the texture myself if i'll somehow get the original sun texture),
// during this event, the hostile mobs would be able to spawn during daytime, the skybox will be darker than normal, and the world will be lighted at level 7 (approx)

//-"Bad sun" in this event, skybox will be brighter than normal, sun will be replaced by the black square (again, i will re-draw the texture of original sun),
// and every entity (including player) will be taking damage during this event, if exposed to the sunlight (skyVisible) (if it's raining, then no damage will be applied) This is totally not inspired by the Votv p.s. my friend gave me an idea for this

//One more idea:
//"cool sun" During this event, sun will have sunglasses (i'll draw textures myself again), and constant effect of luck will be applied to player during daytime (no matter, if he's exposed to the sunlinght, or not, but rain will stop the effect