package the.flash.protocol.request;

import lombok.Data;
import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

@Data
public class MessageRequestPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
