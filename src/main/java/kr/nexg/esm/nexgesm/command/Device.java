package kr.nexg.esm.nexgesm.command;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.nexgesm.mariadb.Config;
import kr.nexg.esm.nexgesm.mariadb.Log;

@Component
public class Device {
	
	@Autowired
	Log.EsmAuditLog esmAuditLog;
	
	@Autowired
	Config.Config1 config1;
	
	@Autowired
	Producer producer;
	
//    public Device() {
//        // Constructor
//    }

    public void apply_device(String user_id, String host_ip) throws IOException {
    	
    	producer.sendMessage();
    	
    	esmAuditLog.esmlog(1, user_id, host_ip, "전체 장비 설정 적용 하였습니다.");
    	
    	config1.set_apply_status(false);
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
