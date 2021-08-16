package the.flash.protocol.request;

import lombok.Data;
import the.flash.protocol.Packet;
import the.flash.protocol.command.Command;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
