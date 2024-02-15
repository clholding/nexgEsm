package kr.nexg.esm.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

//import kr.co.elink.dto.FileVo;

@Component
public class UploadFile {
	
	@Value("${server.file.path}")
	private String serverFilePath;
	
	@Value("${server.temp.file.path}")
	private String serverTempFilePath;
	
	public String makeDir() {
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator +cal.get(Calendar.YEAR) + "";
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		if(!new File(serverFilePath).exists()) {
			new File(serverFilePath).mkdir();
		}
		
		if(!new File(serverFilePath+datePath, datePath).exists()) {
			new File(serverFilePath, yearPath).mkdir();
			new File(serverFilePath, monthPath).mkdir();
			new File(serverFilePath, datePath).mkdir();
		}
		
		
		return datePath;
		
	}
	
//	public FileVo upload(MultipartFile multipartFile, FileVo vo) throws IOException {
//		UUID uuid = UUID.randomUUID();
//		String originalFileName = multipartFile.getOriginalFilename();
//		String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//		String fileName = uuid + "." + fileExt;
//		String dir = makeDir();
//		
//		File file = new File(serverFilePath + dir, fileName);
//		multipartFile.transferTo(file);
////		file.createNewFile();
////		FileCopyUtils.copy(multipartFile.getBytes(), file);
//		
//		String type="";
//		String fileType="";
//		type = Files.probeContentType(file.toPath());
//		if(type.startsWith("image")) {
//			fileType = "0";
//		}else {
//			fileType = "2";
//		}
//		
//		FileVo fileVo = new FileVo();
//		fileVo.setFileId(vo.getFileId());
//		fileVo.setFileNm(fileName);
//		fileVo.setFileOriginNm(originalFileName);
//		fileVo.setFilePath(dir);
//		fileVo.setFileSize(multipartFile.getSize());
//		fileVo.setFileType(fileType);
//		
//		return fileVo;
//		
//	}
//	
//	public FileVo uploadTemp(MultipartFile multipartFile, FileVo vo) throws IOException {
//		UUID uuid = UUID.randomUUID();
//		String originalFileName = multipartFile.getOriginalFilename();
//		String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//		String fileName = uuid + "." + fileExt;
//		String dir = makeDir();
//		
//		File file = new File(serverTempFilePath + dir, fileName);
//		multipartFile.transferTo(file);
////		file.createNewFile();
////		FileCopyUtils.copy(multipartFile.getBytes(), file);
//		
//		String type="";
//		String fileType="";
//		type = Files.probeContentType(file.toPath());
//		if(type.startsWith("image")) {
//			fileType = "0";
//		}else {
//			fileType = "2";
//		}
//		
//		FileVo fileVo = new FileVo();
//		fileVo.setFileId(vo.getFileId());
//		fileVo.setFileNm(fileName);
//		fileVo.setFileOriginNm(originalFileName);
//		fileVo.setFilePath(dir);
//		fileVo.setFileSize(multipartFile.getSize());
//		fileVo.setFileType(fileType);
//		
//		return fileVo;
//		
//	}
	
	
	
}
