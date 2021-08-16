package the.flash.protocol.response;

import lombok.Data;
import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
