package tfar.helios.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tfar.helios.Client;
import tfar.helios.HeliosEvent;

// not threadsafe!
public class S2CSetHeliosEventPacket implements IMessage {

  public S2CSetHeliosEventPacket() {
  }

  public S2CSetHeliosEventPacket(HeliosEvent event) {
    this.heliosEvent = event;
  }

  private HeliosEvent heliosEvent;

  @Override
  public void fromBytes(ByteBuf buf) {
    heliosEvent = HeliosEvent.EVENTS.get(buf.readInt());
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeInt(HeliosEvent.EVENTS.indexOf(heliosEvent));
  }

  public static class Handler implements IMessageHandler<S2CSetHeliosEventPacket, IMessage> {
    @Override
    public IMessage onMessage(S2CSetHeliosEventPacket message, MessageContext ctx) {
      // Always use a construct like this to actually handle your message. This ensures that
      // youre 'handle' code is run on the main Minecraft thread. 'onMessage' itself
      // is called on the networking thread so it is not safe to do a lot of things
      // here.
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
      return null;
    }

    private void handle(S2CSetHeliosEventPacket message, MessageContext ctx) {
      // This code is run on the server side. So you can do server-side calculations here
      Minecraft.getMinecraft().addScheduledTask(() -> Client.clientsideHeliosEvent = message.heliosEvent);
    }
  }
}