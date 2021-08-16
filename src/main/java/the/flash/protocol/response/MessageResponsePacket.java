package the.flash.protocol.response;

import lombok.Data;
import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

@Data
public class MessageResponsePacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
