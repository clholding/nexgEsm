package kr.nexg.esm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nexg.esm.mapper.DevicesMapper;

@Service
public class DevicesService {
	
	@Autowired
	DevicesMapper devicesMapper;
	
}
