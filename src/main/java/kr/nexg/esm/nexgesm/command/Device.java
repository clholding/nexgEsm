package kr.nexg.esm.nexgesm.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class Device {
	
    public Device() {
        // Constructor
    }

    public void test() throws IOException {
        Runtime.getRuntime().exec("/opt/esm/script/test.sh");
    }
    
    public void add_device(String did) throws IOException {
        Runtime.getRuntime().exec("/opt/esm/script/add_device.sh " + did);
    }

    public void del_device(String did) throws IOException {
        Runtime.getRuntime().exec("/opt/esm/script/del_device.sh " + did);
    }

    public void generate_agent() throws IOException {
        Runtime.getRuntime().exec("/opt/esm/script/gen_agent.sh");
    }

    public void device_config_backup_delete(String deviceID) {
        if (deviceID == null || deviceID.isEmpty()) {
            return;
        }

        try {
            Files.deleteIfExists(Paths.get("/opt/esm/backup/config/device_config_" + deviceID + ".tgz"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String device_config_backup(String user, int type, String deviceIDs) {
        String filePath = "";

        if (deviceIDs == null || deviceIDs.isEmpty()) {
            return "";
        }


        return filePath;
    }
}
