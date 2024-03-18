package kr.nexg.esm.logs.dto;

public interface LogsConstants {
	// MongoDB
	int LOG_TYPE_SESSION = 1;
	int LOG_TYPE_IPSEC = 2;
	int LOG_TYPE_IPS = 3;
	int LOG_TYPE_L7F = 4;
	int LOG_TYPE_HTTP = 5;
	int LOG_TYPE_AUTH = 6;
	int LOG_TYPE_SYSTEM = 7;
	int LOG_TYPE_MANAGEMENT = 8;
	int LOG_TYPE_LOGIN = 9;
	int LOG_TYPE_CONFIG = 10;
	int LOG_TYPE_VERSION = 11;

	// MariaDB
	int LOG_TYPE_REBOOT = 12;
	int LOG_TYPE_FAIL = 13;
	int LOG_TYPE_ALARM = 14;
	int LOG_TYPE_ESMAUDIT = 15;

	// MariaDB M2MG
	int LOG_TYPE_RESOURCE = 17;
	int LOG_TYPE_COMMAND = 18;

	//로그저장 진행현황 1 (진행), 2 (완료), 3(에러)
	int LOGSAVE_STATUS_PROCESS = 1;
	int LOGSAVE_STATUS_END = 2;
	int LOGSAVE_STATUS_ERROR = 3;

	// 로그저장시 최대 레코드 개수. (백만개)
	String LOGSAVE_RECORD_LIMIT = "1000000";
}
