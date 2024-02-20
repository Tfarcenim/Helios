package tfar.helios.net;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tfar.helios.Helios;

public class PacketHandler {

  public static SimpleNetworkWrapper INSTANCE = null;

  public PacketHandler() {
  }
  public static void registerMessages() {
    INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Helios.MODID);
    int i = 0;
    INSTANCE.registerMessage(S2CSetHeliosEventPacket.Handler.class, S2CSetHeliosEventPacket.class, i++, Side.CLIENT);
  }

  public static void sendPacketToAllClientsInDimension(IMessage pkt, int dim) {
    INSTANCE.sendToDimension(pkt,dim);
  }

  public static void sendPacketToClient(IMessage pkt, EntityPlayerMP player) {
    INSTANCE.sendTo(pkt,player);
  }

  public static void sendPacketToServer(IMessage pkt) {
    INSTANCE.sendToServer(pkt);
  }
}